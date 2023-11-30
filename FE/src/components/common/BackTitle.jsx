import React from 'react';
import back from '@/assets/back.svg';
import { useNavigate } from 'react-router-dom';

function BackTitle(props) {
  const navigate = useNavigate();
  const moveBack = () => {
    navigate(-1);
  };
  return (
    <>
      <div className="flex justify-between">
        <div className="flex p-4">
          <span className='w-5vw flex items-center' onClick={moveBack}><img src={back} alt="gd" className='w-2vw ml-2vw'/></span>
          <div className="ml-4 text-[5vw] font-bold">{props.title}</div>
        </div>
      </div>
    </>
  );
}
export default BackTitle;
