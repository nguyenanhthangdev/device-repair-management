import React, { useEffect, useState } from "react";
import {
  Table, TableBody, TableCell, TableContainer, TableHead,
  TableRow, Paper, IconButton, Dialog, DialogTitle, DialogActions,
  Button, Snackbar, Alert
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useNavigate } from "react-router-dom";
import { getAllDevices, deleteDevice } from "../api/DeviceApi";

const DeviceTable = () => {
  const [devices, setDevices] = useState([]);
  const [deleteId, setDeleteId] = useState(null); // id thiết bị cần xóa
  const [snackbar, setSnackbar] = useState({ open: false, message: "", severity: "success" });
  const navigate = useNavigate();

  useEffect(() => {
    loadDevices();
  }, []);

  const loadDevices = async () => {
    try {
      const response = await getAllDevices();
      setDevices(response.data);
    } catch (error) {
      console.error("Lỗi khi tải danh sách thiết bị:", error);
    }
  };

  const handleUpdate = (id) => {
    navigate(`/devices/edit/${id}`);
  };

  const handleConfirmDelete = (id) => {
    setDeleteId(id); // mở dialog xác nhận
  };

  const handleDelete = async () => {
    try {
      await deleteDevice(deleteId);
      setSnackbar({ open: true, message: "✅ Đã xóa thiết bị", severity: "success" });
      loadDevices();
    } catch (error) {
      setSnackbar({ open: true, message: "❌ Xóa thất bại", severity: "error" });
    } finally {
      setDeleteId(null); // đóng dialog
    }
  };

  return (
    <>
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
                  <IconButton onClick={() => handleConfirmDelete(device.id)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Hộp thoại xác nhận xóa */}
      <Dialog open={!!deleteId} onClose={() => setDeleteId(null)}>
        <DialogTitle>Bạn có chắc chắn muốn xóa thiết bị này?</DialogTitle>
        <DialogActions>
          <Button onClick={() => setDeleteId(null)}>Hủy</Button>
          <Button onClick={handleDelete} color="error">Xóa</Button>
        </DialogActions>
      </Dialog>

      {/* Snackbar thông báo */}
      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
          variant="filled"
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default DeviceTable;
