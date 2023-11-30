import React from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import IntroPage from '@/pages/IntroPage';
import Error from '@/pages/ETC/Error';
import LoginPage from '@/pages/LoginPage';
import Home from '@/pages/Home';
import SignupPage from '@/pages/SignupPage';
import StopLossPage from '@/pages/StopLossPage';
import AIPage from '@/pages/AIPage';
import CurrentPage from '@/pages/CurrentPage';
import StockOrder from '@/pages/StockOrder';
import BottomBarPageLayout from '@/pages/ETC/BottomBarPageLayot';
import TestBottomBar from '@/components/common/TestBottomBar';
import AssetPage from '@/pages/AssetPage';
import SellOptionPage from '@/pages/SellOptionPage';
import DetailBalance from '../components/asset/DetailBalance';
import DetailDeposit from '../components/asset/DetailDeposit';
import { Toaster } from 'react-hot-toast';
import OptionDetailPage from '../pages/OptionDetailPage';
import StopLossOptionDetailPage from '../pages/StopLossOptionDetailPage';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate replace to={'/intro'} />} />
        <Route path="/intro" element={<IntroPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/home" element={<Home />} />
        <Route path="/stoploss" element={<StopLossPage />} />
        <Route path="/ai" element={<AIPage />} />
        <Route path="/currentprice" element={<CurrentPage />} />
        <Route path="/StockOrder" element={<StockOrder />} />
        <Route path="/assets" element={<AssetPage />} />
        <Route path="/selloptionpage" element={<SellOptionPage />} />
        <Route path="/detailBalance" element={<DetailBalance />} />
        <Route path="/detailDeposit" element={<DetailDeposit />} />
        <Route path="/optionDetail" element={<OptionDetailPage />} />
        <Route
          path="/stoplossOptionDetail"
          element={<StopLossOptionDetailPage />}
        />
        <Route path="/*" element={<Navigate replace to="/error" />} />
        <Route path="/error" element={<Error />} />
      </Routes>
      <BottomBarPageLayout>
        <TestBottomBar />
      </BottomBarPageLayout>
      <Toaster />
    </BrowserRouter>
  );
};

export default AppRouter;
