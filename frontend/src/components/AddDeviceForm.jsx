import React, { useState, useEffect } from "react";
import {
  TextField,
  Button,
  MenuItem,
  Typography,
  Box,
  FormControl,
  InputLabel,
  Select,
} from "@mui/material";
import { getAllDeviceTypes } from "../api/DeviceTypeApi";
import { createDevice } from "../api/DeviceApi";
import { useNavigate } from "react-router-dom";

function AddDeviceForm() {
  const [device, setDevice] = useState({
    name: "",
    typeId: "",
    description: "",
    receivedDate: "",
    status: "",
  });

  const [deviceTypes, setDeviceTypes] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    getAllDeviceTypes()
      .then((res) => setDeviceTypes(res.data))
      .catch((err) => console.error("Lỗi tải loại thiết bị:", err));
  }, []);

  const validate = () => {
    const errors = [];

    if (!device.name.trim()) errors.push("Tên thiết bị không được để trống");
    if (!device.typeId) errors.push("Vui lòng chọn loại thiết bị");
    if (!device.description.trim())
      errors.push("Mô tả lỗi không được để trống");
    if (!device.receivedDate) errors.push("Vui lòng chọn ngày tiếp nhận");
    if (!device.status) errors.push("Vui lòng chọn trạng thái");

    return errors;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDevice((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const errors = validate();
    if (errors.length > 0) {
      alert(errors.join("\n"));
      return;
    }

    if (isSubmitting) return; // tránh gửi nhiều lần
    setIsSubmitting(true);

    try {
      await createDevice(device);
      navigate("/devices");
    } catch (err) {
      console.error("Lỗi thêm thiết bị:", err);
      alert("Thêm thiết bị thất bại!");
    } finally {
      setIsSubmitting(false); // cho phép submit lại nếu cần
    }
  };

  return (
    <Box sx={{ maxWidth: "100%", mx: "auto", p: 3 }}>
      <Typography variant="h5" gutterBottom>
        Thêm Thiết Bị
      </Typography>

      <form onSubmit={handleSubmit}>
        <TextField
          label="Tên thiết bị"
          name="name"
          value={device.name}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />

        <FormControl fullWidth margin="normal" required>
          <InputLabel id="device-type-label">Loại thiết bị</InputLabel>
          <Select
            labelId="device-type-label"
            name="typeId"
            value={device.typeId}
            onChange={handleChange}
            label="Loại thiết bị"
          >
            {deviceTypes.map((type) => (
              <MenuItem key={type.id} value={type.id}>
                {type.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <TextField
          label="Mô tả lỗi"
          name="description"
          value={device.description}
          onChange={handleChange}
          fullWidth
          margin="normal"
          required
        />

        <TextField
          label="Ngày nhận"
          name="receivedDate"
          type="date"
          value={device.receivedDate}
          onChange={handleChange}
          fullWidth
          margin="normal"
          InputLabelProps={{ shrink: true }}
          required
        />

        <FormControl fullWidth margin="normal" required>
          <InputLabel id="device-status-label">Trạng thái</InputLabel>
          <Select
            labelId="device-status-label"
            name="status"
            value={device.status}
            onChange={handleChange}
            label="Trạng thái"
          >
            <MenuItem value="CHUA_SUA">Chưa sửa</MenuItem>
            <MenuItem value="DANG_SUA">Đang sửa</MenuItem>
            <MenuItem value="DA_SUA_XONG">Đã sửa xong</MenuItem>
            <MenuItem value="DA_TRA">Đã trả</MenuItem>
          </Select>
        </FormControl>

        <Button
          variant="contained"
          type="submit"
          color="primary"
          fullWidth
          disabled={isSubmitting}
          sx={{ mt: 2 }}
        >
          {isSubmitting ? "Đang xử lý..." : "Thêm"}
        </Button>
      </form>
    </Box>
  );
}

export default AddDeviceForm;
