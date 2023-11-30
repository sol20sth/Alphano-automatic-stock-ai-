import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { setTotalMyAssetInfo } from '../../store/totalMyAssetInfo';
import BackTitle from '../common/BackTitle';
import { formatNumberWithCommas } from '../../hooks/formatnum';
import Instance from '@/api/Instance';

export default function DetailBalance() {
  const totalMyAssetInfo = useSelector((state) => state.totalMyAssetInfo);
  const navigate = useNavigate();
  const [cano, setCano] = useState('');
  const [acnt_PRDT_CD, setAcnt_PRDT_CD] = useState('');
  useEffect(() => {
    // 정보가 비어있으면 다시 assets로 이동
    async function checkIsData() {
      const keys = Object.keys(totalMyAssetInfo);
      if (keys.length === 0) {
        navigate('/assets');
      }
      await getData(); // 데이터 받아오기를 기다린 후
    }

    async function getData() {
      try {
        const response = await Instance.get(`api/v1/member/private-info`);
        setCano(response.data.data.cano)
        setAcnt_PRDT_CD(response.data.data.acnt_PRDT_CD)
      } catch (error) {
        console.error( error);
      }
    }
    checkIsData()
  }, []);

  return (
    <div style={{ height: '92vh', overflowY: 'auto' }}>
      <div className='mb-2'>
        <BackTitle title="자산 상세보기" />
      </div>
      <div className="flex justify-center text-center items-center mb-2">
        {/* <p style={{ backgroundColor: '#f3f4f5' }}>
          {cano}-{acnt_PRDT_CD}
        </p> */}
        <p
          style={{ backgroundColor: '#f3f4f5', color: '#333333' }}
          className="p-2 m-0 mb-3 w-85vw text-lg border border-black-400 rounded h-6vh "
        >
          {cano}-{acnt_PRDT_CD} 위탁
        </p>
      </div>
      <div className="mx-3 p-3 bg-slate-100">
        <div className="flex justify-between text-lg mb-3">
          <p className="text-sm">평가손익합</p>
          <p
            className="text-sm"
            style={{
              color:
                totalMyAssetInfo.evlu_pfls_smtl_amt * 1 > 0 ? 'blue' : 'red',
            }}
          >
            {formatNumberWithCommas(totalMyAssetInfo.evlu_pfls_smtl_amt) || '0 '}원
          </p>
        </div>
        <div className="flex justify-between text-lg mb-3">
          <p className="text-sm">평가손익비율</p>
          <p
            className="text-sm"
            style={{
              color:
                (totalMyAssetInfo.evlu_pfls_smtl_amt /
                  totalMyAssetInfo.pchs_amt_smtl_amt) *
                  100 > 0 ? 'blue' : 'red',
            }}
          >
            {totalMyAssetInfo.pchs_amt_smtl_amt.length!==0 ? (
              (totalMyAssetInfo.evlu_pfls_smtl_amt /
                totalMyAssetInfo.pchs_amt_smtl_amt) *
              100
            ).toFixed(2) : '0 '}
            %
          </p>
        </div>
        <div className="flex justify-between text-lg mb-3">
          <p className="text-sm">실현수익금</p>
          <p
            className="text-sm"
            style={{
              color:
                totalMyAssetInfo.tot_stln_slng_chgs * 1 > 0 ? 'blue' : 'red',
            }}
          >
            {formatNumberWithCommas(totalMyAssetInfo.tot_stln_slng_chgs) || '0 '}원
          </p>
        </div>
      </div>
      <div className="mx-3 p-3 mb-3">
        <p className="text-xl font-semibold">매수/매도</p>
        <div className='mt-3 border border-blue-200'>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">전일 매수금액</div>
          <div className="text-sm">{formatNumberWithCommas(totalMyAssetInfo.bfdy_buy_amt) || 0} 원</div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">금일 매수금액</div>
          <div className="text-sm">{formatNumberWithCommas(totalMyAssetInfo.thdt_buy_amt) || 0} 원</div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">전일매도금액</div>
          <div className="text-sm">{formatNumberWithCommas(totalMyAssetInfo.bfdy_sll_amt) || 0} 원</div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">금일매도금액</div>
          <div className="text-sm">{formatNumberWithCommas(totalMyAssetInfo.thdt_sll_amt) || 0} 원</div>
        </div>
        </div>
        
      </div>
      <div className="mx-3 p-3 mb-3">
        <p className="text-xl font-semibold">평가</p>
        <div className='mt-3 border border-blue-200'>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">매입금액합계금액</div>
          <div className="text-sm">
            {formatNumberWithCommas(totalMyAssetInfo.pchs_amt_smtl_amt) || 0} 원
          </div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">평가금액합계금액</div>
          <div className="text-sm">
            {formatNumberWithCommas(totalMyAssetInfo.evlu_amt_smtl_amt) || 0} 원
          </div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">평가손익합계금액(매도완료)</div>
          <div className="text-sm">{formatNumberWithCommas(totalMyAssetInfo.bfdy_sll_amt) || 0} 원</div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">전일총자산평가금액</div>
          <div className="text-sm">
            {formatNumberWithCommas(totalMyAssetInfo.bfdy_tot_asst_evlu_amt) || 0} 원
          </div>
        </div>
        <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
          <div className="text-sm">자산증감액</div>
          <div className="text-sm">
            {formatNumberWithCommas(totalMyAssetInfo.asst_icdc_amt )|| 0} 원
          </div>
        </div>
        </div>
        
      </div>
    </div>
  );
}
