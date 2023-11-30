import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { formatNumberWithCommas } from '../../hooks/formatnum';

export default function DetailAsset( ) {
  const [clickArrow, setClickArrow] = useState(false);
  const [detailbtn, setDetailbtn] = useState(false)
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const totalMyAssetInfo = useSelector((state) => state.totalMyAssetInfo)
  return (
    <>
<div className="pb-2 border-b-8 border-b-slate-100">
  <div className="flex justify-between mb-4vw">
    <div className="ml-5">
      <p className="text-xl">평가손익</p>
    </div>
    <div className="flex">
      <p className="text-xl font-medium mr-6vw" style={{ color: (totalMyAssetInfo.evlu_amt_smtl_amt - totalMyAssetInfo.pchs_amt_smtl_amt) * 1 < 0 ? 'blue' : 'red' }}>
        {formatNumberWithCommas(totalMyAssetInfo.evlu_amt_smtl_amt - totalMyAssetInfo.pchs_amt_smtl_amt) || '0'}원
      </p>
      <p className="mr-4vw font-medium mt-1" style={{ color: ((totalMyAssetInfo.evlu_amt_smtl_amt - totalMyAssetInfo.pchs_amt_smtl_amt) / totalMyAssetInfo.pchs_amt_smtl_amt * 100).toFixed(2) * 1 < 0 ? 'blue' : 'red' }}>
      {totalMyAssetInfo.pchs_amt_smtl_amt
        ? ((totalMyAssetInfo.evlu_amt_smtl_amt - totalMyAssetInfo.pchs_amt_smtl_amt) / totalMyAssetInfo.pchs_amt_smtl_amt * 100).toFixed(2) + '%'
        : '0%'
      }
      
      </p>
      <div onClick={() => setClickArrow(!clickArrow)}>
        {clickArrow ? (
          <KeyboardArrowUpIcon className="mr-5vw" />
        ) : (
          <KeyboardArrowDownIcon className="mr-5vw" />
        )}
      </div>
    </div>
  </div>
  {clickArrow ? (
    <>
      <div>
        <div className="flex w-100vw justify-between">
          <div className="flex w-50vw justify-between">
            <p className="ml-3vw text-slate-400 mb-2vw text-sm">평가금액</p>
            <p className="mr-4vw text-sm mb-2vw" >
              {formatNumberWithCommas(totalMyAssetInfo.evlu_amt_smtl_amt) || '0'}원
            </p>
          </div>
          <div className="flex w-50vw justify-between">
            <p className="ml-3vw text-slate-400 mb-2vw text-sm">예수금</p>
            <p className="mr-4vw text-sm mb-2vw" >
              {formatNumberWithCommas(totalMyAssetInfo.dnca_tot_amt) || '0'}원
            </p>
          </div>
        </div>
        <div className="flex w-100vw justify-between mb-3">
          <div className="flex w-50vw justify-between">
            <p className="ml-3vw text-slate-400 mb-2vw text-sm">매입금액</p>
            <p className="mr-4vw text-sm mb-2vw" >
              {formatNumberWithCommas(totalMyAssetInfo.pchs_amt_smtl_amt) || '0'}원
            </p>
          </div>
          <div className="flex w-50vw justify-between">
            <p className="ml-3vw text-slate-400 mb-2vw text-sm">D + 2</p>
            <p className="mr-4vw text-sm mb-2vw" >
              {(totalMyAssetInfo.prvs_rcdl_excc_amt) || '0'}원
            </p>
          </div>
        </div>
        <div className='w-100vw grid grid-cols-2'>
          <button className='h-5vh border-2 border-slate-400 rounded mx-2' onClick={()=> {navigate('/detailBalance')}}>상세보기</button>
          <button className='h-5vh border-2 border-slate-400 rounded mx-2' onClick={()=> {navigate('/detailDeposit')}}>예수금</button>
        </div>
      </div>
    </>
  ) : (
    <></>
  )}
      </div>
    </>
  );
}
// {totalMyAssetInfo}
