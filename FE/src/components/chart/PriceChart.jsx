import React, { useEffect, useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import Instance from '@/api/Instance';
import axios from 'axios';

function PriceChart({ stockCode }) {
  const [targetStockData, setTargetStockData] = useState(null);
  const [series, setSeries] = useState([]);
  const [minY, setMinY] = useState(null);
  const [maxY, setMaxY] = useState(null);

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
          y: [item.startPrice, item.maxPrice, item.minPrice, item.endPrice],
        }));

        if (targetStockData) {
          allData.push({
            x: new Date().getTime(), // 당일 날짜
            y: [
              targetStockData.start,
              targetStockData.max,
              targetStockData.min,
              targetStockData.close,
            ],
          });
        }

        const minY = Math.min(...allData.map((item) => Math.min(...item.y)));
        const maxY = Math.max(...allData.map((item) => Math.max(...item.y)));

        setMinY(minY);
        setMaxY(maxY);

        let aiMaxData = response.data.data.map((item) => ({
          x: new Date(
            item.stockDate.slice(0, 4),
            item.stockDate.slice(4, 6) - 1,
            item.stockDate.slice(6, 8),
          ).getTime(),
          y: item.aiMaxPrice,
        }));

        let aiMinData = response.data.data.map((item) => ({
          x: new Date(
            item.stockDate.slice(0, 4),
            item.stockDate.slice(4, 6) - 1,
            item.stockDate.slice(6, 8),
          ).getTime(),
          y: item.aiMinPrice,
        }));

        setSeries([
          { name: '일봉', data: allData, type: 'candlestick' },
          { name: 'AI최고가', data: aiMaxData, type: 'line' },
          { name: 'AI최저가', data: aiMinData, type: 'line' },
        ]);
      } catch (error) {
        console.error(error);
      }
    }
    getStock();
  }, [stockCode, targetStockData]);

  useEffect(() => {
    async function getCurrentPrice() {
      try {
        const response = await Instance.get(`api/v1/redis/currentPrice/all`);
        const allStocksData = response.data.data;

        // 주식 코드에 해당하는 데이터를 가져옴
        const data = allStocksData[`+${stockCode}`];
        const dataParts = data.split('/'); // 데이터를 '/'로 분리하여 배열로 변환
        const close = dataParts[0]; // close 값 추출
        const start = dataParts[4]; // start 값 추출
        const max = dataParts[5]; // 최고가 추출
        const min = dataParts[6]; // 최저가 추출

        // close, start, max, min 값을 객체로 묶어서 상태에 저장
        setTargetStockData({ close, start, max, min });
      } catch (error) {
        console.error('현재가', error);
      }
    }
    getCurrentPrice();
  }, []);

  const options = {
    chart: {
      type: 'candlestick',
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
    plotOptions: {
      candlestick: {
        colors: {
          upward: '#DC143C',
          downward: '#4169E1',
        },
        wick: {
          useFillColor: true,
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
      min: minY, // y축 최소값 설정
      max: maxY, // y축 최대값 설정
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
      enabled: false,
    },
    stroke: {
      width: [1, 1],
    },
    legend: {
      show: false,
    },
  };

  return (
    <div>
      <ReactApexChart options={options} series={series} />
    </div>
  );
}

export default PriceChart;
