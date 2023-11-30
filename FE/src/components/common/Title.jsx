import React from 'react';

function Title(props) {
  return (
    <>
      <div className="flex justify-between">
        <div className="flex p-4">
          <div className="ml-4">{props.title}</div>
        </div>
      </div>
    </>
  );
}
export default Title;
