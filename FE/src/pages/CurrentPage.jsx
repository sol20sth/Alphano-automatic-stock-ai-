import React from 'react';
import BackTitleCurrent from '../components/common/BackTitleCurrent';
import Instance from '@/api/Instance';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import TradeChart from '../components/chart/TradeChart';
import PriceChart from '../components/chart/PriceChart';

function CurrentPage() {
  const location = useLocation();
  const { stockName = '삼성전자', stockCode = '005930' } = location.state || {};
  const [targetStockData, setTargetStockData] = useState(null);

  async function addCurrent(name) {
    const tmp = { stockName: name };
    try {
      const response = await Instance.post(`api/v1/member/recent/stock`, tmp);
    } catch (error) {
      console.error(error, tmp);
    }
  }
  addCurrent(stockName);
  const [data, setData] = useState([]);

  useEffect(() => {
    async function getCurrentPrice() {
      try {
        const response = await Instance.get(`api/v1/redis/currentPrice/all`);
        const allStocksData = response.data.data;

        // 주식 코드에 해당하는 데이터를 가져옴
        const data = allStocksData[`+${stockCode}`];

        setTargetStockData(data); // 상태 업데이트
      } catch (error) {
        console.error('현재가', error);
      }
    }
    getCurrentPrice();
  }, []);

  useEffect(() => {
    async function getStock() {
      try {
        const response = await Instance.get(`api/v1/stock-daily/${stockCode}`);
        const yearData = response.data.data.map((day) => ({
          time_close: new Date(
            day.stockDate.slice(0, 4),
            day.stockDate.slice(4, 6) - 1,
            day.stockDate.slice(6, 8),
          ).getTime(),
          open: day.startPrice,
          high: day.maxPrice,
          low: day.minPrice,
          close: day.endPrice,
          aiMax: day.aiMaxPrice,
          aiMin: day.aiMinPrice,
        }));
        setData((prevData) => [...prevData, ...yearData]);
      } catch (error) {
        console.log(error);
      }
    }
    getStock();
  }, []);

  return (
    <div>
      <BackTitleCurrent title={stockName} stockCode={stockCode} />

      <div className="flex justify-center font-bold">
        <div
          className={`flex justify-around items-center w-90vw rounded-lg h-10vh text-white text-[4.7vw] ${
            targetStockData?.split('/')[3] === '1' ||
            targetStockData?.split('/')[3] === '2'
              ? 'bg-gradient-to-r from-red-500 to-pink-500'
              : targetStockData?.split('/')[3] === '3'
              ? 'bg-gradient-to-r from-black to-gray-500'
              : targetStockData?.split('/')[3] === '4' ||
                targetStockData?.split('/')[3] === '5'
              ? 'bg-gradient-to-r from-blue-500 to-[#2828CD]'
              : ''
          }`}
        >
          <div className="flex flex-col justify-center items-center">
            <p className="text-[3.5vw]">현재가</p>
            <p>{Number(targetStockData?.split('/')[0]).toLocaleString()}</p>
          </div>
          <div className="flex flex-col justify-center items-center">
            <p className="text-[3.5vw]">전일 대비가</p>
            <div className="flex pl-2vw">
              <p>
                {targetStockData &&
                  targetStockData?.split('/')[2] &&
                  Number(
                    targetStockData?.split('/')[2] === '-'
                      ? targetStockData?.split('/')[2].slice(1)
                      : targetStockData?.split('/')[2],
                  ).toLocaleString()}
              </p>
              <div className="flex items-center">
                {targetStockData &&
                  targetStockData?.split('/')[3] &&
                  (targetStockData?.split('/')[3] === '1' ||
                  targetStockData?.split('/')[3] === '2' ? (
                    <ArrowDropUpIcon />
                  ) : targetStockData?.split('/')[3] === '3' ? null : (
                    <ArrowDropDownIcon />
                  ))}
              </div>
            </div>
          </div>
          <div className="flex flex-col justify-center items-center">
            <p className="text-[3.5vw]">전일 대비율</p>
            {targetStockData &&
              targetStockData?.split('/')[1] &&
              (targetStockData?.split('/')[1] === '-'
                ? targetStockData?.split('/')[1].slice(1)
                : targetStockData?.split('/')[1])}
            %
          </div>
        </div>
      </div>
      <PriceChart stockCode={stockCode} />
      <div className="flex justify-center items-center mb-5vw">
        <div className="flex ml-3vw justify-center items-center">
          <div className="w-3vw h-1vh bg-[#FEB019]"></div>
          <p className="ml-1vw">AI최저가</p>
        </div>
        <div className="flex ml-3vw justify-center items-center">
          <div className="w-3vw h-1vh bg-[#00E396]"></div>
          <p className="ml-1vw">AI최고가</p>
        </div>
      </div>
      <TradeChart stockCode={stockCode} />
      <div className="flex justify-center">
        <p>거래량</p>
      </div>
    </div>
  );
}

export default CurrentPage;
