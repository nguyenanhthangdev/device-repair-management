import React, { useEffect, useState } from "react";
import {
  Table, TableBody, TableCell, TableContainer, TableHead,
  TableRow, Paper, IconButton, Dialog, DialogTitle, DialogActions,
  Button, Snackbar, Alert
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useNavigate } from "react-router-dom";
import { deleteCustomer, getAllCustomer } from "../api/CustomerApi";

const CustomerTable = () => {
  const [customers, setCustomers] = useState([]);
  const [deleteId, setDeleteId] = useState(null);
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: "",
    severity: "success"
  });
  const navigate = useNavigate();

  useEffect(() => {
    loadCustomer();
  }, []);

  const loadCustomer = async () => {
    try {
      const response = await getAllCustomer();
      setCustomers(response.data);
    } catch (error) {
      console.error("Lỗi khi tải danh sách khách hàng:", error);
    }
  };

  const handleUpdate = (id) => {
    navigate(`/customer/edit/${id}`);
  };

  const handleConfirmDelete = (id) => {
    setDeleteId(id);
  };

  const handleDelete = async () => {
    try {
      await deleteCustomer(deleteId);
      setSnackbar({
        open: true,
        message: "✅ Đã xóa khách hàng",
        severity: "success"
      });
      loadCustomer();
    } catch (error) {
      const errMsg = error.response?.data || "❌ Xóa thất bại";
      setSnackbar({
        open: true,
        message: `❌ ${errMsg}`,
        severity: "error"
      });
    } finally {
      setDeleteId(null);
    }
  };

  return (
    <>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Họ & Tên</TableCell>
              <TableCell>Số Điện Thoại</TableCell>
              <TableCell>Địa Chỉ</TableCell>
              <TableCell>Hành động</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {customers.map((customer) => (
              <TableRow key={customer.id}>
                <TableCell>{customer.name}</TableCell>
                <TableCell>{customer.phone}</TableCell>
                <TableCell>{customer.address}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleUpdate(customer.id)} color="warning">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleConfirmDelete(customer.id)} color="error">
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
        <DialogTitle>Bạn có chắc chắn muốn xóa khách hàng này?</DialogTitle>
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

export default CustomerTable;
