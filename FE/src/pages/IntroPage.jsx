import React from 'react';
import { useNavigate } from 'react-router-dom';
import Alphano from '../assets/Alphano.png';

function IntroPage() {
  const navigate = useNavigate();
  const moveToLogin = () => {
    navigate('/login');
  };
  const moveToSignup = () => {
    navigate('/Signup');
  };

  const people = [
    {
      name: 'Leslie Alexander',
      role: 'Co-Founder / CEO',
      imageUrl:
        'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    },
    // More people...
  ];

  return (
    <>
      <div className="bg-white py-24 sm:py-32">
        <div className="mx-auto max-w-7xl px-6 lg:px-8">
          <div className="mx-auto max-w-2xl lg:text-center">
            <div className="flex justify-center">
              <img
                className="mx-100 w-3/4 h-auto"
                src={Alphano}
                alt="Alphano"
              />{' '}
            </div>
            {/* <p className="mt-2 text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl flex justify-center items-center">
              Alphano
            </p> */}
            <h2 className="flex justify-center items-center text-2xl font-bold leading-tight text-blue-600 mb-4">
              현대인을 위한
            </h2>
            <h2 className="flex justify-center items-center text-2xl font-bold leading-tight text-blue-600 mb-4">
              주식 자동매매 서비스
            </h2>
            <h2 className="flex justify-center items-center text-2xl font-bold leading-tight text-blue-600">
              NEXT Alphago
            </h2>

            <div className="mt-10">
              <div
                className="flex justify-center items-center border-blue-600 border-solid border-2 h-14 rounded-lg mb-10"
                onClick={moveToLogin}
              >
                <a
                  href="/login"
                  className="font-semibold leading-6 text-blue-600 hover:text-blue-600"
                >
                  로그인
                </a>
              </div>
              <div
                className="flex justify-center items-center border-blue-600 border-solid border-2 h-14 rounded-lg"
                onClick={moveToSignup}
              >
                <a
                  href="/signup"
                  className="font-semibold leading-6 text-blue-600 hover:text-blue-600"
                >
                  회원가입
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default IntroPage;
