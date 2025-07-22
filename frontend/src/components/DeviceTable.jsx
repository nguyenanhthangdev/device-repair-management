import React, { useEffect, useState } from "react";
import { getAllDevices, deleteDevice, updateDevice } from "../api/DeviceApi";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton, Button
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const DeviceTable = () => {
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    loadDevices();
  }, []);

  const loadDevices = async () => {
    const response = await getAllDevices();
    setDevices(response.data);
  };

  const handleUpdate = async (id) => {
    await updateDevice(id);
    loadDevices(); // reload
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
            <TableCell>Trạng thái</TableCell>
            <TableCell>Hành động</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {devices.map((device) => (
            <TableRow key={device.id}>
              <TableCell>{device.name}</TableCell>
              <TableCell>{device.typeName}</TableCell>
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
