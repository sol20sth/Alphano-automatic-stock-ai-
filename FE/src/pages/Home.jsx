import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Attention from '../components/home/Attention';
import Current from '../components/home/Current';
import AllStock from '../components/home/AllStock';
import Instance from '@/api/Instance';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Autocomplete from '@mui/material/Autocomplete';
import { useNavigate } from 'react-router-dom';

function Home() {
  const [selectedTab, setSelectedTab] = useState('관심');
  const [stockNameNum, setStockNameNum] = useState('');
  const navigate = useNavigate();
  useEffect(() => {
    async function getStockName(event) {
      // event.preventDefault();
      try {
        const response = await Instance.get(`api/v1/stock/info`);
        const stockNames = response.data.map(
          (item) => `${item.stockName}/${item.stockCode}`,
        );
        setStockNameNum(stockNames);
      } catch (error) {
        console.error(error);
      }
    }
    getStockName();
  }, []);

  function handleSelectStock(data) {
    const [name, num] = data.split('/');
    navigate('/currentprice', {
      state: {
        stockName: name,
        stockCode: num,
      },
    });
  }
  function gotoHome() {
    navigate('/home');
  }
  return (
    <div className="h-89vh overflow-auto">
      <div className="mt-7vw flex justify-center ">
        <div className="flex w-94vw">
          <img
            src="/icons/apple-touch-icon-57x57.png"
            alt="Apple Touch Icon"
            onClick={gotoHome}
          />
          <Stack spacing={2} className="grid grid-cols-4 my-auto">
            <Autocomplete
              freeSolo
              id="free-solo-2-demo"
              className="w-72vw text-center"
              options={stockNameNum? stockNameNum : []}
              getOptionLabel={(option) => option}
              onChange={(event, newValue) => {
                handleSelectStock(newValue);
              }}
              renderInput={(params) => (
                <div className="flex">
                  <TextField
                    {...params}
                    InputProps={{
                      ...params.InputProps,
                      type: 'text',
                    }}
                    placeholder="종목 검색"
                  />
                </div>
              )}
            />
          </Stack>
        </div>
      </div>

      <hr className="mt-3vw w-100vw" />
      <div className="flex justify-between w-85vw mb-3vw">
        <div className="flex justify-between h-6vh mt-4vw  mb-2 mx-0 w-100vw">
          <div className="grid grid-cols-3 ml-3vw mr-3vw h-5vh font-extrabold w-100vw">
            <button
              className={` ml-3vw w-22vw  border rounded-full border-[#2B2B2B] ${
                selectedTab === '관심' ? 'bg-[#323131]' : ''
              }`}
              onClick={() => setSelectedTab('관심')}
            >
              <p
                className={`${
                  selectedTab === '관심' ? 'text-white' : 'text-[default color]'
                }`}
              >
                관심
              </p>
            </button>
            <button
              className={` ml-3vw w-22vw  border rounded-full border-[#2B2B2B] ${
                selectedTab === '최근' ? 'bg-[#323131]' : ''
              }`}
              onClick={() => setSelectedTab('최근')}
            >
              <p
                className={`${
                  selectedTab === '최근' ? 'text-white' : 'text-[default color]'
                }`}
              >
                최근
              </p>
            </button>
            <button
              className={` ml-3vw w-22vw  border rounded-full border-[#2B2B2B] ${
                selectedTab === '전체' ? 'bg-[#323131]' : ''
              }`}
              onClick={() => setSelectedTab('전체')}
            >
              <p
                className={`${
                  selectedTab === '전체' ? 'text-white' : 'text-[default color]'
                }`}
              >
                전체
              </p>
            </button>
          </div>
        </div>
      </div>

      {selectedTab === '전체' && <AllStock />}
      {selectedTab === '관심' && <Attention />}
      {selectedTab === '최근' && <Current />}
      {/* {selectedTab === '보유' && <MyStock />} */}

      {/* <BottomBar /> */}
    </div>
  );
}

export default Home;
