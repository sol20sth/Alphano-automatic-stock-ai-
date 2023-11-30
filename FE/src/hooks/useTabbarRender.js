import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

function useTabbarRender() {
  const [isTabbarRender, setIsTabbarRender] = useState(false);
  const location = useLocation();
  const NOT_ARROWED_PATH = [
    '/error',
    '/intro',
    '/login',
    '/signup',
    '/optionDetail',
  ];

  useEffect(() => {
    const isPathExcluded = NOT_ARROWED_PATH.some((path) => {
      if (typeof path === 'string') {
        return path === location.pathname;
      }
      if (path instanceof RegExp) {
        return path.test(location.pathname);
      }
      return false;
    });

    setIsTabbarRender(!isPathExcluded);
  }, [location]);

  return isTabbarRender;
}

export default useTabbarRender;
