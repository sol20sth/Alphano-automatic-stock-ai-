import React from 'react';
import { useSelector } from 'react-redux';

import { formatNumberWithCommas } from '../../hooks/formatnum';

export default function Margin() {

  const totalMyAssetInfo = useSelector((state) => state.totalMyAssetInfo);
  return (
    <>
      <div>
        <div className=" w-100vw">
          <div className="flex w-90vw justify-between">
            <p className="ml-3vw text-slate-600 mb-3vw">총대출금액</p>
            <p className="mr-4vw font-medium mb-3vw">
              {formatNumberWithCommas(totalMyAssetInfo.tot_loan_amt) || '0'}원
            </p>
          </div>
          <div className="flex w-90vw justify-between">
            <p className="ml-3vw text-slate-600 mb-3vw">자동상환여부</p>
            <p className="mr-4vw font-medium mb-3vw">
              {totalMyAssetInfo.fncg_gld_auto_rdpt_yn ? Y : 'N'}
            </p>
          </div>
        </div>
        <div className=" w-100vw">
          <div className="flex w-90vw justify-between">
            <p className="ml-3vw text-slate-600 mb-3vw">익일자동상환금액</p>
            <p className="mr-4vw font-medium mb-3vw">
              {formatNumberWithCommas(totalMyAssetInfo.nxdy_auto_rdpt_amt) || '0'}원
            </p>
          </div>
          <div className="flex w-90vw justify-between">
            <p className="ml-3vw text-slate-600 mb-3vw">D+2자동상환금액</p>
            <p className="mr-4vw font-medium mb-3vw">
            {formatNumberWithCommas(totalMyAssetInfo.d2_auto_rdpt_amt) || '0'}원
            </p>
          </div>
        </div>

      </div>
    </>
  );
}

