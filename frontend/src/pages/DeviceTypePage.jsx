import React, { useEffect, useState } from "react";
import DeviceTable from "../components/DeviceTypeTable";
import {
  Container,
  Typography,
  Box,
  Button,
  Alert,
  Collapse,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate, useLocation } from "react-router-dom";
import DeviceTypeTable from "../components/DeviceTypeTable";

const DeviceTypePage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [alertInfo, setAlertInfo] = useState(null);

  useEffect(() => {
    // Nếu có state được gửi từ trang trước (AddDeviceTypeForm)
    if (location.state && location.state.message) {
      setAlertInfo({
        success: location.state.success,
        message: location.state.message,
      });

      // Xóa state để tránh hiển thị lại khi người dùng F5
      window.history.replaceState({}, document.title);
    }
  }, [location.state]);

  return (
    <Container>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 2,
        }}
      >
        <Typography variant="h4">Quản lý Loại Thiết bị</Typography>
        <Button
          variant="contained"
          color="primary"
          startIcon={<AddIcon />}
          onClick={() => navigate("/device-types/add")}
          sx={{ float: "right", mb: 2 }}
        >
          Thêm loại thiết bị
        </Button>
      </Box>

      {/* Thông báo thêm thành công/thất bại */}
      <Collapse in={!!alertInfo}>
        {alertInfo && (
          <Alert
            severity={alertInfo.success ? "success" : "error"}
            onClose={() => setAlertInfo(null)}
            sx={{ mb: 2 }}
          >
            {alertInfo.message}
          </Alert>
        )}
      </Collapse>

      {/* Bảng danh sách thiết bị */}
      <DeviceTypeTable />
    </Container>
  );
};

export default DeviceTypePage;
