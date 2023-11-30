import React, { useEffect, useState } from 'react';
import Instance from '@/api/Instance';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function OwnStockList() {
  const [isClicked, setIsClicked] = useState(null);

  const navigate = useNavigate();
  const [ownStockList, setOwnStockList] = useState([]);

  useEffect(() => {
    async function getData() {
      try {
        const response = await Instance.get(`api/v1/member/private-info`);
        const data = response.data.data;
        await getAssetData();
      } catch (error) {
        console.error('한투 API쏘기 위한 데이터 받아오기: ', error);
      }
    }
    async function getAssetData() {
      try {
        const response = await Instance.get(`api/v1/rest-api/balance`);
        const tmp = JSON.parse(response.data.data.body)
        setOwnStockList(tmp.output1);
      } catch (error) {
        console.log(error, '잔고조회에러');
      }
    }
    getData();
    getAssetData()
  }, []);

  const formatPrice = (price) => {
    const formatted = Math.floor(price).toLocaleString();
    return formatted.endsWith('.0000')
      ? formatted.replace('.0000', '')
      : formatted;
  };

  const CLickStockInfo = (name) => {
    if (isClicked === name.prdt_name) {
      setIsClicked(null);
    } else {
      setIsClicked(name.prdt_name);
    }
  };

  // 현재가 보러가기
  const moveToCurrentprice = () => {
    console.log('hi');
    navigate('/currentprice');
  };

  // 매도 옵션하러 가기
  const moveToOption = (item) => {
    if (item) {
      navigate('/selloptionpage', { state: item });
    } else {
      console.error('error');
      toast.error('보유 주식을 선택해주세요.');
    }
  };
  return (
    <div className={``}>
      <div className="text-l font-bold mt-1 mb-3"> 보유 종목 (선택) </div>
      {ownStockList?.map((item, index) => (
        <div key={index}>
          <div
            type="button"
            className={`border-b border-t border-r border-l rounded-md border-gray-500 mb-2`}
            onClick={() => CLickStockInfo(item)}
            aria-hidden
          >
            <div className="flex gap-2 m-2">
              <div className="text-l font-semibold">{item.prdt_name}</div>
              <div className="text-xs flex text-gray-400 items-end">
                {item.trad_dvsn_name}
              </div>
            </div>
            <div className="flex items-center justify-between gap-5 m-2 mt-5 ">
              <div className="flex w-1/2 justify-between">
                <div className="text-xs flex text-gray-500 items-end">
                  잔고 :{' '}
                </div>
                <div className="text-xs flex text-gray-900 items-end font-semibold">
                  {' '}
                  {item.hldg_qty}주
                </div>
              </div>
              <div className="flex w-1/2 justify-between items-center">
                <div className="text-xs flex text-gray-500 items-center">
                  평균단가 :{' '}
                </div>
                <div className="text-xs flex text-gray-900 items-center font-semibold">
                  {formatPrice(item.pchs_avg_pric)}원
                </div>
              </div>
            </div>
          </div>
          <div
            className={`flex justify-center gap-5 mb-5 ${
              isClicked === item.prdt_name ? '' : 'hidden'
            }`}
          >
            <button
              className="w-36 mt-4 border-2 border-blue-500  bg-white hover:bg-blue-400 text-blue-500 font-bold py-2 px-4 rounded"
              type="button"
              onClick={() => {
                navigate('/currentprice', {
                  state: {
                    stockName: ownStockList[index].prdt_name,
                    stockCode: ownStockList[index].pdno,
                  },
                });
              }}
            >
              현재가
            </button>
            <button
              className="w-36 mt-4 border-2 border-blue-500 bg-white hover:bg-blue-400 text-blue-500 font-bold py-2 px-4 rounded"
              type="button"
              onClick={() => moveToOption(item)}
            >
              매도옵션설정
            </button>
          </div>
        </div>
      ))}
    </div>
  );
}

export default OwnStockList;
