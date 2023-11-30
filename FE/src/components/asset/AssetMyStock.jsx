import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import { useTheme } from '@mui/material/styles';
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
import { formatNumberWithCommas } from '../../hooks/formatnum';

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

  // HTML 요소를 찾습니다.
function changePageText () {
  const selectLabel = document.querySelector('.MuiTablePagination-selectLabel');
  selectLabel.textContent = '페이지당 개수:';
}

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

TablePaginationActions.propTypes = {
  count: PropTypes.number.isRequired,
  onPageChange: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
};

export default function AssetMyStock() {
  const navigate = useNavigate();
  const haveStock = useSelector((state) => state.haveStockList);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

  // 데이터 가공 및 정렬
  const rows = haveStock
    .map((data) => ({
      stocknum: data.pdno,
      name: data.prdt_name,
      count: data.hldg_qty,
      avgprice: data.pchs_avg_pric,
      MoL: data.evlu_amt,
      perSum: data.evlu_pfls_amt,
    }))
    .sort((a, b) => (a.stocknum < b.stocknum ? -1 : 1));
  // 빈 행 계산
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  useEffect(()=> {
    changePageText()
  },[])

  function handliGoCurrentPage(data) {
    navigate('/currentprice', {
      state: {
        stockName: data.name,
        stockCode: data.stocknum,
      },
    })
  }
  return (
    <TableContainer component={Paper} className='whitespace-nowrap'>
      <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
        <TableHead>
          <TableRow>
            <StyledTableCell align="center" className='whitespace-nowrap'>
              종목코드
              <br />
            </StyledTableCell>
            <StyledTableCell align="center" className='whitespace-nowrap'>
              종목명
              <br />
            </StyledTableCell>
            <StyledTableCell align="center" className='whitespace-nowrap'>
              평가금액
              <br />
            </StyledTableCell>
            <StyledTableCell align="center" className='whitespace-nowrap'>
              평균손익
              <br />
            </StyledTableCell>
            <StyledTableCell align="center" className='whitespace-nowrap'>보유수량</StyledTableCell>
            <StyledTableCell align="center" className='whitespace-nowrap'>평균매수가</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {(rowsPerPage > 0
            ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            : rows
          ).map((row, index) => (
            <TableRow key={index} onClick={()=> {handliGoCurrentPage(row)}}>
              {/* onClick={()=> {navigate('/')}} */}
              <TableCell component="th" scope="row">
                {row.stocknum}
              </TableCell>
              <TableCell style={{ width: 320 }} align="center"  className='whitespace-nowrap' >
                {row.name}
              </TableCell>
              <TableCell style={{ width: 320 }} align="center" >
                {formatNumberWithCommas(row.MoL) || ''}
              </TableCell>
              <TableCell style={{ width: 320 }} align="center" >
                {formatNumberWithCommas(row.perSum) || ''}
              </TableCell>
              <TableCell style={{ width: 320 }} align="center" >
                {formatNumberWithCommas(row.count)}
              </TableCell>
              <TableCell style={{ width: 320 }} align="center" >
                {formatNumberWithCommas(parseInt(row.avgprice, 10))}
              </TableCell>
            </TableRow>
          ))}
          {emptyRows > 0 && (
            <TableRow style={{ height: 53 * emptyRows }}>
              <TableCell colSpan={6} />
            </TableRow>
          )}
        </TableBody>
        <TableFooter>
          <StyledTableRow>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
              colSpan={6}
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
  );
}
