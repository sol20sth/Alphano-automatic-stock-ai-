import React, { useState } from 'react';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton';

function AlertCorrect(props) {
  const [value, setValue] = useState(true);

  const ClickAlert = () => {
    setValue(!value);
  };

  return (
    <div>
      {value && (
        <Alert severity="info" onClick={ClickAlert} className="mb-3">
          <div className="flex">
            <div className="flex justify-center items-center">
              <AlertTitle className="">{props.title}</AlertTitle>
            </div>
          </div>
          <strong>{props.context}</strong>
        </Alert>
      )}
    </div>
  );
}

export default AlertCorrect;
