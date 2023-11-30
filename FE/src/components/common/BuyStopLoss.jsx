import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Autocomplete from '@mui/material/Autocomplete';
import { DemoItem } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import Box from '@mui/material/Box';
import Instance from '@/api/Instance';
import { priceUnit } from '../../hooks/priceUnit';
import { TabContext22 } from '@/hooks/TabContext';
import StopLossModal from '../stoploss/StopLossModal';
import HelpOutlineOutlinedIcon from '@mui/icons-material/HelpOutlineOutlined';
import dayjs from 'dayjs';
import Checkbox from '@mui/material/Checkbox';
import Tooltip from '@mui/material/Tooltip';
import IconButton from '@mui/material/IconButton';
import './BuyStopLoss.css';
import toast, { Toaster } from 'react-hot-toast';

function BuyStopLoss() {
  const [modalOpen, setModalOpen] = useState(false);

  const closeModal = () => {
    setModalOpen(!modalOpen);
  };
  const navigate = useNavigate();
  const { value, setValue } = useContext(TabContext22);
  const [title, setTitle] = useState('');
  const [stockNameNum, setStockNameNum] = useState(['삼성전자/000222']);
  const [selectAll, setSelectAll] = useState('');
  const [selectStockCode, setSelectStockCode] = useState('');
  const [selectStockName, setSelectStockName] = useState('');
  const [watchPrice, setWatchPrice] = useState('');
  const [moreorless, setMoreorless] = useState('');
  const [priceType, setPriceType] = useState('지정가');
  const [orderPrice, setOrderPrice] = useState('');
  const [orderNum, setOrderNum] = useState('');
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [checkTitle, setCheckTitle] = useState(false);

  const [addOptions, setAddOptions] = useState([false, false]);
  const [rsiPercent, setRsiPercent] = useState('');
  const [rsiPriceType, setRsiPriceType] = useState('');
  const [macdPercent, setMacdPercent] = useState('');
  const [macdPriceType, setMacdPriceType] = useState('');
  const today = dayjs();
  const [open1, setOpen1] = useState(false);
  const [open2, setOpen2] = useState(false);
  const content1 = (
    <>
      · 주식의 과매수, 과매도 상태에 있는지 확인하는 지표
      <br />
      <br />
      · 일반적으로 70이상은 과매수 : 매도 타이밍, <br />
      30이하는 과매도 상태 : 매수 타이밍 <br />
      <br />
      · RSI = 100 - (100/1+RS) <br />
      <br />
      · RS = 상승평균값 / 하락평균값 <br />
      <br />· 본 매매 프로그램의 RS를 구할때 14일로 계산
    </>
  );
  const content2 = (
    <>
      · 주가의 추세 방향과 강도를 나타내는 지표
      <br />
      <br />
      · 12일 이동평균(빠른선)과 26일 이동평균(느린선)을 이용 계산
      <br />
      <br />
      · MACD선과 MACD의 9일 이동평균(신호선) 간의 교차로 추세 전환을
      감지하며,이를 통해 주가의 추<br />
      세를 분석하는 데 사용됩니다.
      <br />
      <br />
      · 즉 MACD가 0보다 커지면 강세신호
      <br />
      <br />
      · 즉 MACD가 0보다 작아지면 약세신호
      <br />
    </>
  );
  const handleMoreorlessChange = (data) => {
    console.log(moreorless, data, '확인');
    setMoreorless(data);
  };

  function onChangePriceType(type) {
    setPriceType(type);
    if (type === '시장가') {
      setOrderPrice(-1);
    } else {
      setOrderPrice('');
    }
  }
  function onChangeWatchPrice(e) {
    let result = parseInt(e.target.value, 10).toString();
    setWatchPrice(result);
    setWatchPrice(Number(result));
  }
  function onChangePrice(e) {
    const intValue = e.target.value * 1;
    const sliceNum = intValue === 0 ? null : priceUnit(intValue);
    setOrderPrice(sliceNum);
  }

  function onChangeNum(e) {
    setOrderNum(e.target.value);
  }
  function onChangeStartDate(date) {
    setStartDate(date);
  }
  function onChangeEndDate(date) {
    setEndDate(date);
  }

  function handleChangeAddOptions(i) {
    const newAddOptions = [...addOptions]; // 복사해서 변경
    newAddOptions[i] = !newAddOptions[i]; // 상태를 토글
    setAddOptions(newAddOptions); // 변경된 상태를 적용
  }
  function handleRsiPercent(e) {
    let result = parseInt(e.target.value, 10).toString();
    setRsiPercent(result);
    setRsiPercent(Number(result));
  }
  function handleRsiPriceType(data) {
    setRsiPriceType(data);
  }
  function handleMacdPercent(e) {
    let result = parseInt(e.target.value, 10).toString();
    setMacdPercent(result);
    setMacdPercent(Number(result));
  }
  function handleMacdPriceType(data) {
    setMacdPriceType(data);
  }
  function handelChangeTitle(e) {
    setTitle(e.target.value);
  }
  function handleSelectStock(data) {
    const [name, num] = data[0].split('/');
    setSelectAll(data);
    setSelectStockName(name);
    setSelectStockCode(num);
  }
  const handleClickOpen1 = () => {
    setOpen1(true);
  };

  const handleClose1 = () => {
    setOpen1(false);
  };
  const handleClickOpen2 = () => {
    setOpen2(true);
  };

  const handleClose2 = () => {
    setOpen2(false);
  };
  function handleCreateBuyOptions() {
    if (!stockNameNum.some((stock) => stock[0].includes(selectStockName))) {
      alert('올바른 종목명을 선택하세요.');
    } else if (!checkTitle) {
      toast.error('옵션을 전부 채워 주세요!');
    } else if (!watchPrice) {
      toast.error('감시 가격을 입력하세요.');
    } else if (!moreorless) {
      toast.error('감시 가격 옵션을 선택하세요.');
    } else if (!priceType) {
      toast.error('가격 유형을 선택하세요.');
    } else if (priceType === '지정가' && !orderPrice) {
      toast.error('지정 주문 가격을 입력하세요.');
    } else if (!orderNum) {
      toast.error('주문 수량을 입력하세요.');
    } else if (!endDate) {
      toast.error('마감 날짜를 선택하세요.');
    } else {
      const memberOptions = [];
      if (addOptions[0]) {
        memberOptions.push({
          optionValue: rsiPercent ? rsiPercent * 1 : '',
          optionInequality: rsiPriceType === '이상' ? 'GREATER' : 'LESS',
          optionName: 'RSI',
        });
      }
      if (addOptions[1]) {
        memberOptions.push({
          optionValue: macdPercent ? macdPercent * 1 : '',
          optionInequality: macdPriceType === '이상' ? 'GREATER' : 'LESS',
          optionName: 'MACD',
        });
      }

      const data = {
        memberOrderStockNickname: title ? title : '',
        stockName: selectStockName ? selectStockName : '',
        buySell: 'B',
        isOrdered: 'WAIT',
        endTime: endDate ? endDate + 'T18:00:00.154Z' : '',
        orderCount: orderNum ? orderNum * 1 : '',
        targetPrice: watchPrice ? watchPrice * 1 : '',
        targetPriceCondition: priceType === '시장가' ? 'M' : 'T',
        targetPriceOptionInequality: moreorless === '이상' ? 'GREATER' : 'LESS',
        targetOrderPrice:
          priceType === '시장가' ? -1 : orderPrice ? orderPrice * 1 : null,
        memberOptions: memberOptions,
      };
      const postCreateOption = async (event) => {
        try {
          const response = await Instance.post(
            `api/v1/member/order/stock/create`,
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

  useEffect(() => {
    async function getStockName(event) {
      try {
        const response = await Instance.get(`api/v1/stock/info`);
        setStockNameNum(
          response.data.map((item) => [`${item.stockName}/${item.stockCode}`]),
        );
      } catch (error) {
        console.error('종목명 받아오기: ', error);
      }
    }
    getStockName();
  }, []);

  async function handleTitleCheck(event) {
    // event.preventDefault();
    try {
      const response = await Instance.get(
        `api/v1/member/order/stock/nickname?memberOrderStockNickname=${title}`,
      );
      const tmpData = response.data.data;

      if (tmpData.length === 0) {
        setCheckTitle(true);
        toast.success('사용할 수 있는 옵션명입니다.');
      } else if (
        tmpData.some((item) => item.memberOrderStockNickname === title)
      ) {
        setCheckTitle(false);
        setTitle('');
        toast.error('중복된 옵션명이 존재합니다!');
      } else {
        setCheckTitle(true);
        toast.success('사용할 수 있는 옵션명입니다.');
      }
    } catch (error) {
      console.error('옵션명 받아오기: ', error);
    }
  }

  useEffect(() => {}, [selectStockName, selectStockCode]);

  const color1 = rsiPriceType === '이상' ? 'bg-indigo-400' : 'bg-white';
  const color2 = rsiPriceType === '미만' ? 'bg-indigo-400' : 'bg-white';
  const color3 = macdPriceType === '이상' ? 'bg-indigo-400' : 'bg-white';
  const color4 = macdPriceType === '미만' ? 'bg-indigo-400' : 'bg-white';
  const color5 = moreorless === '이상' ? 'bg-indigo-400' : 'bg-white';
  const color6 = moreorless === '미만' ? 'bg-indigo-400' : 'bg-white';
  return (
    <div sx={{ width: '100%', height: '100%' }}>
      <p className="text-xs my-2 p-0 w-80vw">옵션명 설정</p>
      <div className="flex">
        <input
          value={title}
          type="text"
          className="w-84vw h-7vh mb-3vw border-solid border-2 border-black-600 rounded p-1 text-center"
          onChange={handelChangeTitle}
        />
        <button
          className="text-xs h-7vh mb-3vw ml-3vw hover:bg-blue-500 w-80vw border-solid border-2 border-black-600 rounded p-1"
          onClick={() => handleTitleCheck()}
        >
          중복체크
        </button>
      </div>
      <p className="text-xs my-2 p-0">종목설정</p>
      <Stack spacing={2} className="grid grid-cols-4">
        <Autocomplete
          freeSolo
          id="free-solo-2-demo"
          disableClearable
          options={stockNameNum}
          value={selectStockName}
          getOptionLabel={(option) => `${option}`}
          onChange={(event, newValue) => {
            handleSelectStock(newValue);
          }}
          renderInput={(params) => (
            <TextField
              {...params}
              InputProps={{
                ...params.InputProps,
                type: 'search',
              }}
              placeholder="종목을 검색하세요"
            />
          )}
        />
      </Stack>
      <p className="text-xs mb-2 mt-6">필수조건</p>
      <div className="border-solid border-2 border-black-600 p-1 ">
        <div className="flex pt-3 mb-3">
          <p className="text-xs w-20vw mt-3 ml-2vw pt-3">감시 가격: </p>
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
            {/* <FormControl fullWidth>
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
                <MenuItem value={'이상'}>이상</MenuItem>
                <MenuItem value={'이하'}>미만</MenuItem>
              </Select>
            </FormControl> */}
                          <div className="flex flex-col items-center">
                  <button
                    className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color5}`}
                    onClick={() => handleMoreorlessChange('이상')}
                  >
                    이상
                  </button>
                  <button
                    className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color6}`}
                    onClick={() => handleMoreorlessChange('이하')}
                  >
                    미만
                  </button>
                </div>
          </div>
        </div>

        <div className="mt-5">
          <div className="flex justify-between pb-4">
            <p
              className={`ml-15vw ${
                priceType === '지정가' ? 'text-black' : 'text-gray-400'
              }`}
              onClick={() => onChangePriceType('지정가')}
            >
              지정가
            </p>
            <p
              className={`mr-15vw ${
                priceType === '시장가' ? 'text-black' : 'text-gray-400'
              }`}
              onClick={() => onChangePriceType('시장가')}
            >
              시장가
            </p>
          </div>
          {priceType === '지정가' ? (
            <>
              <div className="flex mb-2vw">
                <p className="text-xs m-auto ml-2vw">주문가격 :</p>
                <input
                  className="w-40vw border-solid border-2 border-black-600 text-center place-content-center"
                  type="number"
                  value={orderPrice}
                  onChange={onChangePrice}
                  placeholder="0"
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
              <p className="text-xs mb-1vw ml-2vw">마감날짜(해당날까지 적용)</p>
            </div>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <Box
                sx={{
                  width: '100%',
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
                      const formattedDate = dayjs(date).format('YYYY-MM-DD');
                      onChangeEndDate(formattedDate);
                    }}
                  />
                </DemoItem>
              </Box>
            </LocalizationProvider>
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
            RSI{' '}
          </div>
          <div
            className="flex justify-center items-center ml-1"
            onClick={handleClickOpen1}
          >
            <HelpOutlineOutlinedIcon
              sx={{
                width: '24px',
                height: '24px',
                color: 'gray',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                marginTop: '4px',
              }}
            />
          </div>
        </div>

        <StopLossModal
          title={'RSI(상대강도지수)'}
          content={content1}
          open={open1}
          handleClose={handleClose1}
        />
        {addOptions[0] === true && (
          <div className="flex  ml-13vw">
            <div className="flex">
              <input
                value={rsiPercent}
                onChange={handleRsiPercent}
                className="h-5vh w-17vw my-auto border-solid border-2 border-black-400 rounded text-center"
                type="number"
              />
              <p className="text-xs my-auto px-1 mx-5"> % </p>
            </div>
            <div className="flex items-center">
              <div className="flex flex-col items-center">
                <button
                  className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color1}`}
                  onClick={() => handleRsiPriceType('이상')}
                >
                  이상
                </button>
                <button
                  className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color2}`}
                  onClick={() => handleRsiPriceType('미만')}
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
          <div
            className="flex justify-center items-center ml-1"
            onClick={handleClickOpen2}
          >
            <Tooltip title="MACD">
              <IconButton>
                <HelpOutlineOutlinedIcon
                  sx={{
                    width: '24px',
                    height: '24px',
                    color: 'gray',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    marginTop: '4px',
                  }}
                />
              </IconButton>
            </Tooltip>
          </div>
        </div>
        
        <StopLossModal  title={`MACD<br/>(이동평균 수렴확산 지수)`} content={content2} open={open2} handleClose={handleClose2}/>
        {addOptions[1] === true && (
          <div className="flex ml-13vw pb-3">
            <div className="flex">
              <input
                value={macdPercent}
                onChange={handleMacdPercent}
                className="h-5vh w-17vw my-auto border-solid border-2 border-black-400 rounded text-center"
                type="number"
              />
              <p className="text-xs my-auto px-1 mx-5"> % </p>
            </div>
            <div className="flex items-center">
              <div className="flex flex-col">
                <button
                  className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color3}`}
                  onClick={() => handleMacdPriceType('이상')}
                >
                  이상
                </button>
                <button
                  className={`w-15vw border-solid border-2 border-black-400 rounded-md ${color4}`}
                  onClick={() => handleMacdPriceType('미만')}
                >
                  미만
                </button>
              </div>
              <div className="text-xs my-auto px-1">일 때</div>
            </div>
          </div>
        )}
      </div>

      {modalOpen && (
        <div className="flex justify-center items-center fixed top-0 left-0 w-full h-full bg-gray-500 bg-opacity-50 z-50">
          <div className="bg-white p-6 rounded-lg">
            <div className="mb-4">옵션 설정하시겠습니까?</div>
            <div className="mb-4">확인버튼을 눌러주세요</div>
            <div className="flex justify-center items-center gap-3">
              <div
                className="border border-gray-600 p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-center"
                onClick={closeModal}
              >
                취소
              </div>
              <div
                className="text-center border p-2 rounded-lg mx-2 cursor-pointer w-1/3 text-cente bg-blue-600  text-white"
                onClick={() => handleCreateBuyOptions()}
              >
                확인
              </div>
            </div>
          </div>
        </div>
      )}
      <div className="flex justify-center items-center">
        <button
          className="w-36 mb-5 border-2 border-blue-500 bg-white text-blue-500 font-bold py-2 px-4 rounded mt-5vw"
          type="button"
          onClick={closeModal}
        >
          확인
        </button>
      </div>
    </div>
  );
}

export default BuyStopLoss;
