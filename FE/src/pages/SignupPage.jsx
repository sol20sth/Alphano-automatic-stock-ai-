import React, { useState } from 'react';
import Instance from '@/api/Instance';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setName } from '../store/userSlice'; // 이 부분은 당신의 userSlice 경로에 맞게 수정하세요.
import Alphano from '../assets/Alphano.png';
import toast, { Toaster } from 'react-hot-toast';

function SignupPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [userId, setUserId] = useState();
  const [userPassword, setUserPassword] = useState();
  const [checkPassword, setCheckPassword] = useState();
  const [userName, setUserName] = useState();
  const [userNumber, setUserNumber] = useState();
  const [appKey, setAppKey] = useState();
  const [secretKey, setSecretKey] = useState();
  const [accountFirstNum, setAccountFristNum] = useState();
  const [accountSecondNum, setAccountSeconNum] = useState();
  const [passwordCheck, setPasswordCheck] = useState(null);

  const handleIdChange = (event) => {
    setUserId(event.target.value);
  };
  const handlePasswordChange = (event) => {
    setUserPassword(event.target.value);
    if (checkPassword === event.target.value) {
      setPasswordCheck(true);
    } else {
      setPasswordCheck(false);
    }
  };
  const handleCheckPasswordChange = (event) => {
    setCheckPassword(event.target.value);
    if (event.target.value === userPassword) {
      setPasswordCheck(true);
    } else {
      setPasswordCheck(false);
    }
  };
  const handleNameChange = (event) => {
    setUserName(event.target.value);
  };
  const handleNumberChange = (event) => {
    setUserNumber(event.target.value);
  };
  const handleAppKeyChange = (event) => {
    setAppKey(event.target.value);
  };
  const handleSecretKeyChange = (event) => {
    setSecretKey(event.target.value);
  };

  const handleAccountFirstNumChange = (e) => {
    setAccountFristNum(e.target.value);
  };
  const handleAccountSecondNumChange = (e) => {
    setAccountSeconNum(e.target.value);
  };
  const isPasswordValid = (password) => {
    // 비밀번호가 영문, 숫자, 특수 문자를 각각 1개 이상 포함하고, 8글자 이상인지 확인
    const condition =
      /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return condition.test(password);
  };
  const sendUserData = async () => {
    if (!userId) {
      toast.error('사용자 아이디를 입력해주세요.');
    } else if (!userPassword) {
      toast.error('비밀번호를 입력해주세요.');
    } else if (userPassword !== checkPassword) {
      toast.error('비밀번호가 일치하지 않습니다.');
    } else if (!isPasswordValid(userPassword)) {
      toast.error(
        '비밀번호는 영문, 숫자, 특수 문자를 각각 1개 이상 포함하고, 8글자 이상이어야 합니다.',
      );
    } else if (!checkPassword) {
      toast.error('비밀번호 확인을 입력해주세요.');
    } else if (!userName) {
      toast.error('사용자 이름을 입력해주세요.');
    } else if (!userNumber) {
      toast.error('전화번호를 입력해주세요.');
    } else if (!appKey || appKey.length !== 36) {
      toast.error('올바른 App 키를 36글자 입력해주세요!');
    } else if (!secretKey || secretKey.length !== 180) {
      toast.error('올바른 App 시크릿 키를 180글자 입력해주세요!');
    } else if (!accountFirstNum || accountFirstNum.length !== 8) {
      toast.error('계좌 숫자 8글자를 입력해주세요.');
    } else if (!accountSecondNum) {
      toast.error('계좌 코드숫자 2글자를 입력해주세요.');
    } else {
      try {
        const userInfo = {
          username: userId,
          password: userPassword,
          tel: userNumber,
          appKey: appKey,
          appsecretKey: secretKey,
          account: accountFirstNum,
          accountCode: accountSecondNum,
        };
        const response = await Instance.post('api/v1/auth/sign-up', userInfo);

        if (response.status === 200 && response.data.code === 'C004') {
          toast.error('중복된 아이디 입니다!');
        } else if (response.status === 200 && response.data.code === 'OK') {
          navigate('/login');
          dispatch(setName(userName));
        } else {
          console.error('회원가입 실패:', response);
        }
      } catch (error) {
        console.error('회원가입 실패:', error);
      }
    }
  };

  //   const moveToSinguptwo = () => {
  //     navigate('/signup-key');
  //   };
  return (
    <div>
      <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="flex justify-center">
          <img className="mx-100 w-3/4 h-auto" src={Alphano} alt="Alphano" />{' '}
        </div>

        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          {/* <form className="space-y-6" action="#" method="POST"> */}
          <div className="mb-5">
            <label
              htmlFor="id"
              className="block text-sm font-medium leading-6 text-gray-900 "
            >
              아이디 (한투HTS아이디를 입력하시오)
            </label>
            <div className="mt-2">
              <input
                onChange={handleIdChange}
                name="email"
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div className="mb-5">
            <div className="flex items-center justify-between">
              <label
                htmlFor="password"
                className="block text-sm font-medium leading-6 text-gray-900 "
              >
                비밀번호
              </label>
            </div>
            <div className="mt-2">
              <input
                onChange={handlePasswordChange}
                name="password"
                type="password"
                autoComplete="current-password"
                required
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          <div className="mb-5">
            <div className="flex items-center justify-between">
              <label
                htmlFor="password"
                className="block text-sm font-medium leading-6 text-gray-900"
              >
                비밀번호 확인
              </label>
            </div>
            <div className="mt-2">
              <input
                onChange={handleCheckPasswordChange}
                name="password"
                type="password"
                autoComplete="current-password"
                required
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>

            {passwordCheck === false ? (
              <p className="text-center text-red-600 font-bold">
                비밀번호가 다릅니다!
              </p>
            ) : (
              <></>
            )}
          </div>

          <div className="mb-5">
            <label
              htmlFor="name"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              이름
            </label>
            <div className="mt-2">
              <input
                onChange={handleNameChange}
                name="name"
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          <div className="mb-5">
            <label
              htmlFor="number"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              전화번호
            </label>
            <div className="mt-2">
              <input
                onChange={handleNumberChange}
                name="number"
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div className="mb-5">
            <label
              htmlFor="appkey"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              App Key(한투 App Key는 36글자입니다)
            </label>
            <div className="mt-2">
              <input
                onChange={handleAppKeyChange}
                name="appkey"
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          <div className="mb-5">
            <label
              htmlFor="secretkey"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              Secret Key(한투 Secret Key는 180글자입니다)
            </label>
            <div className="mt-2">
              <input
                onChange={handleSecretKeyChange}
                name="secretkey"
                className="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          <div className="mb-5">
            <label
              htmlFor="accountNum"
              className="block text-sm font-medium leading-6 text-gray-900"
            >
              계좌번호(10자리)
            </label>
            <div className="mt-2 flex">
              <input
                type="text"
                inputMode="numeric"
                value={accountFirstNum}
                onChange={handleAccountFirstNumChange}
                maxLength="8"
                name="accountFirstNum"
                className="block w-50vw text-center rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
              <p className="mx-3"> - </p>
              <input
                type="text"
                inputMode="numeric"
                value={accountSecondNum}
                maxLength="2"
                onChange={handleAccountSecondNumChange}
                name="accountSecondNum"
                className="block w-30vw text-center rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>
          <div className="mb-5">
            <button
              onClick={sendUserData}
              className="flex justify-center items-center border-blue-600 border-solid border-2 h-14 rounded-lg w-full"
            >
              회원가입
            </button>
          </div>
          {/* </form> */}

          <p className="mt-10 text-center text-sm text-gray-500">
            회원이라면?{' '}
            <a
              href="/login"
              className="font-semibold leading-6 text-blue-600 hover:text-blue-500"
            >
              로그인
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default SignupPage;
