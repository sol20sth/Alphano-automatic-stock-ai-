import React, { useEffect, useState } from 'react';
import Instance from '@/api/Instance';
import Pagination from '@mui/material/Pagination';
import { useNavigate } from 'react-router-dom';
import RemoveIcon from '@mui/icons-material/Remove';
import { useDispatch, useSelector } from 'react-redux';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { setCurrentPrice } from '@/store/currentPrice';

const StockList = ({ data, navigate, currentPrice }) => {
  const goCurrentPage = (item) => {
    navigate('/currentprice', {
      state: {
        stockName: item.stockName,
        stockCode: item.stockCode,
      },
    });
  };
  return (
    <>
      <div className="flex ml-2vw mr-2vw rounded-md text-[4.0vw] font-bold text-gray-600 py-2vw bg-slate-100">
        <p className="flex w-30vw justify-center">종목명</p>
        <p className="flex w-20vw ml-14vw justify-end">현재가</p>
        <p className="flex w-20vw ml-3vw justify-end ">등락률</p>
      </div>
      <div className="mb-4vw border rounded-lg shadow-md ml-2vw mr-2vw">
        {data &&
          data.length > 0 &&
          data.map((stock, index) => (
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
    </>
  );
};

export default function AllStock() {
  const [stockData, setStockData] = useState([{}]);
  const [keyData, setKeyData] = useState([]);
  const [pagenation, setPagenation] = useState(1);
  const [pageSliceData, setPageSliceData] = useState([]);
  const [webstockCodes, setWebstockCodes] = useState([]);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const currentPrice = useSelector((state) => state.currentPrice.data);
  const handlePage = (event, page) => {
    setPagenation(page);
  };

  useEffect(() => {
    async function fetchData() {
      async function getStockName() {
        try {
          const response = await Instance.get(`api/v1/stock/info`);
          setStockData(response.data);
          setWebstockCodes(response.data.map((data) => data.stockCode));
          setPageSliceData(response.data.slice(0, 10));
        } catch (error) {
          console.log(error);
        }
      }

      async function getData() {
        try {
          const response = await Instance.get(`api/v1/member/private-info`);
          setKeyData(response.data.data);
        } catch (error) {
          console.log(error);
        }
      }

      async function getCurrentPrice() {
        try {
          const response = await Instance.get(`api/v1/redis/currentPrice/all`);
          dispatch(setCurrentPrice(response.data.data));
        } catch (error) {
          console.error('현재가', error);
        }
      }
      await getStockName();
      await getData();
      await getCurrentPrice();
    }
    fetchData();
  }, []);

  useEffect(() => {
    const tmp = stockData.slice(0, 10);
    setPageSliceData(tmp);
  }, [stockData]);

  useEffect(() => {
    if (stockData.length !== 0) {
      const tmp = stockData.slice((pagenation - 1) * 10, pagenation * 10);
      setPageSliceData(tmp);
    }
  }, [pagenation]);

  return (
    <div className="">
      <p className="ml-2vw mr-2vw text-[5.5vw] font-bold mb-3vw">전체 종목</p>
      <StockList
        data={pageSliceData}
        navigate={navigate}
        currentPrice={currentPrice}
      />
      <div className="flex justify-center mt-2vw font-medium">
        <Pagination
          count={5}
          color="primary"
          page={pagenation}
          onChange={handlePage}
          className="mr-2vw"
        />
      </div>
    </div>
  );
}
