import React, { useState, useEffect } from 'react';
import { useTheme } from '@mui/material/styles';
import Instance from '@/api/Instance';
import { useNavigate } from 'react-router-dom';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableContainer from '@mui/material/TableContainer';
import TableFooter from '@mui/material/TableFooter';
import TablePagination from '@mui/material/TablePagination';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Autocomplete from '@mui/material/Autocomplete';
import HelpCenterIcon from '@mui/icons-material/HelpCenter';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: '#50586C',
    color: '#DCE2F0',
    fontSize: 13,
    padding: 7,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  backgroundColor: '#DCE2F0',
  color: '#50586C',
  '&:nth-of-type(odd)': {
    backgroundColor: '#DCE2F0',
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    backgroundColor: '#DAE2F0',
  },
}));

const SelectBtnComponent = (props) => {
  const { selectSearchOption, setSelectSearchOption, searchoptions } = props;

  const handleSelectChange = (option) => {
    setSelectSearchOption(option);
  };
  const selected = 'border-blue-500 border-4 text-blue-500 font-bold';
  const noSelceted = 'border-black-300 border-2';
  return (
    <div className="mb-5vw grid grid-cols-6">
      {searchoptions.map((option) => (
        <button
          key={option}
          onClick={() => handleSelectChange(option)}
          className={`${
            option === selectSearchOption ? `${selected}` : `${noSelceted}`
          } h-6vh rounded-sm text-xs`}
        >
          {option}
        </button>
      ))}
    </div>
  );
};

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (event) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page"
      >
        {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton
        onClick={handleBackButtonClick}
        disabled={page === 0}
        aria-label="previous page"
      >
        {theme.direction === 'rtl' ? (
          <KeyboardArrowRight />
        ) : (
          <KeyboardArrowLeft />
        )}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page"
      >
        {theme.direction === 'rtl' ? (
          <KeyboardArrowLeft />
        ) : (
          <KeyboardArrowRight />
        )}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page"
      >
        {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  );
}

