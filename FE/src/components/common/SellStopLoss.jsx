import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import toast  from 'react-hot-toast';


function SellStopLoss() {
  const navigate = useNavigate();
  const [selectedRow, setSelectedRow] = useState(null);
  const columns = [
    { id: 'name', label: '종목명', minWidth: 120 },
    {
      id: 'volume',
      label: '수량',
      minWidth: 170,
      align: 'right',
      format: (value) => value.toLocaleString('en-US'),
    },
    {
      id: 'price',
      label: '평균단가',
      minWidth: 170,
      align: 'right',
      format: (value) => value.toLocaleString('en-US'),
    },
  ];
  function createData(name, volume, price) {
    // const stockVolume = volume + '주';
    const averagePrice = price.toLocaleString();
    return { name, volume, price };
  }

  // 여기다가 보유 주식 데이터 입력
  const rows = [
    createData('삼성전자', 5, 83000),
    createData('카카오', 4, 44000),
    createData('현대자동차', 10, 56000),
    createData('LG전자', 2, 24000),
    createData('SK증권', 22, 58000),
  ];

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);


  const moveToOption = () => {
    if (selectedRow) {
      navigate('/selloptionpage', { state: selectedRow });
    } else {
      console.error('error');
      toast.error('보유 주식을 선택해주세요.');
    }
  };

  return (
    <div>
      <div className="text-l font-bold mt-1 mb-3"> 보유 종목 </div>
      {rows.length ? (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
          <TableContainer sx={{ maxHeight: 440 }}>
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {columns.map((column) => (
                    <TableCell
                      key={column.id}
                      align={column.align}
                      style={{ minWidth: column.minWidth }}
                    >
                      {column.label}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>

              <TableBody>
                {rows
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row, key) => {
                    return (
                      <TableRow
                        hover
                        role="checkbox"
                        tabIndex={-1}
                        key={row.code}
                        onClick={() => setSelectedRow(row)}
                      >
                        {columns.map((column) => {
                          const value = row[column.id];
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.format && typeof value === 'number'
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
        </Paper>
      ) : (
        <div>
          <div className="mt-4 text-center text-gray-500">
            보유 주식이 없습니다.
          </div>
          <div className="flex justify-center">
            <button
              className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
              type="button"
              onClick={moveToOption}
            >
              주식 구매하기
            </button>
          </div>
        </div>
      )}
      <div>
        {selectedRow ? (
          <div>
            <div className="mt-8">
              <div className="text-l font-bold mt-6 mb-3">선택한 주식</div>

              <TableRow
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                }}
              >
                <div className="flex items-center border-b-2 border-black py-2 w-100vw justify-between">
                  <div className="flex-1 text-center">{selectedRow.name}</div>
                  <div className="flex-1 text-center">{`${selectedRow.volume}주`}</div>
                  <div className="flex-1 text-center">{`${selectedRow.price.toLocaleString()}원`}</div>
                </div>
              </TableRow>

              {/* <div>종목명 : {selectedRow.name}</div>
              <div>수량 : {selectedRow.volume}주</div>
              <div>평균단가 : {selectedRow.price.toLocaleString()}원</div> */}
            </div>
            <div className="flex justify-center">
              <button
                className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                type="button"
                onClick={moveToOption}
              >
                매도옵션설정
              </button>
            </div>
          </div>
        ) : (
          <div>
            <div className="mt-4 text-center text-gray-500">
              매도할 주식을 선택해 주세요
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default SellStopLoss;
