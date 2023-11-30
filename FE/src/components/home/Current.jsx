import React, { useEffect, useState } from 'react';
import Instance from '@/api/Instance';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { setCurrentPrice } from '@/store/currentPrice';
import RemoveIcon from '@mui/icons-material/Remove';

function Current() {
  const [recentData, setRecentData] = useState([]);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const currentPrice = useSelector((state) => state.currentPrice.data);

  useEffect(() => {
    async function FetchCurrentData() {
      async function currentStock() {
        try {
          const response = await Instance.get(`api/v1/member/recent/stock`);
          setRecentData(response.data.data.slice(0, 5));
        } catch (error) {
          console.error('최근 종목 ', error);
        }
      }
      async function getCurrentPrice() {
        try {
          const response = await Instance.get(`api/v1/redis/currentPrice/all`);
          dispatch(setCurrentPrice(response.data.data));
        } catch (error) {
          console.error('현재가: ', error);
        }
      }
      await currentStock();
      await getCurrentPrice();
    }
    FetchCurrentData();
  }, []);

  return (
    <div className="ml-2vw mr-2vw">
      <p className="text-[5.5vw] font-bold mb-5vw ml-4vw">최근 검색 종목</p>
      <div className="flex rounded-md text-[4.0vw] font-bold text-gray-600 py-2vw bg-slate-100">
        <p className="flex w-30vw justify-center">종목명</p>
        <p className="flex w-20vw ml-14vw justify-end">현재가</p>
        <p className="flex w-20vw ml-3vw justify-end ">등락률</p>
      </div>
      <div className="mb-4vw border rounded-lg shadow-md">
        {recentData &&
          recentData.length > 0 &&
          recentData.map((stock, index) => (
            <div>
              <div
                key={index}
                className="flex items-center mb-2vw ml-7vw"
                onClick={() =>
                  navigate('/currentprice', {
                    state: {
                      stockName: stock.stockName,
                      stockCode: stock.stockCode,
                    },
                  })
                }
              >
                <div className="w-43vw mt-3vw">
                  <p className={`font-bold text-gray-900`}>{stock.stockName}</p>
                </div>
                <div className="flex w-20vw mt-3vw justify-end pr-5vw">
                  <p
                    className={`flex font-bold ${
                      currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                        '1' ||
                      currentPrice['+' + stock.stockCode]?.split('/')[3] === '2'
                        ? 'text-red-500'
                        : currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                            '4' ||
                          currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                            '5'
                        ? 'text-blue-500'
                        : ''
                    }`}
                  >
                    {Number(
                      currentPrice['+' + stock.stockCode]?.split('/')[0],
                    ).toLocaleString()}
                  </p>
                </div>
                <div
                  className={`flex font-bold mt-3vw ${
                    currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                      '1' ||
                    currentPrice['+' + stock.stockCode]?.split('/')[3] === '2'
                      ? 'text-red-500'
                      : currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                          '4' ||
                        currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                          '5'
                      ? 'text-blue-500'
                      : ''
                  }`}
                >
                  {stock &&
                    currentPrice['+' + stock.stockCode]?.split('/')[3] &&
                    (currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                      '1' ||
                    currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                      '2' ? (
                      <ArrowDropUpIcon />
                    ) : currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                      '3' ? (
                      <RemoveIcon />
                    ) : (
                      <ArrowDropDownIcon />
                    ))}
                  <p>
                    {currentPrice['+' + stock.stockCode]
                      ?.split('/')[1]
                      .charAt(0) === '-'
                      ? currentPrice['+' + stock.stockCode]
                          ?.split('/')[1]
                          .substring(1)
                      : currentPrice['+' + stock.stockCode]?.split('/')[1]}
                    %
                  </p>
                </div>
              </div>
              <hr></hr>
            </div>
          ))}
      </div>
    </div>
  );
}

export default Current;
