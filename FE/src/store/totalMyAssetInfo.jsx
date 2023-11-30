import { createSlice } from '@reduxjs/toolkit';

const TotalMyAssetInfo = createSlice({
  name: 'TotalMyAssetInfo',
  // initialState:{},
  initialState: {
    dnca_tot_amt: '',
    nxdy_excc_amt: '',
    prvs_rcdl_excc_amt: '',
    cma_evlu_amt: '0',
    bfdy_buy_amt: '0',
    thdt_buy_amt: '0',
    nxdy_auto_rdpt_amt: '0',
    bfdy_sll_amt: '0',
    thdt_sll_amt: '0',
    d2_auto_rdpt_amt: '0',
    bfdy_tlex_amt: '0',
    thdt_tlex_amt: '0',
    tot_loan_amt: '0',
    scts_evlu_amt: '',
    tot_evlu_amt: '',
    nass_amt: '',
    fncg_gld_auto_rdpt_yn: '',
    pchs_amt_smtl_amt: '',
    evlu_amt_smtl_amt: '',
    evlu_pfls_smtl_amt: '',
    tot_stln_slng_chgs: '',
    bfdy_tot_asst_evlu_amt: '',
    asst_icdc_amt: '',
    asst_icdc_erng_rt: '',
  },
  reducers: {
    setTotalMyAssetInfo: (state, action) => {
      return action.payload;
    },
  },
});

export const { setTotalMyAssetInfo } = TotalMyAssetInfo.actions;
export default TotalMyAssetInfo.reducer;