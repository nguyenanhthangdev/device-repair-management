import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Sidebar from "./layouts/SideBar";
import DevicePage from "./pages/DevicePage";
import { Box, Toolbar } from "@mui/material";
import AddDeviceForm from "./components/AddDeviceForm";
import EditDeviceForm from "./components/EditDeviceFrom";
import DeviceTypePage from "./pages/DeviceTypePage";

const drawerWidth = 0;

function App() {
  return (
    <Router>
      <Box sx={{ display: "flex" }}>
        <Sidebar />
        <Box
          component="main"
          sx={{ flexGrow: 1, bgcolor: "#f4f6f8", p: 3, ml: `${drawerWidth}px` }}
        >
          <Toolbar />
          <Routes>
            <Route path="/" element={<h1>Trang chủ</h1>} />
            <Route path="/devices" element={<DevicePage />} />
            <Route path="/device-types" element={<DeviceTypePage />} />
            <Route path="/customers" element={<h1>Khách hàng</h1>} />
            <Route path="/repairs" element={<h1>Đơn sửa chữa</h1>} />
            <Route path="/devices/add" element={<AddDeviceForm />} />
            <Route path="/devices/edit/:id" element={<EditDeviceForm />} />
          </Routes>
        </Box>
      </Box>
    </Router>
  );
}

export default App;
