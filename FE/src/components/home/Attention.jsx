import React from 'react';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Instance from '@/api/Instance';
import { useEffect, useState } from 'react';
import RemoveIcon from '@mui/icons-material/Remove';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setCurrentPrice } from '@/store/currentPrice';

function Attention() {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem('accesstoken');
  const [likeStock, setLikeStock] = useState(['삼성전자']);
  const [topFiveData, setTopFiveData] = useState([]);
  const dispatch = useDispatch();
  const currentPrice = useSelector((state) => state.currentPrice.data);
  useEffect(() => {
    const getAi = async () => {
      try {
        const response = await Instance.get(`api/v1/stock-daily/ai`);
        // 데이터를 aiWeeklyReliability 값에 따라 내림차순 정렬
        const sortedData = response.data.data.sort(
          (a, b) => b.aiWeeklyReliability - a.aiWeeklyReliability,
        );

        // 정렬된 데이터에서 상위 5개를 추출
        const topFive = sortedData.slice(0, 5);
        setTopFiveData(topFive);
      } catch (error) {
        console.error('AI예측 데이터 받아오기: ', error);
      }
    };
    getAi();
  }, []);

  // 관심 종목 얻어오기
  useEffect(() => {
    async function fetchData() {
      async function getLikeStock() {
        Instance.get(`api/v1/member/like/stock/`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
          .then((response) => {
            setLikeStock(response.data.data);
          })
          .catch((error) => console.error('Error :', error));
      }
      async function getCurrentPrice() {
        try {
          const response = await Instance.get(`api/v1/redis/currentPrice/all`);
          dispatch(setCurrentPrice(response.data.data));
          console.log(response.data, '현재가 받아오는 API');
        } catch (error) {
          console.error('현재가', error);
        }
      }
      await getLikeStock();
      await getCurrentPrice();
    }
    fetchData();
  }, []);

  return (
    <div className='ml-2vw mr-2vw'>
      
      {/* <p className="text-[5.5vw] font-bold mb-5vw ">나의 관심 종목</p> */}
      <div className='flex rounded-md text-[4.0vw] font-bold text-gray-600 py-2vw bg-slate-100'>
          <p className='flex w-30vw justify-center'>종목명</p>
          <p className='flex w-20vw ml-14vw justify-end'>현재가</p>
          <p className='flex w-20vw ml-3vw justify-end '>등락률</p>
      </div>
      <div className="mb-4vw border rounded-lg shadow-md">
        {likeStock &&
          likeStock.length > 0 &&
          likeStock.map((stock, index) => (
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
                    currentPrice['+' + stock.stockCode]?.split('/')[3] === '1' ||
                    currentPrice['+' + stock.stockCode]?.split('/')[3] === '2'
                      ? 'text-red-500'
                      : currentPrice['+' + stock.stockCode]?.split('/')[3] ===
                          '4' ||
                        currentPrice['+' + stock.stockCode]?.split('/')[3] === '5'
                      ? 'text-blue-500'
                      : ''
                  }`}
                >
                  {stock &&
                    currentPrice['+' + stock.stockCode]?.split('/')[3] &&
                    (currentPrice['+' + stock.stockCode]?.split('/')[3] === '1' ||
                    currentPrice['+' + stock.stockCode]?.split('/')[3] === '2' ? (
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

      <p className="text-[5.5vw] font-bold mt-10vw mb-4vw ">AI 추천</p>
      <hr />
      <div className="mt-5vw grid grid-cols-2 font-bold text-center py-2vw bg-slate-100 rounded-md text-gray-600">
        <p> 종목</p>
        <p className="ml-4vw">AI 신뢰도</p>
      </div>
      <div className="border shadow-md rounded-md">
        {topFiveData.map((stock, index) => (
          <div>
            <div
              key={index}
              className="my-3vw grid grid-cols-2 text-center"
              onClick={() =>
                navigate('/currentprice', {
                  state: {
                    stockName: stock.stockName,
                    stockCode: stock.stockCode,
                  },
                })
              }
            >
              <p className="font-bold whitespace-nowrap text-left ml-8vw text-gray-900">
                {stock.stockName}
              </p>
              <p className="font-bold ml-4vw text-gray-900">
                {stock.aiWeeklyReliability.toFixed(2)}%
              </p>
            </div>
            <hr></hr>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Attention;
