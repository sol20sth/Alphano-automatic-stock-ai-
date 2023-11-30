import React, { useState } from 'react';

function OptionalCondition() {
  const [addOptions, setAddOptions] = useState([false, false, false]);
  const [rsiPercent, setRsiPercent] = useState('');
  const [rsiPriceType, setRsiPriceType] = useState('');
  const [macdPercent, setMacdPercent] = useState('');
  const [macdPriceType, setMacdPriceType] = useState('');

  const color1 = rsiPriceType === '이상' ? 'bg-indigo-400' : 'bg-white';
  const color2 = rsiPriceType === '미만' ? 'bg-indigo-400' : 'bg-white';
  const color3 = macdPriceType === '이상' ? 'bg-indigo-400' : 'bg-white';
  const color4 = macdPriceType === '미만' ? 'bg-indigo-400' : 'bg-white';
  function handleChangeAddOptions(i) {
    const newAddOptions = [...addOptions]; // 복사해서 변경
    newAddOptions[i] = !newAddOptions[i]; // 상태를 토글
    setAddOptions(newAddOptions); // 변경된 상태를 적용
  }
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
  return <div></div>;
}

export default OptionalCondition;
