import { useNavigate } from 'react-router';

const useMovePage = () => {
  const navigate = useNavigate();

  const movePage = () => {
    navigate(url, { state });
  };

  const goBack = () => {
    navigate(-1);
  };

  return {
    movePage,
    goBack,
  };
};

export default useMovePage;
