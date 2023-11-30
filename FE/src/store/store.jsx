import { configureStore } from '@reduxjs/toolkit';
import userReducer from './userSlice';
import emailReducer from './emailSlice'
import { persistReducer, persistStore } from 'redux-persist';
import totalMyAssetInfo from './totalMyAssetInfo';
import haveStock from './haveStock';
import currentPrice from './currentPrice';
export default configureStore({
  reducer: {
    user: userReducer,
    email: emailReducer,
    totalMyAssetInfo : totalMyAssetInfo,
    haveStockList: haveStock,
    currentPrice: currentPrice
  },
});
