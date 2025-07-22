// import React from "react";
// import DeviceTable from "../components/DeviceTable";
// import { Container, Typography } from "@mui/material";

// const DevicePage = () => {
//   return (
//     <Container>
//       <Typography variant="h4" gutterBottom>
//         Quản lý Thiết bị
//       </Typography>
//       <DeviceTable />
//     </Container>
//   );
// };

// export default DevicePage;

import React from "react";
import DeviceTable from "../components/DeviceTable";
import {
  Container,
  Typography,
  Box,
  Button,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";

const DevicePage = () => {
  const handleAddDevice = () => {
    alert("Mở form thêm thiết bị"); // bạn có thể mở dialog tại đây
  };

  return (
    <Container>
      {/* Header + Add Button */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 2,
        }}
      >
        <Typography variant="h4">Quản lý Thiết bị</Typography>
        <Button
          variant="contained"
          color="primary"
          startIcon={<AddIcon />}
          onClick={handleAddDevice}
        >
          Thêm thiết bị
        </Button>
      </Box>

      {/* Bảng danh sách thiết bị */}
      <DeviceTable />
    </Container>
  );
};

export default DevicePage;
