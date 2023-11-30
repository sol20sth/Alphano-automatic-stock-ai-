import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import dayjs from 'dayjs';
import Instance from '@/api/Instance';

function OptionSettingList(props) {
  const navigate = useNavigate();
  const [isClicked, setIsClicked] = useState(null);
  const [stateValue, setStateValue] = useState(null);

  const data = props.object;
  // 주문 수량 체결이 완료된 건지 확인하는 API

  // 주문 삭제할 때 활성화 false 값을 백엔드에 보내준다.

  const ClickStockInfo = (name) => {
    if (isClicked === name.stockName) {
      setIsClicked(null);
    } else {
      setIsClicked(name.stockName);
    }
  };
  const ClickOptionDetail = (item, title) => {

    navigate('/optionDetail', { state: { data: item, status: title } });
  };
  const moveToStoplossOptionDetail = (item) => {
    navigate('/stoplossOptionDetail', { state: item });
  };

  // const handleDeleteConfirmation = (item) => {
  //   const isConfirmed = window.confirm('삭제하시겠습니까?');
  //   if (isConfirmed) {
  //     // 확인 버튼이 눌렸을 때만 axios 요청 보내기
  //     orderDelete(item.memberOrderStockNickname);
  //   }
  // };
  // const orderDelete = async(nickName) => {
    
  //   try {
  //     const response = await Instance.delete(
  //       `api/v1/member/order/stock/delete?memberOrderStockNickname=${nickName}`,
  //     );
      
  //   } catch (error) {
  //     console.log(error);
  //     if(error.response.data.status === 200){
  //       window.location.reload();
  //     }
  //   }
  // };

  return (
    <div>
      {props.object.length ? (
        props.object.map((item, index) => (
          <div key={index}>
            {item.isOrdered !== 'SUCCESS' ? (
              <div
                type="button"
                className={`border-b border-t border-r border-l rounded-md border-gray-500 mb-2
                  // item.isOrdered === 'SUCCESS' ? 'disabled' : ''
                `}
                onClick={() =>
                  ClickStockInfo(item)
                }
                aria-hidden
              >
                <div className="flex justify-between">
                  <div className="flex gap-2 m-2">
                    <div className="text-l font-semibold">
                      {item.memberOrderStockNickname}
                    </div>
                    <div className="text-xs flex text-gray-400 items-end">
                      {item.stockName}
                    </div>
                  </div>
                  {item.isOrdered === 'SUCCESS' ? (
                    <div className="flex justify-end items-end m-2 text-xs text-green-600">
                      완료
                    </div>
                  ) : (
                    <div className="flex justify-end items-end m-2 text-xs text-red-500">
                      진행중
                    </div>
                  )}
                </div>
                <div className="flex items-center justify-between gap-5 m-2 mt-5 ">
                  <div className="flex w-1/2 justify-between">
                    <div className="text-xs flex text-gray-500 items-end">
                      목표수량 :{' '}
                    </div>
                    <div className="text-xs flex text-gray-900 items-end font-semibold">
                    {item.count >item.orderCount ? item.orderCount : item.count}주 / {item.orderCount}주
                    </div>
                  </div>
                  <div className="flex w-1/2 justify-between items-center">
                    <div className="text-xs flex text-gray-500 items-center">
                      설정날짜 :
                    </div>
                    <div className="text-xs flex text-gray-900 items-center font-semibold">
                      {/* {item.startTime} */}
                      {dayjs(item.startTime).format('YYYY-MM-DD')}
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div
                type="button"
                className={`border-b border-t border-r border-l rounded-md border-gray-200 mb-2`}
                onClick={() =>
                  // item.isOrdered !== 'SUCCESS' ? ClickStockInfo(item) : null
                  ClickStockInfo(item)
                }
                aria-hidden
              >
                <div className="flex justify-between">
                  <div className="flex gap-2 m-2">
                    <div className="text-l font-semibold text-gray-400">
                      {item.memberOrderStockNickname}
                    </div>
                    <div className="text-xs flex text-gray-400 items-end">
                      {item.stockName}
                    </div>
                  </div>
                  {item.isOrdered === 'SUCCESS' ? (
                    <div className="flex justify-end items-end m-2 text-xs rounded-xl text-green-600 p-2">
                      완료
                    </div>
                  ) : (
                    <div className="flex justify-end items-end m-2 text-xs text-red-500">
                      진행중
                    </div>
                  )}
                </div>
                <div className="flex items-center justify-between gap-5 m-2 mt-5 ">
                  <div className="flex w-1/2 justify-between">
                    <div className="text-xs flex text-gray-500 items-end">
                      목표수량 :{' '}
                    </div>
                    <div className="text-xs flex text-gray-500 items-end font-semibold">
                      {item.count >item.orderCount ? item.orderCount : item.count}주 / {item.orderCount}주
                    </div>
                  </div>
                  <div className="flex w-1/2 justify-between items-center">
                    <div className="text-xs flex text-gray-500 items-center">
                      설정날짜 :
                    </div>
                    <div className="text-xs flex text-gray-500 items-center font-semibold">
                      {/* {item.startTime} */}
                      {dayjs(item.startTime).format('YYYY-MM-DD')}
                    </div>
                  </div>
                </div>
              </div>
            )}
            {/* <div
              type="button"
              className={
                `border-b border-t border-r border-l rounded-md text-center h-4vh justify-center my-auto bg-stone-200
                border-gray-500 mb-2 
                // item.isOrdered === 'SUCCESS' ? 'disabled' : ''`}
                onClick={() => handleDeleteConfirmation(item)}
            >
              삭제
            </div> */}
            
            <div
              className={`flex justify-center gap-5 mb-5 ${
                isClicked === item.stockName ? '' : 'hidden'
              }`}
            >
              {item.isOrdered === 'SUCCESS' ? (
                <button
                  className="w-36 mt-4 border-2 border-blue-500 bg-white hover:bg-blue-400 text-blue-500 font-bold py-2 px-4 rounded"
                  type="button"
                  onClick={() => moveToStoplossOptionDetail(item)}
                >
                  내역보기
                </button>
              ) : (
                <div className="flex justify-center gap-5 mb-2">
                  <button
                    className="w-36 mt-4 border-2 border-blue-500 bg-white hover:bg-blue-400 text-blue-500 font-bold py-2 px-4 rounded"
                    type="button"
                    onClick={() => moveToStoplossOptionDetail(item)}
                  >
                    상세내역
                  </button>
                  <button
                    className="w-36 mt-4 border-2 border-blue-500 bg-white hover:bg-blue-400 text-blue-500 font-bold py-2 px-4 rounded"
                    type="button"
                    onClick={() => ClickOptionDetail(item, props.title)}
                  >
                    수정하기
                  </button>
                </div>
              )}
              
            </div>
          </div>
        ))
      ) : (
        <div className="mt-4 text-center text-gray-500">
          옵션정보가 없습니다.
        </div>
      )}
      
    </div>
  );
}

export default OptionSettingList;
