import { createSlice } from '@reduxjs/toolkit';

const currentPrice = createSlice({
  name: 'currentPrice',
  initialState: {
    data: {
    },
  },
  reducers: {
    setCurrentPrice: (state, action) => {
      state.data = action.payload;
    },
  },
});

export const { setCurrentPrice } = currentPrice.actions;
export default currentPrice.reducer;