export default function AIPage() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const navigate = useNavigate();

  const [tmprows, setTmpRows] = useState([]);
  const [rows, setRows] = useState([]);
  const searchoptions = [
    '이름순',
    '일주일',
    '오늘',
    '종가',
    '최대값',
    '최저값',
  ];
  const [selectSearchOption, setSelectSearchOption] = useState(
    searchoptions[1],
  );

  const [stockName, setStockName] = useState([]);
  const [selectStockName, setSelectStockName] = useState('');

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const [isHovered, setIsHovered] = useState(false);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  useEffect(() => {
    if (selectStockName === '') {
      setRows(tmprows); // 만약 selectStockName이 비어있다면 모든 행을 보여줍니다.
    } else {
      // selectStockName이 비어있지 않다면 해당 조건을 만족하는 행만 표시합니다.
      const filteredRows = tmprows.filter((row) =>
        row[0].includes(selectStockName),
      );
      setRows(filteredRows);
    }
  }, [selectStockName]);
  useEffect(() => {
    setRows(tmprows);
  }, [tmprows]);

  function getTableCellTextColor(cellIndex, row) {
    const tmp = row.slice(1).map(Number);
    const largestValue = Math.max(...tmp);
    const smallestValue = Math.min(...tmp);
    if (row[cellIndex] === largestValue) {
      return 'red';
    } else if (row[cellIndex] === smallestValue) {
      return 'blue';
    } else {
      return 'inherit';
    }
  }
  function handliGoCurrentPage(data) {
    navigate('/currentprice', {
      state: {
        stockName: data[0].slice(0, -8),
        stockCode: data[0].slice(-7, -1),
      },
    });
  }
  function classFontWeight(cellIndex, row) {
    const tmp = row.slice(1).map(Number);
    const largestValue = Math.max(...tmp);
    const smallestValue = Math.min(...tmp);
    if (row[cellIndex] === largestValue) {
      return 'bold';
    } 
    if (cellIndex === 0) {
      return 'bold';
    }
  }

  useEffect(() => {
    const selectLabel = document.querySelector(
      '.MuiTablePagination-selectLabel',
    );

    if (selectLabel) {
      // 요소가 존재하면 내용을 변경
      selectLabel.textContent = '페이지당 개수:';
    }
    if (rows) {
      setStockName(rows.map((row) => row[0]));
    }
  }, [rows]);

  useEffect(() => {
    function sortStockList() {
      if (selectSearchOption === '이름순') {
        setRows([...rows.sort((a, b) => a[0].localeCompare(b[0]))]);
      } else if (selectSearchOption === '일주일') {
        setRows([...rows.sort((a, b) => b[1] - a[1])]);
      } else if (selectSearchOption === '오늘') {
        setRows([...rows.sort((a, b) => b[2] - a[2])]);
      } else if (selectSearchOption === '종가') {
        setRows([...rows.sort((a, b) => b[3] - a[3])]);
      } else if (selectSearchOption === '최대값') {
        setRows([...rows.sort((a, b) => b[4] - a[4])]);
      } else if (selectSearchOption === '최저값') {
        setRows([...rows.sort((a, b) => b[5] - a[5])]);
      }
    }
    sortStockList();
  }, [selectSearchOption]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await Instance.get(`api/v1/stock-daily/ai`);
        const tmpData = response.data.data;
        const tmp2 = [];
        for (let i = 0; i < tmpData.length; i++) {
          const tmp1 = [];
          tmp1.push(tmpData[i].stockName + '(' + tmpData[i].stockCode + ')');
          tmp1.push(tmpData[i].aiWeeklyReliability);
          tmp1.push(tmpData[i].aiReliability);
          tmp1.push(tmpData[i].aiEndReliability);
          tmp1.push(tmpData[i].aiMaxReliability);
          tmp1.push(tmpData[i].aiMinReliability);
          tmp2.push(tmp1);
        }
        setTmpRows(tmp2);
      } catch (error) {
        console.log('AI예측 데이터 받아오기: ', error);
      }
    };
    getData();
  }, []);

  return (
    <>
      <div className=" h-86vh mx-4  whitespace-nowrap">
        <div>
          <p className="my-10 text-3xl font-bold text-center ">
            AI 분석 서비스
          </p>
        </div>

        <div className="">
          <div className="flex overflow-hidden whitespace-nowrap">
            <Stack className="mb-3vw w-85vw">
              <Autocomplete
                freeSolo
                id="free-solo-2-demo"
                disableClearable
                options={stockName}
                value={selectStockName}
                onInputChange={(event, newInputValue) => {
                  setSelectStockName(newInputValue);
                }}
                onChange={(event, newValue) => {
                  setSelectStockName(newValue);
                }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    InputProps={{
                      ...params.InputProps,
                      type: 'search',
                      style: {
                        height: '5vh',
                        padding: '0px',
                        textAlign: 'center',
                      },
                    }}
                    placeholder="종목을 검색하세요"
                  />
                )}
              />
            </Stack>
            <div
              onMouseEnter={() => {
                setIsHovered(true);
              }}
              onMouseLeave={() => {
                setIsHovered(false);
              }}
              className="relative h-5vh h-5vw"
            >
              <HelpCenterIcon fontSize="large" className="h-5vh h-5vw" />
            </div>
            {isHovered && (
              <div className="absolute top-52 left-4 w-93vw bg-white p-2 shadow-lg rounded-lg">
                <p>
                  <p className="text-lg font-bold text-center">신뢰도란?</p><br/>
                  <p className='text-sm text-center'>
                  · AI가 예측한 주식 투자 정보를 기반으로 각 <br/>종목마다의 전체,
                  종가, 최대값, 최저가의<br/> 예측 결과의 일일 평균치를 계산해놓은
                    값<br /><br />
                    · abs(일일 예측 결과-실제값) / 실제값 * 100<br /> = 일일 신뢰도{' '}
                    <br /><br />
                    · (모든 일일 신뢰도를 더한 값) / 모든 날의 일수<br /> = 신뢰도{' '}
                    <br /><br />
                    <span className="text-red-500 font-bold">· 높은</span> 점수는
                     신뢰성 <span className="text-red-500 font-bold">높은</span>{' '}
                    추천을 나타냅니다
                    <br />
                    <span className="text-blue-500 font-bold">· 낮은</span> 점수는
                    신뢰성 <span className="text-blue-500 font-bold">낮은</span>{' '}
                    추천을 나타냅니다
                    <br />
                  </p>
                </p>
              </div>
            )}
          </div>

          <SelectBtnComponent
            selectSearchOption={selectSearchOption}
            setSelectSearchOption={setSelectSearchOption}
            searchoptions={searchoptions}
          />
          {/* {selectSearchOption} */}
        </div>

        <TableContainer
          component={Paper}
          className=" whitespace-nowrap "
          style={{ height: '62vh' }}
        >
          <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <StyledTableCell align="center" className="text-sm ">
                  종목명
                  <br />
                </StyledTableCell>
                <StyledTableCell
                  align="center"
                  className="text-sm whitespace-nowrap"
                >
                  일주일
                  <br />
                  신뢰도
                  <br />
                  (단위:%)
                </StyledTableCell>
                <StyledTableCell
                  align="center"
                  className="text-sm whitespace-nowrap"
                >
                  오늘
                  <br />
                  신뢰도
                  <br />
                  (단위:%)
                </StyledTableCell>
                <StyledTableCell
                  align="center"
                  className="text-sm whitespace-nowrap"
                >
                  종가값
                  <br />
                  신뢰도
                  <br />
                  (단위:%)
                </StyledTableCell>
                <StyledTableCell
                  align="center"
                  className="text-sm whitespace-nowrap"
                >
                  최대값
                  <br />
                  신뢰도
                  <br />
                  (단위:%)
                </StyledTableCell>
                <StyledTableCell
                  align="center"
                  className="text-sm whitespace-nowrap"
                >
                  최저값
                  <br />
                  신뢰도
                  <br />
                  (단위:%)
                </StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                ? rows.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage,
                  )
                : rows
              ).map((row, index) => (
                <TableRow
                  key={index}
                  onClick={() => {
                    handliGoCurrentPage(row);
                  }}
                >
                  {row.map((cell, cellIndex) => (
                    <TableCell
                      key={cellIndex}
                      align="center"
                      style={{
                        color: getTableCellTextColor(cellIndex, row),
                        fontWeight: classFontWeight(cellIndex, row),
                      }}
                    >
                      {cellIndex === 0 ? (
                        <>
                          {cell.slice(0, -8)}
                          <br />
                          {cell.slice(-8)}
                        </>
                      ) : (
                        cell
                      )}
                    </TableCell>
                  ))}
                </TableRow>
              ))}
              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={rows[0].length} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <StyledTableRow>
                <TablePagination
                  rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                  colSpan={rows[0]?.length}
                  count={rows.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  SelectProps={{
                    inputProps: {
                      'aria-label': 'rows per page',
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                  ActionsComponent={TablePaginationActions}
                />
              </StyledTableRow>
            </TableFooter>
          </Table>
        </TableContainer>
      </div>
    </>
  );
}
