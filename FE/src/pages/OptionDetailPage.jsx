import React from 'react';
import OptionDetail from '../components/stoploss/OptionDetail';
import { useLocation } from 'react-router-dom';
import OptionUpdate from '../components/common/OptionComponent';

function OptionDetailPage() {
  const location = useLocation();
  const pageData = location.state.data;
  const pageState = location.state.status;
  return (
    <div>
      <OptionDetail />
      <OptionUpdate />
    </div>
  );
}

export default OptionDetailPage;
