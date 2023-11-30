import React, { useEffect, useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import Instance from '@/api/Instance';
import axios from 'axios';

function TradeChart({ stockCode }) {
  const [series, setSeries] = useState([]);
  const [stockKey, setStockKey] = useState(null);
  const [acml_vol, setacml_vol] = useState(null);

  useEffect(() => {
    async function getStock() {
      try {
        const response = await Instance.get(`api/v1/stock-daily/${stockCode}`);
        let allData = response.data.data.map((item) => ({
          x: Date.UTC(
            parseInt(item.stockDate.slice(0, 4)),
            parseInt(item.stockDate.slice(4, 6)) - 1,
            parseInt(item.stockDate.slice(6, 8)),
          ),
          y: item.todayVolume, // 거래량으로 설정
        }));

        // 오늘의 날짜를 타임스탬프로 얻기
       
        // 오늘의 거래량을 시리즈 데이터에 추가
        allData.push({ x: new Date().getTime(), y: acml_vol });

        setSeries([{ data: allData }]);
      } catch (error) {
        console.error(error);
      }
    }
    getStock();
  }, [stockCode, acml_vol]); // acml_vol이 변경될 때도 차트를 업데이트

  useEffect(() => {
    async function getCurrentPrice() {
      try {
        const response = await Instance.get(`api/v1/redis/currentPrice/all`);
        const allStocksData = response.data.data;

        // 주식 코드에 해당하는 데이터를 가져옴
        const data = allStocksData[`+${stockCode}`];
        const dataParts = data.split('/'); 
        setacml_vol(dataParts[7]); 

        
      } catch (error) {
        console.error('현재가', error);
      }
    }
    getCurrentPrice();
  }, []);


  const options = {
    chart: {
      type: 'bar', // 차트 타입을 'bar'로 설정
      toolbar: {
        show: true,
        tools: {
          download: false,
          selection: false,
          zoom: false,
          pan: false,
        },
      },
      zoom: {
        enabled: true,
        type: 'x',
        resetIcon: {
          offsetX: -10,
          offsetY: 0,
          fillColor: '#fff',
          strokeColor: '#37474F',
        },
        
      },
    },
    xaxis: {
      labels: {
        style: {
          colors: 'black',
        },
        show: true,
        datetimeFormatter: {
          day: 'MM/dd',
          month: 'MM/dd',
        },
      },
      type: 'datetime',
      axisBorder: {
        show: true,
      },
      axisTicks: {
        show: true,
      },
      range: 30 * 24 * 60 * 60 * 1000,
    },
    yaxis: {
      show: true,
      labels: {
        style: {
          colors: 'black',
          fontSize: '9px',
        },
        show: true,
        formatter: (value) => `${Number(value).toLocaleString()}`,
      },
    },
    dataLabels: {
      enabled: false, // 데이터 레이블 비활성화
    },
  };

  return (
    <div>
      <ReactApexChart options={options} series={series} type="bar" />{' '}
      {/* 차트 타입을 'bar'로 설정 */}
    </div>
  );
}

export default TradeChart;
