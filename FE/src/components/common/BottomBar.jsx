import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';

import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import ShowChartOutlinedIcon from '@mui/icons-material/ShowChartOutlined';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import DeveloperBoardOutlinedIcon from '@mui/icons-material/DeveloperBoardOutlined';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

export default function BottomBar() {
  const Location = useLocation();
  const Url = Location.pathname.split('/')[1];
  const [value, setValue] = useState('recents');
  const navigate = useNavigate();
  useEffect(() => {
    if (value === 'home') {
      navigate('/home');
    } else if (value === 'search') {
      navigate('/search');
    } else if (value === 'recommend') {
      navigate('/recommend');
    } else if (value === 'stoploss') {
      navigate('/stoploss');
    } else if (Url === 'home') {
      setValue(Url);
    } else if (Url === 'recommend') {
      setValue(Url);
    } else if (Url === 'stoploss') {
      setValue(Url);
    } else if (Url === 'search') {
      setValue(Url);
    }
  }, [value]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    // <div className="fixed bottom-2 w-full">
    // <Box sx={{ pb: 7 }}>
    <BottomNavigation
      sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }}
      value={value}
      onChange={handleChange}
    >
      <BottomNavigationAction
        label="홈"
        value="home"
        icon={<HomeOutlinedIcon />}
      />
      <BottomNavigationAction
        label="종목검색"
        value="search"
        icon={<SearchOutlinedIcon />}
      />
      <BottomNavigationAction
        label="AI추천"
        value="recommend"
        icon={<DeveloperBoardOutlinedIcon />}
      />
      <BottomNavigationAction
        label="자동매매"
        value="stoploss"
        icon={<ShowChartOutlinedIcon />}
      />
    </BottomNavigation>
    // </Box>
    // </div>
  );
}
