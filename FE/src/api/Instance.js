import axios from 'axios';
import LocalStorage from '@/api/LocalStorage';

const instance = axios.create({
  baseURL: 'https://k9c106.p.ssafy.io/',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Axios 요청시 인터셉트
instance.interceptors.request.use((req) => {
  const accessToken = LocalStorage.getItem('accesstoken');
  if (accessToken) {
    req.headers.authorization = `Bearer ${accessToken}`;
  }

  return req;
});

// Axios 응답시 인터셉트
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // 401 에러 처리 로직
      window.location.href = '/login';
      LocalStorage.removeItem('accesstoken');
    }
    return Promise.reject(error);
  },
);

export default instance;
