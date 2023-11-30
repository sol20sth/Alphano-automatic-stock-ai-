import React from 'react';

const OptionSellSettingList = () => {

  return (
    <div>
      <div>
        <div className="flex mt-5vw border-b border-t border-black">
          <div className="border-r w-1/3 border-black">
            <div className="flex justify-center items-center h-6vh font-bold bg-[#F5F5F5]">
              <div className="">종목명</div>
            </div>
          </div>

          <div className="w-1/4 border-r border-black">
            <div className="flex flex-col justify-center items-center h-6vh font-bold bg-[#F5F5F5]">
              <div>설정 가격</div>
            </div>
          </div>

          <div className="w-1/6 border-r border-black">
            <div className="flex  flex-col justify-center items-center h-6vh font-bold bg-[#F5F5F5]">
              <div className="">수량</div>
            </div>
          </div>

          <div className="w-1/4">
            <div className="flex flex-col justify-center items-center h-6vh font-bold bg-[#F5F5F5]">
              <div>설정시간</div>
            </div>
          </div>
        </div>
      </div>
      {/* <div>hi</div> */}
      {/* {data.map((item) => (
        <div key={item.id}>
          <div className="flex" onClick={() => ClickOptionDetail(item)}>
            <div className=" w-1/3">
              <div className="flex justify-center items-center h-6vh">
                <div className="flex justify-center items-center">
                  {item.item.length > 5
                    ? `${item.item.slice(0, 5)}...`
                    : item.item}
                </div>
              </div>
            </div>

            <div className="w-1/4 ">
              <div className="flex flex-col justify-center items-end h-6vh mr-2vw">
                <div>{item.averagePurchasePrice.toLocaleString()}</div>
              </div>
            </div>

            <div className="w-1/6">
              <div className="flex flex-col justify-center items-end h-6vh mr-2vw">
                <div className="">{item.balanceQuantity}주</div>
              </div>
            </div>

            <div className="w-1/4">
              <div className="flex flex-col justify-center items-center h-6vh">
                <div className="text-xs font-bold">{item.buyDay}</div>
              </div>
            </div>
          </div>
        </div>
      ))} */}
    </div>
  );
};

export default OptionSellSettingList;
