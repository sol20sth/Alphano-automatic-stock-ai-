import { createSlice } from '@reduxjs/toolkit';

const HaveStockList = createSlice({
  name: 'HaveStockList',
  initialState: [],
  reducers: {
    setHaveStockList: (state, action) => {
      return action.payload;
    },
  },
});

export const { setHaveStockList } = HaveStockList.actions;
export default HaveStockList.reducer;