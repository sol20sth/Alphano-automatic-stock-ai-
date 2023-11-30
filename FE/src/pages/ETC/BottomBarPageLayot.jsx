import React from 'react';
// import BottomBar from '@/components/common/BottomBar';
import useTabbarRender from '../../hooks/useTabbarRender';

function BottomBarPageLayout({ children }) {
  if (useTabbarRender()) {
    return (
      <div className="fixed bottom-0 left-0 right-0 h-54px m-auto">
        {children}
      </div>
    );
  }
  return <div />;
}

export default BottomBarPageLayout;
