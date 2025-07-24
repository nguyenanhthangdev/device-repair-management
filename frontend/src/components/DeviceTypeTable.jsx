import React, { useEffect, useState } from "react";
import {
  Table, TableBody, TableCell, TableContainer, TableHead,
  TableRow, Paper, IconButton, Dialog, DialogTitle, DialogActions,
  Button, Snackbar, Alert
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useNavigate } from "react-router-dom";
import { deleteDeviceType, getAllDeviceTypes } from "../api/DeviceTypeApi";

const DeviceTypeTable = () => {
  const [deviceTypes, setDeviceTypes] = useState([]);
  const [deleteId, setDeleteId] = useState(null); // id thiết bị cần xóa
  const [snackbar, setSnackbar] = useState({ open: false, message: "", severity: "success" });
  const navigate = useNavigate();

  useEffect(() => {
    loadDeviceTypes();
  }, []);

  const loadDeviceTypes = async () => {
    try {
      const response = await getAllDeviceTypes();
      setDeviceTypes(response.data);
    } catch (error) {
      console.error("Lỗi khi tải danh sách loại thiết bị:", error);
    }
  };

  const handleUpdate = (id) => {
    navigate(`/device-types/edit/${id}`);
  };

  const handleConfirmDelete = (id) => {
    setDeleteId(id); // mở dialog xác nhận
  };

  const handleDelete = async () => {
    try {
      await deleteDeviceType(deleteId);
      setSnackbar({ open: true, message: "✅ Đã xóa loại thiết bị", severity: "success" });
      loadDeviceTypes();
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
              <TableCell>Loại thiết bị</TableCell>
              <TableCell>Hành động</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {deviceTypes.map((deviceType) => (
              <TableRow key={deviceType.id}>
                <TableCell>{deviceType.name}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleUpdate(deviceType.id)} color="warning">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleConfirmDelete(deviceType.id)} color="error">
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
        <DialogTitle>Bạn có chắc chắn muốn xóa loại thiết bị này?</DialogTitle>
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

export default DeviceTypeTable;
