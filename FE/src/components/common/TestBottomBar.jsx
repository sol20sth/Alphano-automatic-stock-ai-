import React, { useState, useEffect, useContext } from 'react';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Paper from '@mui/material/Paper';
import { TabContext22 } from '../../hooks/TabContext';

function TestBottomBar() {

  const { bottomValue, setBottomValue, url, setUrl } = useContext(TabContext22);
  const Location = useLocation();
  const Url = Location.pathname.split('/')[1];
 
  const navigate = useNavigate();

  useEffect(() => {
    if (Url === 'home') {
      setBottomValue(0);
      if (Url !== 'home') {
        // navigate('/currentprice');
        navigate('/home');
      }
    } else if (Url === 'currentprice') {
      setBottomValue(1);
      if (Url !== 'currentprice') {
        navigate('/currentprice');
      }
    } else if (Url === 'stoploss') {
      setBottomValue(2);
      if (Url !== 'stoploss') {
        navigate('/stoploss');
      }
    } else if (Url === 'ai') {
      setBottomValue(3);
      if (Url !== 'ai') {
        navigate('/ai');
      }
    } else if (Url === 'assets') {
      setBottomValue(4);
      if (Url !== 'assets') {
        navigate('/assets');
      }
    }
  }, [Url]);

  return (
    <Paper
      sx={{
        width: '100%',
        display: 'flex',
        justifyContent: 'space-around',
        position: 'fixed',
        bottom: '0',
        height: '54px',
        left: '0',
        right: '0',
        margin: '0 auto',
        minWidth: 0,
      }}
      elevation={5}
    >
      <BottomNavigation
        sx={{
          width: '100%',
          display: 'flex',
          justifyContent: 'space-around',
          alignItems: 'center',
          minWidth: 0,
          margin: '0 auto',
          height: '54px',
        }}
        showLabels
        value={bottomValue}
        // onChange={(event, newValue) => {
        //   // if (newValue === 0) {
        //   //   navigate('/home');
        //   // } else if (newValue === 1) {
        //   //   navigate('/currentprice');
        //   // } else if (newValue === 2) {
        //   //   navigate('/stoploss');
        //   // } else if (newValue === 3) {
        //   //   navigate('/ai');
        //   // } else if (newValue === 4) {
        //   //   navigate('/assets');
        //   // }
        //   setBottomValue(newValue);
        // }}
      >
        <BottomNavigationAction
          label="홈"
          sx={{ minWidth: 0, padding: 0 }}
          onClick={() => {
            setBottomValue(0);
            navigate('/home');
          }}
        />
        <BottomNavigationAction
          label="현재가"
          sx={{ minWidth: 0, padding: 0 }}
          onClick={() => {
            setBottomValue(1);
            navigate('/currentprice');
          }}
        />
        <BottomNavigationAction
          label="자동매매"
          sx={{ minWidth: 0, padding: 0 }}
          onClick={() => {
            setBottomValue(2);
            navigate('/stoploss');
          }}
        />
        <BottomNavigationAction
          label="인공지능"
          sx={{ minWidth: 0, padding: 0 }}
          onClick={() => {
            setBottomValue(3);
            navigate('/ai');
          }}
        />
        <BottomNavigationAction
          label="잔고"
          sx={{ minWidth: 0, padding: 0 }}
          onClick={() => {
            setBottomValue(4);
            navigate('/assets');
          }}
        />
      </BottomNavigation>
    </Paper>
  );
}

export default TestBottomBar;
