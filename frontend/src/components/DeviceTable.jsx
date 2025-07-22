import React, { useEffect, useState } from "react";
import { getAllDevices, deleteDevice } from "../api/DeviceApi";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useNavigate } from "react-router-dom";

const DeviceTable = () => {
  const [devices, setDevices] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadDevices();
  }, []);

  const loadDevices = async () => {
    const response = await getAllDevices();
    setDevices(response.data);
  };

  const handleUpdate = (id) => {
    navigate(`/devices/edit/${id}`); // ✅ chỉ điều hướng, không gọi API PUT
  };

  const handleDelete = async (id) => {
    await deleteDevice(id);
    loadDevices(); // reload
  };

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Tên thiết bị</TableCell>
            <TableCell>Loại</TableCell>
            <TableCell>Mô tả lỗi</TableCell>
            <TableCell>Trạng thái</TableCell>
            <TableCell>Hành động</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {devices.map((device) => (
            <TableRow key={device.id}>
              <TableCell>{device.name}</TableCell>
              <TableCell>{device.typeName}</TableCell>
              <TableCell>{device.description}</TableCell>
              <TableCell>{device.status}</TableCell>
              <TableCell>
                <IconButton onClick={() => handleUpdate(device.id)} color="warning">
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => handleDelete(device.id)} color="error">
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default DeviceTable;
