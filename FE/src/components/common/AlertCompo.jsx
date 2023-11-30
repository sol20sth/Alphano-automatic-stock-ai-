import React, { useState } from 'react';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton';

function AlertCompo(props) {
  // const [value, setValue] = useState(true);

  return (
    <div>
      <Alert severity="error" className="mb-3">
        <div className="flex">
          <div className="flex justify-center items-center">
            <AlertTitle className="">{props.title}</AlertTitle>
          </div>
          {/* <div className="ml-auto"> */}
          {/* <IconButton onClick={ClickAlert} className="ml-auto">
              <ClearIcon />
            </IconButton> */}
          {/* </div> */}
        </div>
        <strong>{props.context}</strong>
      </Alert>
    </div>
  );
}

export default AlertCompo;
