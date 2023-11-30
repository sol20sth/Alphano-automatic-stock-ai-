import React, { useState } from 'react';
import axios from 'axios'; // Axios 추가
import Alphano from '../assets/Alphano.png';
import { setEmail } from '@/store/emailSlice';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import AlertCompo from '../components/common/AlertCompo';

function LoginPage() {
  const [userId, setUserId] = useState('');
  const [userPassword, setUserPassword] = useState('');
  const [alertState, setAlertState] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleUserIdChange = (event) => {
    setUserId(event.target.value);
  };

  const handleUserPasswordChange = (event) => {
    setUserPassword(event.target.value);
  };

  const loginBtnClick = async (event) => {
    event.preventDefault();
    console.log(1);
    try {
      const loginData = {
        username: userId,
        password: userPassword,
      };

      const response = await axios.post(
        `https://k9c106.p.ssafy.io/api/v1/auth/login`,
        loginData,
        {
          headers: {
            'X-Authentication-Strategy': 'basic',
          },
        },
      );
      console.log(response, '2');
      if (response.data.status === 200) {
        localStorage.setItem('accesstoken', response.data.data.accessToken);
        dispatch(setEmail(userId));
        navigate('/home');
      }
    } catch (error) {
      // 에러 처리
      if (error.message === 'Request failed with status code 401') {
        setAlertState(true);
        // alert('회원 아이디 또는 비밀번호가 잘못되었습니다 ');
      }
      console.error('에러 발생:', error);
    }
  };

  return (
    <div>
      <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="flex justify-center sm:mx-auto sm:w-full sm:max-w-sm">
          <img className="mx-100 w-3/4 h-auto" src={Alphano} alt="Alphano" />
        </div>

        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              아이디
            </label>
            <div className="mt-2">
              <input
                onChange={handleUserIdChange}
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between">
              <label
                htmlFor="password"
                className="block text-sm font-medium leading-6 text-gray-900"
              >
                비밀번호
              </label>
            </div>
            <div className="mt-2">
              <input
                onChange={handleUserPasswordChange}
                id="password"
                name="password"
                type="password"
                autoComplete="current-password"
                required
                className="pl-3 block mb-4 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          {alertState && (
            <AlertCompo
              title="오류"
              context="회원 아이디 또는 비밀번호가 잘못되었습니다 "
              className=""
            />
          )}

          <div>
            <div
              type="button"
              aria-hidden
              onClick={loginBtnClick}
              className="flex justify-center items-center border-blue-600 border-solid border-2 h-14 rounded-lg"
            >
              로그인
            </div>
          </div>

          <p className="mt-10 text-center text-sm text-gray-500">
            회원이 아니라면?{' '}
            <a
              href="/signup"
              className="font-semibold leading-6 text-blue-600 hover:text-blue-500"
            >
              회원가입
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
