import React from "react";
import DeviceTable from "../components/DeviceTable";
import {
  Container,
  Typography,
  Box,
  Button,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate } from "react-router-dom";

const DevicePage = () => {
  const navigate = useNavigate();

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
          onClick={() => navigate("/devices/add")}
          sx={{ float: "right", mb: 2 }}
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
