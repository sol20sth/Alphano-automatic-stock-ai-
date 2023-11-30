import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import BackTitle from '../components/common/BackTitle';
import dayjs from 'dayjs';
import Button from '@mui/material/Button';
import Instance from '@/api/Instance';
// import Alert from '../components/common/AlertCompo';

import { TabContext22 } from '@/hooks/TabContext';

function StopLossOptionDetailPage() {
  const { value, setValue } = useContext(TabContext22);
  const [modalOpen, setModalOpen] = useState(false);

  const closeModal = () => {
    setModalOpen(!modalOpen);
  };

  const DeleteOption = async () => {
    try {
      const response = await Instance.delete(
        `api/v1/member/order/stock/delete?memberOrderStockNickname=${data.memberOrderStockNickname}`,
      );
      console.log('성공', response);
    } catch (error) {
      if (error.message === 'Request failed with status code 302') {
        navigate('/stoploss');
      }
      console.log('에러', error);
    }
  };
  const location = useLocation();
  const navigate = useNavigate();
  const data = location.state;
  console.log('data', data);
  const ClickToDelete = () => {
    const optionData = data;
    // optionData.active = false;
    delete optionData.isActive;
    console.log(optionData);
    const postCreateOption = async (event) => {
      console.log(data, '데이터 확인요');
      try {
        const response = await Instance.post(`api/v1/member/order/stock`, data);
        console.log(response.data, '만들기');
      } catch (error) {
        console.log(data);
        console.error('만들기 에러: ', error);
      }
    };
    postCreateOption();
    navigate('/stoploss', { state: setValue('3') });
  };

  console.log('data : ', data);
  return (
    <div>
      <BackTitle title="옵션 내역 상세보기" />
      <div className="p-4">
        <div
          type="button"
          className={`border-b border-t border-r border-l rounded-md border-gray-500 mb-2`}
          // onClick={() => ClickStockInfo(item)}
          aria-hidden
        >
          <div className="flex justify-between">
            <div className="flex gap-2 m-2">
              <div className="text-l font-semibold">{data.stockName}</div>
              <div className="text-xs flex text-gray-400 items-end">
                {data.memberOrderStockNickname}
              </div>
            </div>
            {/* {data.isOrdered === 'SUCCESS' ? (
            <div className="flex justify-end items-end m-2 text-xs text-green-600">
              완료
            </div>
          ) : (
            <div className="flex justify-end items-end m-2 text-xs text-red-500">
              진행중
            </div>
          )} */}
          </div>
          <div className="flex items-center justify-between gap-5 m-2 mt-5 ">
            <div className="flex w-1/2 justify-between items-center">
              <div className="text-xs flex text-gray-500 items-center">
                설정날짜 :
              </div>
              <div className="text-xs flex text-gray-900 items-center font-semibold">
                {dayjs(data.startTime).format('YYYY-MM-DD')}
              </div>
            </div>
            <div className="flex w-1/2 justify-between items-center">
              <div className="text-xs flex text-gray-500 items-center">
                마감날짜 :
              </div>
              <div className="text-xs flex text-gray-900 items-center font-semibold">
                {dayjs(data.endTime).format('YYYY-MM-DD')}
              </div>
            </div>
          </div>
        </div>
        <p className="text-lg font-semibold mb-4 mt-6">옵션조건</p>
        <div className="border border-black p-4 rounded-md">
          <div className="mb-4 flex justify-center">
            <p className="text-lg text-black font-semibold p-2 rounded-lg">
              {data.targetPriceCondition === 'T' ? '지정가' : '시장가'}
            </p>
          </div>

          <div className="mb-4 flex justify-between items-center border-b-5 border-gray-200 pb-2">
            <p className="text-lg font-semibold">감시 가격:</p>
            <div className="flex gap-1 text-lg">
              <div>{data.targetPrice.toLocaleString()}원</div>
              <div className="text-gray-500">
                {data.targetPriceOptionInequality === 'GREATER'
                  ? '이상'
                  : '이하'}
              </div>
            </div>
          </div>

          <div className="mb-4 flex justify-between items-center">
            <p className="text-lg font-semibold">주문가격 :</p>
            <p className="text-lg font-semibold">
              {data.targetOrderPrice === -1
                ? '시장가'
                : `${data.targetOrderPrice.toLocaleString()}원`}
            </p>
          </div>

          <div className="mb-4 flex justify-between items-center">
            <p className="text-lg font-semibold">주문수량 :</p>
            <p className="text-lg font-semibold">{data.orderCount}주</p>
          </div>

          <div className="mb-4 flex justify-between items-center">
            <p className="text-lg font-semibold">마감날짜 :</p>
            <div>{dayjs(data.endTime).format('YYYY-MM-DD')}</div>
          </div>

          {data.memberOptions && (
            <div>
              {data.memberOptions.length === 2 ? (
                <div>
                  <div className="mb-4 flex justify-between items-center">
                    <p className="text-lg font-semibold">RSI : </p>
                    <div className="flex gap-5 items-center text-lg">
                      <p>
                        {data.memberOptions[0]?.optionValue.toLocaleString()}%
                      </p>
                      <p>
                        {data.memberOptions[0]?.optionInequality === 'LESS'
                          ? '미만'
                          : '이상'}{' '}
                        일 때
                      </p>
                    </div>
                  </div>

                  <div className="mb-4 flex justify-between items-center">
                    <p className="text-lg font-semibold">MACD : </p>
                    <div className="flex gap-5 items-center text-lg">
                      <p>
                        {data.memberOptions[1]?.optionValue.toLocaleString()}%
                      </p>
                      <p>
                        {data.memberOptions[1]?.optionInequality === 'LESS'
                          ? '미만'
                          : '이상'}{' '}
                        일 때
                      </p>
                    </div>
                  </div>
                </div>
              ) : data.memberOptions.length === 1 ? (
                <div className="mb-4 flex justify-between items-center">
                  <p className="text-lg font-semibold">
                    {data.memberOptions[0]?.optionName} :{' '}
                  </p>
                  <div className="flex gap-5 items-center text-lg">
                    <p>{data.memberOptions[0]?.optionValue}%</p>
                    <p>
                      {data.memberOptions[0]?.optionInequality === 'LESS'
                        ? '미만'
                        : '이상'}{' '}
                      일 때
                    </p>
                  </div>
                </div>
              ) : null}
            </div>
          )}
        </div>

        <div className="flex justify-center items-center mt-7">
          {data.isOrdered === 'SUCCESS' ? (
            <Button
              variant="outlined"
              color="primary"
              sx={{ width: '100%' }}
              onClick={() => navigate('/stoploss', { state: setValue('3') })}
            >
              뒤로가기
            </Button>
          ) : (
            <Button
              variant="outlined"
              color="error"
              sx={{ width: '100%' }}
              onClick={closeModal}
            >
              삭제하기
            </Button>
          )}
          {modalOpen && (
            <div className="flex justify-center items-center fixed top-0 left-0 w-full h-full bg-gray-500 bg-opacity-50 z-50">
              <div className="bg-white p-6 rounded-lg">
                <div className="mb-4">정말 삭제하시겠습니까?</div>
                <div className="mb-4">다시 복구할 수 없습니다.</div>
                <div className="flex justify-center items-center gap-3">
                  <div
                    className="border border-gray-600 p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-center"
                    onClick={closeModal}
                  >
                    취소
                  </div>
                  <div
                    className="text-center border p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-cente bg-red-500  text-white"
                    onClick={DeleteOption}
                  >
                    삭제
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default StopLossOptionDetailPage;