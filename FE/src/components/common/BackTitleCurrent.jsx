import React, { useEffect, useContext } from 'react';
import back from '@/assets/back.svg';
import { useNavigate, useLocation } from 'react-router-dom';
import StarOutlineIcon from '@mui/icons-material/StarOutline';
import StarRateIcon from '@mui/icons-material/StarRate';
import { useState } from 'react';
import Instance from '@/api/Instance';
// import { TabContext22 } from '../../hooks/TabContext';

function BackTitle(props) {
  const accessToken = localStorage.getItem('accesstoken');
  const [likeStock, setLikeStock] = useState([]);
  // const { bottomValue, setBottomValue, url, setUrl } = useContext(TabContext22);

  const navigate = useNavigate();
  // const location = useLocation();
  const moveBack = () => {
    const previousPath = location.state ? location.state.previousPath : null;
    navigate(-1);
  };

  const StarClick = async (stockName) => {
    try {
      await Instance.post(
        `api/v1/member/like/stock`,
        { stockName },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        },
      );
    } catch (error) {
      console.error('Error :', error);
    }
  };

  useEffect(() => {
    Instance.get(`api/v1/member/like/stock/`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
      .then((response) => {
        setLikeStock(response.data.data);
      })
      .catch((error) => console.error('Error :', error));
  }, [likeStock]);

  return (
    <>
      <div className="flex justify-between">
        <div className="flex items-center p-4">
          <img src={back} alt="gd" onClick={moveBack} />
          <div className="w-48vw ml-4 text-[5vw] font-bold">{props.title}</div>
          <div className="flex w-35vw items-center justify-end">
            {likeStock.some((stock) => stock.stockName === props.title) ? (
              <StarRateIcon
                className="text-yellow-200"
                fontSize="large"
                onClick={() => StarClick(props.title)}
              />
            ) : (
              <StarOutlineIcon
                fontSize="large"
                onClick={() => StarClick(props.title)}
              />
            )}
            <div
              className="flex ml-3vw w-22vw h-5vh justify-center items-center rounded-lg bg-[#323131] text-white"
              onClick={() =>
                navigate('/StockOrder', {
                  state: {
                    stockName: props.title,
                    stockCode: props.stockCode,
                  },
                })
              }
            >
              주문하기
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
export default BackTitle;
