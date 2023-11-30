import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import BackTitle from '../common/BackTitle';
import { formatNumberWithCommas } from '../../hooks/formatnum';
import Instance from '@/api/Instance';

export default function DetailDeposit() {
  const totalMyAssetInfo = useSelector((state) => state.totalMyAssetInfo);
  const navigate = useNavigate();
  const [getEssential, setGetEssential] = useState([]);
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
        setGetEssential(response.data.data);
        updateAccountNum(response.data.data)
      } catch (error) {
        console.error( error);
      }
    }
    async function updateAccountNum(data) {
      setCano(data.cano);
      setAcnt_PRDT_CD(data.acnt_PRDT_CD);
    }
    checkIsData()
  }, []);
  return (
    <>
      <div style={{ height: '92vh', overflowY: 'auto' }}>
        <div className="mb-2">
          <BackTitle title="예수금" />
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

        <div>
          <div className="mx-3 p-3 mb-3">
            {/* <p className="text-xl font-semibold">예수금 상세보기</p> */}
            <div className='mt-3 border border-blue-200'>
            <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
              <div className="text-sm">출금가능금액</div>
              <div className="text-sm">
                {formatNumberWithCommas(totalMyAssetInfo.dnca_tot_amt) || 0} 원
              </div>
            </div>
            <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
              <div className="text-sm">주문가능금액</div>
              <div className="text-sm">
                {formatNumberWithCommas(parseInt(totalMyAssetInfo.dnca_tot_amt / 0.9975)) || 0} 원
              </div>
            </div>
            <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
              <div className="text-sm">예수금</div>
              <div className="text-sm">
                {formatNumberWithCommas(totalMyAssetInfo.dnca_tot_amt) || 0} 원
              </div>
            </div>
            <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
              <div className="text-sm">D + 1 예수금</div>
              <div className="text-sm">
                {formatNumberWithCommas(totalMyAssetInfo.nxdy_excc_amt) || 0} 원
              </div>
            </div>
            <div className="flex justify-between mx-3 p-3 mb-3 border-b-2 border-b-slate-100">
              <div className="text-sm">D + 2 예수금</div>
              <div className="text-sm">
                {formatNumberWithCommas(totalMyAssetInfo.prvs_rcdl_excc_amt) || 0} 원
              </div>
            </div>
            </div>
 
          </div>
        </div>
      </div>
    </>
  );
}
