import React, { useEffect, useState } from 'react';
import Checkbox from '@mui/material/Checkbox';
import Box from '@mui/material/Box';
import Input from '@mui/material/Input';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import FormControl from '@mui/material/FormControl';

function StopLossMenu(props) {
  const [tagetPriceRange, setTagetPriceRange] = useState('');
  const [rsiRange, setRsiRange] = useState('');
  const [macdRange, setMacdRange] = useState('');
  const [volumeRange, setVolumeRange] = useState('');
  const [trailingRange, setTrailingRange] = useState('');

  const handleTagetPriceChange = (e) => {
    setTagetPriceRange(e.target.value);
  };

  const handleRsiChange = (e) => {
    setRsiRange(e.target.value);
  };

  const handleMacdChange = (e) => {
    setMacdRange(e.target.value);
  };

  const handleVolumeChange = (e) => {
    setVolumeRange(e.target.value);
  };
  const handleTrailingChange = (e) => {
    setVolumeRange(e.target.value);
  };

  const [typeCode, setTypeCode] = useState(null);
  useEffect(() => {
    const type = () => {
      if (props.value === '1') {
        setTypeCode('Buy');
      } else {
        setTypeCode('Sell');
      }
    };

    type();
  }, [props.value]);
  const [targetPriceChecked, setTargetPriceChecked] = useState(false);
  const [rsiChecked, setRSIChecked] = useState(false);
  const [macdChecked, setMACDChecked] = useState(false);
  const [volumeChecked, setVolumeChecked] = useState(false);
  const [trailingChecked, setTrailingChecked] = useState(false);

  //   const [selected, setSelected] = useState(false);
  const [targetPrice, setTargetPrice] = useState(null);
  const [RSI, setRSI] = useState(null);
  const [MACD, setMACD] = useState(null);
  const [volume, setVolume] = useState(null);
  const [trailing, setTratiling] = useState(null);

  const valueChange = (event) => {
    setTargetPrice(event.target.value);
  };
  useEffect(() => {
    const valueToZero = () => {
      if (targetPriceChecked === false) {
        setTargetPrice('');
      }
    };
    valueToZero();
  }, [targetPriceChecked]);

  const rsiValueChange = (event) => {
    setRSI(event.target.value);
  };
  useEffect(() => {
    const valueToZero = () => {
      if (rsiChecked === false) {
        setRSI('');
      }
    };
    valueToZero();
  }, [rsiChecked]);
  const macdValueChange = (event) => {
    setMACD(event.target.value);
  };
  useEffect(() => {
    const valueToZero = () => {
      if (macdChecked === false) {
        setMACD('');
      }
    };
    valueToZero();
  }, [macdChecked]);

  const volumeValueChange = (event) => {
    setVolume(event.target.value);
  };
  useEffect(() => {
    const valueToZero = () => {
      if (volumeChecked === false) {
        setVolume('');
      }
    };
    valueToZero();
  }, [volumeChecked]);

  const trailingValueChange = (event) => {
    setTratiling(event.target.value);
  };
  useEffect(() => {
    const valueToZero = () => {
      if (trailingChecked === false) {
        setTratiling('');
      }
    };
    valueToZero();
  }, [trailingChecked]);

  const submitData = () => {
    const stoplossData = {
      type: typeCode,
      value: targetPrice,
      range: tagetPriceRange,
      rsi: RSI,
      macd: MACD,
      volume: volume,
      trailing: trailing,
    };
  };
  const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

  const ariaLabel = { 'aria-label': 'description' };

  return (
    <div className="mb-20">
      {/* <p className="max-w-2xl text-sm text-gray-500 px-4 py-6">상세설정</p> */}
      <div className="mt-1 border-t border-gray-100 ">
        <dl className="divide-y divide-gray-100">
          <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="flex justify-left items-center text-sm font-medium leading-6 text-gray-900">
              <Checkbox
                {...label}
                checked={targetPriceChecked}
                onChange={() => setTargetPriceChecked(!targetPriceChecked)}
              />
              Target Price
            </dt>
            {targetPriceChecked && (
              <div
                className={` items-center justify-between text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0 `}
              >
                <div className="flex justify-between items-center ">
                  <div>가격: </div>
                  <Box
                    sx={{
                      '& > :not(style)': { m: 1, width: '10ch' },
                    }}
                    noValidate
                    autoComplete="off"
                  >
                    <div className="flex justify-center items-center">
                      <Input
                        type="number"
                        value={targetPrice}
                        placeholder="0"
                        inputProps={ariaLabel}
                        onChange={valueChange}
                        disabled={!targetPriceChecked}
                      />
                      <dt className="">₩</dt>
                    </div>
                  </Box>
                </div>
                <div className="flex justify-between items-center ">
                  <div>조건 :</div>
                  <FormControl sx={{ m: 1, minWidth: 80 }}>
                    <Select
                      value={tagetPriceRange}
                      onChange={handleTagetPriceChange}
                      autoWidth
                    >
                      <MenuItem value={'이상'}>이상</MenuItem>
                      <MenuItem value={'이하'}>이하</MenuItem>
                      {/* <MenuItem value={22}>Twenty one and a half</MenuItem> */}
                    </Select>
                  </FormControl>
                </div>
              </div>
            )}
          </div>
          <div className="py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-medium leading-6 text-gray-900">
              <Checkbox
                {...label}
                checked={rsiChecked}
                onChange={() => setRSIChecked(!rsiChecked)}
              />
              RSI
            </dt>
            {rsiChecked && (
              <div className="flex items-center justify-between text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                <Box
                  sx={{
                    '& > :not(style)': { m: 1, width: '10ch' },
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <div className="flex justify-center items-center">
                    <Input
                      type="number"
                      value={RSI}
                      placeholder="0"
                      inputProps={ariaLabel}
                      onChange={rsiValueChange}
                      // disabled={!rsiChecked}
                    />
                    <dt className="">₩</dt>
                  </div>
                </Box>
                <FormControl sx={{ m: 1, minWidth: 80 }}>
                  <Select value={rsiRange} onChange={handleRsiChange} autoWidth>
                    <MenuItem value={10}>이상</MenuItem>
                    <MenuItem value={21}>이하</MenuItem>
                    {/* <MenuItem value={22}>Twenty one and a half</MenuItem> */}
                  </Select>
                </FormControl>
              </div>
            )}
          </div>
          <div className="py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-medium leading-6 text-gray-900">
              <Checkbox
                {...label}
                checked={macdChecked}
                onChange={() => setMACDChecked(!macdChecked)}
              />
              MACD
            </dt>
            {macdChecked && (
              <div className="flex items-center justify-between text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                <Box
                  sx={{
                    '& > :not(style)': { m: 1, width: '10ch' },
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <div className="flex justify-center items-center">
                    <Input
                      type="number"
                      value={MACD}
                      placeholder="0"
                      inputProps={ariaLabel}
                      onChange={macdValueChange}
                      disabled={!macdChecked}
                    />
                    <dt className="">₩</dt>
                  </div>
                </Box>
                <FormControl sx={{ m: 1, minWidth: 80 }}>
                  <Select
                    value={macdRange}
                    onChange={handleMacdChange}
                    autoWidth
                  >
                    <MenuItem value={10}>이상</MenuItem>
                    <MenuItem value={21}>이하</MenuItem>
                    {/* <MenuItem value={22}>Twenty one and a half</MenuItem> */}
                  </Select>
                </FormControl>
              </div>
            )}
          </div>
          <div className="py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-medium leading-6 text-gray-900">
              <Checkbox
                {...label}
                checked={volumeChecked}
                onChange={() => setVolumeChecked(!volumeChecked)}
              />
              Volume
            </dt>
            {volumeChecked && (
              <div className="flex items-center justify-between text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                <Box
                  sx={{
                    '& > :not(style)': { m: 1, width: '10ch' },
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <div className="flex justify-center items-center">
                    <Input
                      type="number"
                      value={volume}
                      placeholder="0"
                      inputProps={ariaLabel}
                      onChange={volumeValueChange}
                      disabled={!volumeChecked}
                    />
                    <dt className="">₩</dt>
                  </div>
                </Box>
                <FormControl sx={{ m: 1, minWidth: 80 }}>
                  <Select
                    value={volumeRange}
                    onChange={handleVolumeChange}
                    autoWidth
                  >
                    <MenuItem value={10}>이상</MenuItem>
                    <MenuItem value={21}>이하</MenuItem>
                    {/* <MenuItem value={22}>Twenty one and a half</MenuItem> */}
                  </Select>
                </FormControl>
              </div>
            )}
          </div>
          <div className="py-4 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-medium leading-6 text-gray-900">
              <Checkbox
                {...label}
                checked={trailingChecked}
                onChange={() => setTrailingChecked(!trailingChecked)}
              />
              Trailing
            </dt>
            {trailingChecked && (
              <div className="flex items-center justify-between text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                <Box
                  sx={{
                    '& > :not(style)': { m: 1, width: '10ch' },
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <div className="flex justify-center items-center">
                    <Input
                      type="number"
                      placeholder="0"
                      inputProps={ariaLabel}
                      onChange={volumeValueChange}
                      disabled={!trailingChecked}
                    />
                    <dt className="">₩</dt>
                  </div>
                </Box>
                <FormControl sx={{ m: 1, minWidth: 80 }}>
                  <Select
                    value={trailingRange}
                    onChange={handleTrailingChange}
                    autoWidth
                    sx={{ minWidth: '100px' }}
                  >
                    <MenuItem value={10}>이상</MenuItem>
                    <MenuItem value={21}>이하</MenuItem>
                    {/* <MenuItem value={22}>Twenty one and a half</MenuItem> */}
                  </Select>
                </FormControl>
              </div>
            )}
          </div>
          <div className="mt-10">
            <div
              className="flex justify-center items-center border-indigo-600 border-solid border-2 h-14 rounded-lg mb-10"
              onClick={submitData}
            >
              확인
            </div>
          </div>
        </dl>
      </div>
    </div>
  );
}

export default StopLossMenu;
