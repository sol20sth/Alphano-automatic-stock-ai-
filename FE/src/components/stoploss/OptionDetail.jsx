import React from 'react';
import back from '@/assets/back.svg';
import { useNavigate } from 'react-router-dom';

function OptionDetail() {
  const navigate = useNavigate();
  const moveBack = () => {
    navigate(-1);
    // 이동할 페이지의 Tab 값을 변경하도록 설정
    // 예를 들어, 주문내역 페이지로 이동할 경우
    // setValue('3');
  };

  return (
    <div>
      <div className="flex justify-between">
        <div className="flex p-4">
          <span className="w-5vw flex items-center" onClick={moveBack}>
            <img src={back} alt="gd" className="w-2vw ml-2vw" />
          </span>
          <div className="ml-4 text-[5vw] font-bold">옵션 상세보기</div>
        </div>
      </div>
    </div>
  );
}

export default OptionDetail;
