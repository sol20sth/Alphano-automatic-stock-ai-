import React from 'react';
import BackTitle from '../components/common/BackTitle';
import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState, useRef, useContext } from 'react';
import { TabContext22 } from '../hooks/TabContext';
import Instance from '@/api/Instance';
import axios from 'axios';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import IMask from 'imask';

function StockOrder() {
  const [targetStockData, setTargetStockData] = useState(null);
  const [count, setCount] = useState(0);
  const [price, setPrice] = useState(0);
  const priceInputRef = useRef(null);
  const countInputRef = useRef(null);
  const navigate = useNavigate();
  const { value, setValue } = useContext(TabContext22);
  const location = useLocation();
  const { stockName, stockCode } = location.state;
  const [askPrices, setAskPrices] = useState([]);
  const [askVolumes, setAskVolumes] = useState([]);
  const [bidPrices, setBidPrices] = useState([]);
  const [bidVolumes, setBidVolumes] = useState([]);

  const buyClick = () => {
    setValue('2');
    navigate('/stoploss');
  };
  const sellOrder = () => {
    setValue('1');
    navigate('/stoploss');
  };

  useEffect(() => {
    if (priceInputRef.current) {
      const numberMask = IMask(priceInputRef.current, {
        mask: Number,
        scale: 0,
        thousandsSeparator: ',',
        padFractionalZeros: false,
        normalizeZeros: true,
        radix: '.',
        mapToRadix: ['.'],
      });

      numberMask.on('accept', () => {
        setPrice(numberMask.unmaskedValue);
      });
    }
  }, []);

  useEffect(() => {
    if (countInputRef.current) {
      const numberMask = IMask(countInputRef.current, {
        mask: Number,
        scale: 0,
        thousandsSeparator: ',',
        padFractionalZeros: false,
        normalizeZeros: true,
        radix: '.',
        mapToRadix: ['.'],
      });

      numberMask.on('accept', () => {
        setCount(numberMask.unmaskedValue || 0);
      });

      // IMask에 초기값을 설정
      numberMask.value = count.toString();
    }
  }, []);

  const increase = () => {
    const newCount = parseInt(count) + 1;
    setCount(newCount);
    if (countInputRef.current) {
      const numberMask = IMask(countInputRef.current, {
        mask: Number,
        scale: 0,
        thousandsSeparator: ',',
        padFractionalZeros: false,
        normalizeZeros: true,
        radix: '.',
        mapToRadix: ['.'],
      });
      numberMask.value = newCount.toString();
    }
  };

  const decrease = () => {
    const newCount = Math.max(0, parseInt(count) - 1);
    setCount(newCount);
    if (countInputRef.current) {
      const numberMask = IMask(countInputRef.current, {
        mask: Number,
        scale: 0,
        thousandsSeparator: ',',
        padFractionalZeros: false,
        normalizeZeros: true,
        radix: '.',
        mapToRadix: ['.'],
      });
      numberMask.value = newCount.toString();
    }
  };

  const handleInputChange = (e) => {
    const inputValue = e.target.value;
    if (inputValue >= 0) {
      setCount(inputValue);
    }
  };

  const handlePriceChange = (e) => {
    // 호가 입력 처리 함수
    setPrice(e.target.value);
  };

  const orderAmount = price * count; // 주문금액 계산

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
    async function realTimeTrade() {
      try {
        const response = await Instance.post(`api/v1/rest-api/asking-price`, {
          stockCode: stockCode,
        });
        const jsonData = JSON.parse(response.data.data.body);

        // output1의 값 가져오기
        const output1 = jsonData.output1;

        setAskPrices([
          output1.askp6,
          output1.askp5,
          output1.askp4,
          output1.askp3,
          output1.askp2,
          output1.askp1,
        ]);
        setAskVolumes([
          output1.askp_rsqn6,
          output1.askp_rsqn5,
          output1.askp_rsqn3,
          output1.askp_rsqn4,
          output1.askp_rsqn5,
          output1.askp_rsqn1,
        ]);
        setBidPrices([
          output1.bidp1,
          output1.bidp2,
          output1.bidp3,
          output1.bidp4,
          output1.bidp5,
          output1.bidp6,
        ]);
        setBidVolumes([
          output1.bidp_rsqn1,
          output1.bidp_rsqn2,
          output1.bidp_rsqn3,
          output1.bidp_rsqn4,
          output1.bidp_rsqn5,
          output1.bidp_rsqn6,
        ]);

        // 주식 코드에 해당하는 데이터를 가져옴
      } catch (error) {
        console.error('현재가', error);
      }
    }

    const intervalId = setInterval(realTimeTrade, 1000); // 3초 간격으로 함수 실행

    return () => {
      clearTimeout(intervalId); // 컴포넌트 언마운트 시 반복 중지
    };
  }, [stockCode]);

  const handleClickPrice = (price) => {
    // `price` 상태를 업데이트합니다.
    setPrice(price);

    // IMask 인스턴스의 `value`를 업데이트합니다.
    const numberMask = IMask(priceInputRef.current, {
      mask: Number,
      scale: 0,
      thousandsSeparator: ',',
      padFractionalZeros: false,
      normalizeZeros: true,
      radix: '.',
      mapToRadix: ['.'],
    });
    numberMask.value = price.toString();
  };

  return (
    <div>
      <BackTitle title={stockName} />
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
      <div className="flex justify-center h-5vh text-[5vw] font-bold mt-3vw">
        <div className="flex justify-center w-30vw">호가</div>
        <div className="flex justify-center w-30vw">잔량</div>
        <div className="flex justify-center w-30vw">주문량</div>
      </div>
      <hr className="ml-5vw w-90vw" />
      {/* 매도호가 */}
      <div className="flex flex-col items-center mt-3vw">
        <div className="flex font-bold  rounded-lg">
          <div>
            {askPrices.map((item, index) => (
              <div key={index} className="flex w-54vw h-5vh bg-[#DAF3FF]">
                <div
                  className="flex justify-center items-center w-27vw border  text-[#1A548E]"
                  onClick={() => handleClickPrice(item)}
                >
                  {Number(item).toLocaleString()}
                </div>
                <div className="flex justify-center items-center w-27vw border  text-[#1A548E]">
                  {Number(askVolumes[index]).toLocaleString()}
                </div>
              </div>
            ))}
          </div>
          <div className="flex flex-col items-center mt-1vw ml-3vw">
            <div className="flex justify-center items-center w-30vw h-4vh border rounded-lg">
              <div
                className="flex justify-center items-center w-7vw h-4vh  text-[6vw] border text-[#E7DDFF]"
                onClick={decrease}
              >
                <p>-</p>
              </div>
              <div className="flex justify-center items-center w-16vw text-[4.5vw]">
                <input
                  ref={countInputRef}
                  type="text"
                  className="w-full text-center"
                />
              </div>
              <div
                className="flex justify-center items-center w-7vw h-4vh border text-[6vw] text-[#E7DDFF]"
                onClick={increase}
              >
                <p>+</p>
              </div>
            </div>
            <div className="flex flex-col items-center mt-5vw">
              <p className="text-[5vw] font-bold">호가</p>
              <div className="mt-2vw flex justify-center items-center w-30vw h-4vh border rounded-lg">
                <div className="w-24vw text-[4.5vw] font-bold">
                  <input
                    ref={priceInputRef}
                    type="text"
                    className="w-full text-center"
                  />
                </div>
              </div>
            </div>
            <div className="flex flex-col items-center mt-5vw">
              <p className="text-[5vw] font-bold">주문금액</p>
              <div className="flex justify-center items-center w-30vw h-4vh border rounded-lg mt-2vw">
                <div className="flex justify-center items-center w-16vw text-[4.5vw] font-bold">
                  {orderAmount.toLocaleString()}
                </div>
              </div>
            </div>
          </div>
        </div>
        {/* 매수호가 */}
        <div className="flex ">
          <div className="font-bold">
            {bidPrices.map((item, index) => (
              <div key={index} className="flex w-54vw h-5vh bg-[#FFE3E1]  ">
                <div
                  className="flex justify-center items-center w-27vw border  text-[#82211A]"
                  onClick={() => handleClickPrice(item)}
                >
                  {Number(item).toLocaleString()}
                </div>
                <div className="flex justify-center items-center w-27vw border  text-[#82211A]">
                  {Number(bidVolumes[index]).toLocaleString()}
                </div>
              </div>
            ))}
          </div>
          <div className="flex ml-2vw">
            <div
              className="flex flex-col justify-center items-center w-15vw h-7vh border bg-red-500 text-white rounded-lg"
              onClick={buyClick}
            >
              <p>자동</p>
              <p>매수</p>
            </div>
            <div
              className="flex flex-col ml-1vw justify-center items-center w-15vw h-7vh border bg-blue-500 text-white rounded-lg"
              onClick={sellOrder}
            >
              <p>자동</p>
              <p>매도</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default StockOrder;
