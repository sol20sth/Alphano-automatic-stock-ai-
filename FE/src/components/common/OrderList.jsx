import React, { useState, useEffect, useContext } from 'react';
import OptionSettingList from './OptionSettingList';
import Instance from '@/api/Instance';
import AutorenewOutlinedIcon from '@mui/icons-material/AutorenewOutlined';
import { TabContext22 } from '@/hooks/TabContext';
import IconButton from '@mui/material/IconButton';
import { useDispatch } from 'react-redux';

function OrderList() {
  const { value, setValue } = useContext(TabContext22);
  // const [value, setData] = useState();
  const [sellOption, setSellOption] = useState([]);
  const [buyOption, setBuyOption] = useState([]);
  const [toggle, setToggle] = useState(true);
  const sellList = [];
  const buyList = [];

  useEffect(() => {
    const getStockName = async () => {
      try {
        const response = await Instance.get(`api/v1/member/order/stock`);
        const updatedSellOption = [...sellList];
        const updatedBuyOption = [...buyList];
        response.data.data.map((item) => {
          if (item.buySell === 'S') {
            // 매도
            updatedSellOption.push(item);
            setSellOption(updatedSellOption);
          } else if (item.buySell === 'B') {
            // 매수
            updatedBuyOption.push(item);
            setBuyOption(updatedBuyOption);
          }
        });
      } catch (error) {
        console.error('종목명 받아오기: ', error);
      }
    };
    // 최초 1회 실행
    getStockName();
    // 10초마다 실행
    const interval = setInterval(() => {
      getStockName();
    }, 5000);
    // 컴포넌트가 언마운트되면 인터벌 클리어
    return () => clearInterval(interval);
  }, [value, toggle]);

  return (
    <div>
      {(buyOption.length === 0) & (sellOption.length === 0) ? (
        <div className="mt-4 text-center text-gray-500">
          현재 주문내역 정보가 없습니다.
        </div>
      ) : (
        <div>
          <div className="flex justify-between mb-4vw">
            <div className="text-lg font-bold my-auto">매수 옵션 설정 목록 </div>
            <IconButton onClick={() => setToggle(!toggle)}>
              <div
                className="flex justify-center items-center"
                style={{ fontSize: '5vw' }}
              >
                새로고침
              </div>
              <AutorenewOutlinedIcon style={{ fontSize: '5vw' }} />
            </IconButton>
          </div>

          <OptionSettingList object={buyOption} title="B" />
          <div className="text-l font-bold mt-8 mb-3">매도 옵션 설정 목록 </div>
          <OptionSettingList object={sellOption} title="S" />
        </div>
      )}
    </div>
  );
}

export default OrderList;
