import React, { createContext, useState } from 'react';

export const TabContext22 = createContext();

export const TabProvider = ({ children }) => {
  const [value, setValue] = useState('1');
  const [bottomValue, setBottomValue] = useState();
  const [url, setUrl] = useState('');
  return (
    <TabContext22.Provider
      value={{ value, setValue, url, setUrl, bottomValue, setBottomValue }}
    >
      {children}
    </TabContext22.Provider>
  );
};
