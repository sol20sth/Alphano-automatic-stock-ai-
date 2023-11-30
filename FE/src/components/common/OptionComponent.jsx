import React, { useState, useContext } from 'react';
import { TabContext22 } from '@/hooks/TabContext';
import { useLocation } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { DemoItem } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Switch from '@mui/material/Switch';
import toast, { Toaster } from 'react-hot-toast';

import FormControlLabel from '@mui/material/FormControlLabel';
// import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Instance from '@/api/Instance';
import HelpOutlineOutlinedIcon from '@mui/icons-material/HelpOutlineOutlined';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

function OptionComponent(props) {
  const [modalOpen, setModalOpen] = useState(false);
  const { value, setValue } = useContext(TabContext22);
  const closeModal = () => {
    setModalOpen(!modalOpen);
  };
  const navigate = useNavigate();

  const [calendarToggle, setCalendarToggle] = useState(false);
  const handleCalenderToggle = () => {
    setCalendarToggle(!calendarToggle);
  };
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const location = useLocation();
  const selectedObject = location.state.data;
  const option = location.state.status;
  const stockName = selectedObject.stockName;
  const stockNickName = selectedObject.memberOrderStockNickname;
  const BSType = selectedObject.buySell;

  const targetPriceOptionInequality =
    selectedObject.targetPriceOptionInequality;
  const [addOptions, setAddOptions] = useState([false, false]);
  const [rsiPercent, setRsiPercent] = useState('');
  const [rsiPriceType, setRsiPriceType] = useState('');
  const [macdPercent, setMacdPercent] = useState('');
  const [macdPriceType, setMacdPriceType] = useState('');

  const color1 = rsiPriceType === 'GREATER' ? 'bg-indigo-400' : 'bg-white';
  const color2 = rsiPriceType === 'LESS' ? 'bg-indigo-400' : 'bg-white';
  const color3 = macdPriceType === 'GREATER' ? 'bg-indigo-400' : 'bg-white';
  const color4 = macdPriceType === 'LESS' ? 'bg-indigo-400' : 'bg-white';

  const [watchPrice, setWatchPrice] = useState(selectedObject.targetPrice);
  const [moreorless, setMoreorless] = useState(targetPriceOptionInequality);
  const [priceType, setPriceType] = useState(
    selectedObject.targetPriceCondition,
  );
  const [orderPrice, setOrderPrice] = useState(selectedObject.targetPrice);
  const [orderNum, setOrderNum] = useState(selectedObject.orderCount);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  // const [realStartDate, setRealStartDate] = useState(dayjs(startDate).format('YYYY-MM-DD'));
  const today = dayjs();

  const DeleteOption = async () => {
    try {
      const response = await Instance.delete(
        `api/v1/member/order/stock/delete?memberOrderStockNickname=${stockNickName}`,
      );
      navigate('/stoploss', { state: setValue('3') });
    } catch (error) {
      console.log('error');
      navigate('/stoploss', { state: setValue('3') });
    }
  };

  function handleChangeAddOptions(i) {
    const newAddOptions = [...addOptions]; // 복사해서 변경
    newAddOptions[i] = !newAddOptions[i]; // 상태를 토글
    setAddOptions(newAddOptions); // 변경된 상태를 적용
  }
  const optionList = selectedObject.memberOptions;
  useEffect(() => {
    if (optionList.length === 2) {
      const updatedOptions = [...addOptions]; // addOptions 배열 복사
      updatedOptions[0] = true; // 첫 번째 요소를 true로 설정
      updatedOptions[1] = true; // 첫 번째 요소를 true로 설정
      setAddOptions(updatedOptions);
      setRsiPercent(optionList[0].optionValue);
      setRsiPriceType(optionList[0].optionInequality);
      setMacdPercent(optionList[1].optionValue);
      setMacdPriceType(optionList[1].optionInequality);
    } else if (optionList.length === 1) {
      if (optionList[0].optionName === 'RSI') {
        const updatedOptions = [...addOptions]; // addOptions 배열 복사

        updatedOptions[0] = true; // 첫 번째 요소를 true로 설정
        setAddOptions(updatedOptions);
        setRsiPercent(optionList[0].optionValue);
        setRsiPriceType(optionList[0].optionInequality);
      } else if (optionList[0].optionName === 'MACD') {
        const updatedOptions2 = [...addOptions]; // addOptions 배열 복사
        updatedOptions2[1] = true; // 첫 번째 요소를 true로 설정
        setAddOptions(updatedOptions2);
        setMacdPercent(optionList[0].optionValue);
        setMacdPriceType(optionList[0].optionInequality);
      }
    }
  }, []);
  function handleRsiPercent(e) {
    setRsiPercent(e.target.value);
  }
  function handleRsiPriceType(data) {
    setRsiPriceType(data);
  }
  function handleMacdPercent(e) {
    setMacdPercent(e.target.value);
  }
  function handleMacdPriceType(data) {
    setMacdPriceType(data);
  }
  function handleCreateBuyOptions() {
    if (checked) {
    } else {
      if (!stockName) {
        toast.error('올바른 종목명을 선택하세요.');
      } else if (!stockNickName) {
        toast.error('중복되지 않은 옵션명을 다시 작성해 주세요!');
      } else if (!watchPrice) {
        toast.error('감시 가격을 입력하세요.');
      } else if (!moreorless) {
        toast.error('감시 가격 옵션을 선택하세요.');
      } else if (!priceType) {
        toast.error('가격 유형을 선택하세요.');
      } else if (priceType === 'T' && !orderPrice) {
        toast.error('지정 주문 가격을 입력하세요.');
      } else if (!orderNum) {
        toast.error('주문 수량을 입력하세요.');
      } else {
        const memberOptions = [];
        if (addOptions[0]) {
          memberOptions.push({
            optionValue: rsiPercent ? rsiPercent * 1 : '',
            optionInequality: rsiPriceType === 'GREATER' ? 'GREATER' : 'LESS',
            optionName: 'RSI',
          });
        }
        if (addOptions[1]) {
          memberOptions.push({
            optionValue: macdPercent ? macdPercent * 1 : '',
            optionInequality: macdPriceType === 'GREATER' ? 'GREATER' : 'LESS',
            optionName: 'MACD',
          });
        }
        if (!checked === true) {
          const data = {
            // stockCode: selectStockCode ? selectStockCode : '',
            // stockCode: '5930',
            memberOrderStockNickname: stockNickName,
            stockName: stockName ? stockName : '',
            buySell: BSType,
            isOrdered: 'WAIT',
            // startTime: startDate ? startDate + 'T09:00:00.154Z' : '',
            // startTime: '"2023-11-03T10:52:13.154Z"',
            endTime: endDate
              ? endDate + 'T18:00:00.154Z'
              : selectedObject.endTime,
            // endTime: '2023-11-03T18:00:00.154Z',
            orderCount: orderNum ? orderNum * 1 : '',
            targetPrice: priceType === 'M' ? watchPrice * 1 : watchPrice * 1,
            targetPriceCondition: priceType === 'M' ? 'M' : 'T',
            targetPriceOptionInequality:
              moreorless === 'GREATER' ? 'GREATER' : 'LESS',
            targetOrderPrice: priceType === 'M' ? -1 : orderPrice * 1,
            memberOptions: memberOptions,
            // active: false,
          };
          const postCreateOption = async (event) => {
            try {
              const response = await Instance.post(
                `api/v1/member/order/stock/update`,
                data,
              );
              navigate('/stoploss', { state: setValue('3') });
            } catch (error) {
              console.error('만들기 에러: ', error);
            }
          };
          postCreateOption();
        } else {
          const data = {
            // stockCode: selectStockCode ? selectStockCode : '',
            // stockCode: '5930',
            memberOrderStockNickname: stockNickName,
            stockName: stockName ? stockName : '',
            buySell: BSType,
            isOrdered: 'WAIT',
            // startTime: startDate ? startDate + 'T09:00:00.154Z' : '',
            // startTime: '"2023-11-03T10:52:13.154Z"',
            endTime: endDate
              ? endDate + 'T18:00:00.154Z'
              : selectedObject.endTime,
            // endTime: '2023-11-03T18:00:00.154Z',
            orderCount: orderNum ? orderNum * 1 : '',
            targetPrice: priceType === 'M' ? watchPrice * 1 : watchPrice * 1,
            targetPriceCondition: priceType === 'M' ? 'M' : 'T',
            targetPriceOptionInequality:
              moreorless === 'GREATER' ? 'GREATER' : 'LESS',
            targetOrderPrice: priceType === 'M' ? -1 : orderPrice * 1,
            memberOptions: memberOptions,
            // active: true,
          };
          const postCreateOption = async (event) => {
            try {
              const response = await Instance.post(
                `api/v1/member/order/stock/update`,
                data,
              );
              navigate('/stoploss', { state: setValue('3') });
            } catch (error) {
              console.error('만들기 에러: ', error);
            }
          };
          postCreateOption();
        }
      }
    }
  }

  const handleMoreorlessChange = (event) => {
    setMoreorless(event.target.value);
  };
  function onChangePriceType(type) {
    setPriceType(type);
    if (type === 'M') {
      setOrderPrice('M');
    } else {
      setOrderPrice('');
    }
  }
  function onChangeWatchPrice(e) {
    setWatchPrice(e.target.value);
  }
  function onChangePrice(e) {
    setOrderPrice(e.target.value);
  }

  function onChangeNum(e) {
    setOrderNum(e.target.value);
  }

  function onChangeEndDate(date) {
    setEndDate(date);
    // setUpdateDate(date);
  }

  const [checked, setChecked] = React.useState(false);

  const handleChange = () => {
    setChecked((prev) => !prev);
  };
  return (
    <div>
      <div className={`relative pb-14 p-1`}>
        {!checked && (
          <div className="p-7">
            <div className="text-l font-bold mb-3">
              {selectedObject.memberOrderStockNickname}{' '}
            </div>
            <div
              // type="button"
              className={`border-b border-t border-r border-l rounded-md border-gray-500 mb-2`}
              // onClick={() => CLickStockInfo(item.name)}
              aria-hidden
            >
              <div className="flex gap-2 m-2">
                <div className="text-l font-semibold">
                  {selectedObject.stockName}
                </div>
                <div className="text-xs flex text-gray-400 items-end">현금</div>
              </div>
              <div className="flex items-center justify-between gap-5 m-2 mt-5 ">
                <div className="flex w-1/2 justify-between">
                  <div className="text-xs flex text-gray-500 items-end">
                    현재 체결 수 :
                  </div>
                  <div className="text-xs flex text-gray-900 items-end font-semibold">
                    {selectedObject.count}주
                  </div>
                </div>
                <div className="flex w-1/2 justify-between items-center">
                  <div className="text-xs flex text-gray-500 items-center">
                    주문 주식 수 :
                  </div>
                  <div className="text-xs flex text-gray-900 items-center font-semibold">
                    {/* {selectedObject.averagePurchasePrice.toLocaleString()}원 */}
                    {selectedObject.orderCount}주
                  </div>
                </div>
              </div>
            </div>
            <div>
              <p className="text-xs mb-2 mt-6 font-bold">필수조건</p>
              <div className="border-solid border-2 border-black-600 p-1">
                <div className="flex pt-3 mb-3">
                  <p className="text-xs w-20vw mt-3 ml-2vw">감시 가격: </p>
                  <TextField
                    id="outlined-number"
                    label="가격"
                    type="number"
                    value={watchPrice}
                    onChange={onChangeWatchPrice}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    className="w-30vw"
                  />
                  <p className="text-xs mt-3 mx-1"> 원 </p>
                  <div className="w-30vw">
                    <FormControl fullWidth>
                      <InputLabel className="text-xs" shrink={true}>
                        옵션
                      </InputLabel>
                      <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={moreorless}
                        label="Age"
                        onChange={handleMoreorlessChange}
                      >
                        <MenuItem value={'GREATER'}>이상</MenuItem>
                        <MenuItem value={'LESS'}>미만</MenuItem>
                      </Select>
                    </FormControl>
                  </div>
                </div>

                <div className="mt-5">
                  <div className="flex justify-between pb-4">
                    <p
                      className={`ml-15vw ${
                        priceType === 'T' ? 'text-black' : 'text-gray-400'
                      }`}
                      onClick={() => onChangePriceType('T')}
                    >
                      지정가
                    </p>
                    <p
                      className={`mr-15vw ${
                        priceType === 'M' ? 'text-black' : 'text-gray-400'
                      }`}
                      onClick={() => onChangePriceType('M')}
                    >
                      시장가
                    </p>
                  </div>
                  {priceType === 'T' ? (
                    <>
                      <div className="flex mb-2vw">
                        <p className="text-xs m-auto ml-2vw">주문가격 :</p>
                        <input
                          className="w-40vw border-solid border-2 border-black-600 text-center place-content-center"
                          type="number"
                          value={orderPrice}
                          onChange={onChangePrice}
                        />
                        <p className="text-xs m-auto ">원</p>
                      </div>
                    </>
                  ) : null}

                  <div className="flex mb-2vw">
                    <p className="text-xs m-auto ml-2vw">주문수량 :</p>
                    <input
                      className="w-40vw border-solid border-2 border-black-600 text-center place-content-center"
                      type="number"
                      value={orderNum}
                      onChange={onChangeNum}
                    />
                    <p className="text-xs m-auto ">개</p>
                  </div>

                  <div className="mb-2vw">
                    <div className="">
                      <p className="text-xs mb-1vw ml-2vw">
                        마감날짜(해당날까지 적용)
                      </p>
                    </div>
                    {!calendarToggle ? (
                      <div className="flex justify-center items-center gap-2">
                        <div className="relative flex justify-center items-center border p-2 rounded-lg w-3/5">
                          <div className="font-medium">
                            {dayjs(selectedObject.endTime).format('YYYY-MM-DD')}
                          </div>
                        </div>
                        <Button
                          variant="outlined"
                          onClick={handleCalenderToggle}
                        >
                          수정하기
                        </Button>
                      </div>
                    ) : (
                      <LocalizationProvider dateAdapter={AdapterDayjs} >
                        <Box
                          sx={{
                            width: '100%',
                            // height: '100%',
                            display: 'flex',
                            justifyContent: 'center',
                            position: 'relative',
                          }}
                        >
                          <DemoItem>
                            <DatePicker
                              sx={{ width: 260 }}
                              slotProps={{
                                textField: {
                                  size: 'small',
                                },
                                field: {
                                  clearable: true,
                                },
                              }}
                              minDate={startDate ? dayjs(startDate) : today}
                              format="YYYY-MM-DD"
                              value={endDate}
                              onChange={(date) => {
                                const formattedDate =
                                  dayjs(date).format('YYYY-MM-DD');
                                onChangeEndDate(formattedDate);
                              }}
                            />
                          </DemoItem>
                        </Box>
                      </LocalizationProvider>
                    )}
                  </div>
                </div>
              </div>
            </div>
            <p className="text-xs mb-2 mt-6">추가조건</p>
            <div className="border-solid border-2 border-black-600 p-1">
              <div className="flex ">
                <Checkbox
                  checked={addOptions[0]}
                  onChange={() => handleChangeAddOptions(0)}
                />

                <div className="flex justify-center items-center text-lg text-center">
                  RSI
                </div>
                <div
                  className="flex justify-center items-center ml-1"
                  onClick={handleOpen}
                >
                  <HelpOutlineOutlinedIcon
                    sx={{
                      width: '17px',
                      height: '17px',
                      color: 'gray',
                      display: 'flex',
                      justifyContent: 'center',
                      alignItems: 'center',
                      marginTop: '4px',
                    }}
                  />
                </div>
              </div>

              {addOptions[0] === true && (
                <div className="flex justify-around ml-13vw">
                  <div className="flex">
                    <input
                      value={rsiPercent}
                      onChange={handleRsiPercent}
                      className="h-5vh w-17vw my-auto border-solid border-2 border-black-400 rounded text-center"
                      type="number"
                    />
                    <p className="text-xs my-auto px-1"> % </p>
                  </div>
                  <div className="flex items-center">
                    <div className="flex flex-col items-center">
                      <button
                        className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color1}`}
                        onClick={() => handleRsiPriceType('GREATER')}
                      >
                        이상
                      </button>
                      <button
                        className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color2}`}
                        onClick={() => handleRsiPriceType('LESS')}
                      >
                        미만
                      </button>
                    </div>
                    <p className="text-xs my-auto px-1">일 때</p>
                  </div>
                </div>
              )}

              <div className="flex mb-3">
                <Checkbox
                  checked={addOptions[1]}
                  onChange={() => handleChangeAddOptions(1)}
                />
                <div className="flex justify-center items-center text-lg text-center">
                  MACD{' '}
                </div>
                <div className="flex justify-center items-center ml-1">
                  <HelpOutlineOutlinedIcon
                    sx={{
                      width: '17px',
                      height: '17px',
                      color: 'gray',
                      display: 'flex',
                      justifyContent: 'center',
                      alignItems: 'center',
                      marginTop: '4px',
                    }}
                  />
                </div>
              </div>
              {addOptions[1] === true && (
                <div className="flex ml-13vw justify-around">
                  <div className="flex">
                    <input
                      value={macdPercent}
                      onChange={handleMacdPercent}
                      className="h-5vh w-17vw my-auto border-solid border-2 border-black-400 rounded text-center"
                      type="number"
                    />
                    <p className="text-xs my-auto px-1"> % </p>
                  </div>
                  <div className="flex items-center">
                    <div className="flex flex-col">
                      <button
                        className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color3}`}
                        onClick={() => handleMacdPriceType('GREATER')}
                      >
                        이상
                      </button>
                      <button
                        className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color4}`}
                        onClick={() => handleMacdPriceType('LESS')}
                      >
                        미만
                      </button>
                    </div>
                    <div className="text-xs my-auto px-1">일 때</div>
                  </div>
                </div>
              )}
            </div>
          </div>
        )}

        {/* 추가옵션 */}
        <div className="flex justify-center items-center mb-4">
          <Box sx={{}}>
            <FormControlLabel
              control={<Switch checked={checked} onChange={handleChange} />}
              label="삭제"
            />
            <Box sx={{ display: 'flex' }}>
              {/* <Fade in={checked}>{icon}</Fade> */}
            </Box>
          </Box>
        </div>
        {/* 버튼 */}
        {!checked ? (
          <div className="flex justify-center gap-5 items-center">
            <button
              className="w-36 mb-5 border-2 border-blue-500 bg-white text-blue-500 font-bold py-2 px-4 rounded"
              type="button"
              onClick={() => navigate(-1)}
            >
              {checked ? '확인' : '뒤로가기'}
            </button>
            <button
              className="w-36 mb-5 border-2 border-blue-500 bg-white text-blue-500 font-bold py-2 px-4 rounded"
              type="button"
              onClick={() => closeModal()}
            >
              {checked ? '확인' : '수정하기'}
            </button>
          </div>
        ) : (
          <div>
            <div className="mb-5">
              <div className="mt-4 text-center text-gray-500">
                선택한 옵션을 삭제하시겠습니까?
              </div>
              <div className="mt-4 text-center text-gray-500">
                한 번 삭제하면 다시 불러올 수 없습니다.
              </div>
            </div>
            <div className="flex justify-center items-center">
              <button
                className="w-36 mb-5 border-2 border-blue-500 bg-white text-blue-500 font-bold py-2 px-4 rounded"
                type="button"
                onClick={closeModal}
              >
                {checked ? '확인' : '수정하기'}
              </button>
            </div>
          </div>
        )}
        {modalOpen && (
          <div className="flex justify-center items-center fixed top-0 left-0 w-full h-full bg-gray-500 bg-opacity-50 z-50">
            <div className="bg-white p-6 rounded-lg">
              {!checked ? (
                <div>
                  <div className="mb-4">정말 수정하시겠습니까?</div>
                  {/* <div className="mb-4">다시 복구할 수 없습니다.</div> */}
                </div>
              ) : (
                <div>
                  <div className="">정말 삭제하시겠습니까?</div>
                  <div className="mb-4">다시 복구할 수 없습니다.</div>
                </div>
              )}

              <div className="flex justify-center items-center gap-3">
                <div
                  className="border border-gray-600 p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-center"
                  onClick={closeModal}
                >
                  취소
                </div>
                {!checked ? (
                  <div
                    className="text-center border p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-cente bg-blue-600  text-white"
                    onClick={() => handleCreateBuyOptions()}
                  >
                    확인
                  </div>
                ) : (
                  <div
                    className="text-center border p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-cente bg-blue-600  text-white"
                    onClick={() => DeleteOption()}
                  >
                    확인
                  </div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default OptionComponent;
