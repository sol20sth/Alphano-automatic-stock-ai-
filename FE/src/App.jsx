import AppRouter from './router/AppRouter';
import { Provider } from 'react-redux';
import store from '@/store/store.jsx';
import { TabProvider } from './hooks/TabContext';
import React from 'react';

function App() {
  return (
    <>
      <Provider store={store}>
        <TabProvider>
          <AppRouter />
        </TabProvider>
      </Provider>
    </>
  );
}

export default App;
