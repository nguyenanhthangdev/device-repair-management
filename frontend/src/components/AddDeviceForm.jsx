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
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    getAllDeviceTypes()
      .then((res) => setDeviceTypes(res.data))
      .catch((err) => console.error("Lỗi tải loại thiết bị:", err));
  }, []);

  const validate = () => {
    const newErrors = {};

    if (!device.name.trim()) newErrors.name = "Tên thiết bị không được để trống.";
    if (!device.typeId) newErrors.typeId = "Vui lòng chọn loại thiết bị.";
    if (!device.description.trim()) newErrors.description = "Mô tả lỗi không được để trống.";
    if (!device.receivedDate) newErrors.receivedDate = "Ngày nhận không được để trống.";
    if (!device.status) newErrors.status = "Vui lòng chọn trạng thái.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDevice((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) return;
    if (isSubmitting) return;

    setIsSubmitting(true);

    try {
      await createDevice(device);
      navigate("/devices", {
        state: { success: true, message: "Thêm thiết bị thành công!" },
      });
    } catch (err) {
      console.error("Lỗi thêm thiết bị:", err);
      navigate("/devices", {
        state: { success: false, message: "Thêm thiết bị thất bại!" },
      });
    } finally {
      setIsSubmitting(false);
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
          error={Boolean(errors.name)}
          helperText={errors.name}
        />

        <FormControl fullWidth margin="normal">
          <InputLabel id="device-type-label">Loại thiết bị</InputLabel>
          <Select
            labelId="device-type-label"
            name="typeId"
            value={device.typeId}
            onChange={handleChange}
            label="Loại thiết bị"
            error={Boolean(errors.typeId)}
          >
            {deviceTypes.map((type) => (
              <MenuItem key={type.id} value={type.id}>
                {type.name}
              </MenuItem>
            ))}
          </Select>
          {errors.typeId && (
            <Typography variant="caption" color="error">
              {errors.typeId}
            </Typography>
          )}
        </FormControl>

        <TextField
          label="Mô tả lỗi"
          name="description"
          value={device.description}
          onChange={handleChange}
          fullWidth
          margin="normal"
          error={Boolean(errors.description)}
          helperText={errors.description}
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
          error={Boolean(errors.receivedDate)}
          helperText={errors.receivedDate}
        />

        <FormControl fullWidth margin="normal">
          <InputLabel id="device-status-label">Trạng thái</InputLabel>
          <Select
            labelId="device-status-label"
            name="status"
            value={device.status}
            onChange={handleChange}
            label="Trạng thái"
            error={Boolean(errors.status)}
          >
            <MenuItem value="Chưa sửa">Chưa sửa</MenuItem>
            <MenuItem value="Đang sửa">Đang sửa</MenuItem>
            <MenuItem value="Đã sửa xong">Đã sửa xong</MenuItem>
            <MenuItem value="Đã trả">Đã trả</MenuItem>
          </Select>
          {errors.status && (
            <Typography variant="caption" color="error">
              {errors.status}
            </Typography>
          )}
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
