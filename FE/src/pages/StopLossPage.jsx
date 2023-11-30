import React, { useState, useContext } from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import TabContext from '@mui/lab/TabContext';
import Title from '@/components/common/Title';
import OrderList from '../components/common/OrderList';
// import SellStopLoss from '../components/common/SellStopLoss';
import BuyStopLoss from '../components/common/BuyStopLoss';
// import StopLossMenu from '../components/common/StopLossMenu';
import OwnStockList from '../components/common/OwnStockList';
import { TabContext22 } from '../hooks/TabContext';
import BackTitle from '../components/common/BackTitle';
function StopLoss() {
  const { value, setValue } = useContext(TabContext22);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div>
      <div>
        <BackTitle title="자동매매" />
      </div>
      <Box sx={{ width: '100%', typography: 'body1' }}>
        <TabContext value={value}>
          <Box
            sx={{
              borderBottom: 1,
              borderColor: 'divider',
            }}
          >
            <TabList onChange={handleChange} aria-label="lab API tabs example">
              <Tab label="매도감시" value="1" />
              <Tab label="매수감시" value="2" />
              <Tab label="주문내역" value="3" />
            </TabList>
          </Box>
          {/* <div className="bg-[#F7F7F7]"> */}
          <div className=" pb-14 p-1">
            <TabPanel value="1">
              <div>
                <div className="">
                  <OwnStockList />
                </div>
              </div>
            </TabPanel>
            <TabPanel value="2">
              <div>
                <div className="">
                  <BuyStopLoss />
                </div>
              </div>
            </TabPanel>
            <TabPanel value="3">
              <div>
                <OrderList />
              </div>
            </TabPanel>
          </div>
        </TabContext>
        {/* <BottomBar /> */}
      </Box>
    </div>
  );
}
export default StopLoss;
