import React from "react";
export function formatNumberWithCommas(strnum) {
  return new Intl.NumberFormat('ko-KR').format(strnum);
}