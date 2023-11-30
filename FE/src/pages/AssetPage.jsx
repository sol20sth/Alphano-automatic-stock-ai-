import React, { useEffect, useState } from 'react';
import BackTitle from '../components/common/BackTitle';

import axios from 'axios';
import Instance from '@/api/Instance';

import { useDispatch } from 'react-redux';
import { setTotalMyAssetInfo } from '../store/totalMyAssetInfo';
import { setHaveStockList } from '../store/haveStock';
import AssetMyStock from '../components/asset/AssetMyStock';
import DetailAsset from '../components/asset/DetailAsset';
import Margin from '../components/asset/Margin';

function AssetPage() {
  const dispatch = useDispatch();
  const [checkManu, setCheckManu] = useState([true, false, false]);
  const [cano, setCano] = useState('');
  const [acnt_PRDT_CD, setAcnt_PRDT_CD] = useState('');

  useEffect(() => {
    async function fetchGetData() {
      async function getData() {
        try {
          const response = await Instance.get(`api/v1/member/private-info`);
          const data = response.data.data;
          setCano(response.data.data.cano);
          setAcnt_PRDT_CD(response.data.data.acnt_PRDT_CD);
        } catch (error) {
          console.log('API쏘기 위한 데이터 받아오기: ', error);
        }
      }
      async function getAssetData() {
        try {
          const response = await Instance.get(`api/v1/rest-api/balance`);
          const tmp = JSON.parse(response.data.data.body)
          dispatch(setHaveStockList(tmp.output1));
          dispatch(setTotalMyAssetInfo(tmp.output2[0]));
          
        } catch (error) {
          console.log(error, '잔고조회에러');
        }
      }
      await getData();
      await getAssetData();
    }
    fetchGetData();
  }, []);


  function onchangeManu(index) {
    const tmp = [false, false];
    tmp[index] = !tmp[index];
    setCheckManu(tmp);
  }
  return (
    <div className="h-92vh overflow-y-auto">
      <div>
        <BackTitle title="잔고" />
      </div>
      <div className="flex ml-7vw justify-center text-center items-center h-6vh w-84vw text-lg border border-black-400 rounded" style={{ backgroundColor: '#f3f4f5' }}>
        <p >
          {cano}-{acnt_PRDT_CD} 위탁
        </p>
      </div>
      <div className="mb-5 grid grid-cols-2">
        <button
          className={`text-base h-6vh border-b-${
            checkManu[0] ? '4 border-b-slate-400 font-bold' : ' font light'
          } `}
          onClick={() => onchangeManu(0)}
        >
          보유
        </button>
        <button
          className={`text-base h-6vh border-b-${
            checkManu[1] ? '4 border-b-slate-400 font-bold' : ' font light'
          } `}
          onClick={() => onchangeManu(1)}
        >
          증거/대출
        </button>
      </div>

      {checkManu[0] ? <DetailAsset /> : <Margin />}
      <div className="mt-6 mb-4">
        <AssetMyStock />
      </div>
    </div>
  );
}

export default AssetPage;
