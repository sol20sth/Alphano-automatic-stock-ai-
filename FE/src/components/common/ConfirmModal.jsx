import React from 'react';

function ConfirmModal(props) {
  return (
    <div>
      <div>
        <div>{props.title}</div>
      </div>
      <div>
        <button>취소</button>
        <button>확인</button>
      </div>
    </div>
  );
}

export default ConfirmModal;
