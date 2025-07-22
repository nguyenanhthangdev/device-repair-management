import React, { useEffect, useState } from "react";
import {
  TextField,
  Button,
  Container,
  MenuItem,
  Typography,
  Box,
  FormControl,
  InputLabel,
  Select,
  Alert,
} from "@mui/material";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { getDeviceById, updateDevice } from "../api/DeviceApi";
import { getAllDeviceTypes } from "../api/DeviceTypeApi";

const EditDeviceForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  const [device, setDevice] = useState({
    name: "",
    typeId: "",
    description: "",
    receivedDate: "",
    status: "",
  });

  const [types, setTypes] = useState([]);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    getDeviceById(id).then((res) => {
      const deviceData = res.data;
      setDevice({
        ...deviceData,
        typeId: deviceData.type?.id || "",
      });
    });
    getAllDeviceTypes().then((res) => setTypes(res.data));
  }, [id]);

  const validate = () => {
    const newErrors = {};

    if (!device.name.trim())
      newErrors.name = "Tên thiết bị không được để trống.";
    if (!device.typeId) newErrors.typeId = "Vui lòng chọn loại thiết bị.";
    if (!device.receivedDate)
      newErrors.receivedDate = "Ngày nhận không được để trống.";
    if (!device.status.trim()) newErrors.status = "Vui lòng chọn trạng thái.";
    if (!device.description.trim())
      newErrors.description = "Mô tả lỗi không được để trống.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDevice({ ...device, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    if (isSubmitting) return;
    setIsSubmitting(true);

    try {
      await updateDevice(id, device);
      navigate("/devices", {
        state: {
          success: true,
          message: "Cập nhật thiết bị thành công!",
        },
      });
    } catch (err) {
      console.error("Lỗi cập nhật:", err);
      navigate("/devices", {
        state: {
          success: false,
          message: "Cập nhật thiết bị thất bại!",
        },
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Container maxWidth="100%">
      <Typography variant="h5" gutterBottom>
        Chỉnh sửa thiết bị
      </Typography>

      <Box component="form" onSubmit={handleSubmit} noValidate>
        <TextField
          fullWidth
          margin="normal"
          label="Tên thiết bị"
          name="name"
          value={device.name}
          onChange={handleChange}
          error={!!errors.name}
          helperText={errors.name}
        />

        <FormControl fullWidth margin="normal" error={!!errors.typeId}>
          <InputLabel id="type-label">Loại thiết bị</InputLabel>
          <Select
            labelId="type-label"
            name="typeId"
            value={device.typeId}
            onChange={handleChange}
            label="Loại thiết bị"
          >
            {types.map((type) => (
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
          fullWidth
          margin="normal"
          label="Mô tả"
          name="description"
          value={device.description}
          onChange={handleChange}
          error={!!errors.description}
          helperText={errors.description}
        />

        <TextField
          fullWidth
          margin="normal"
          label="Ngày nhận"
          type="date"
          name="receivedDate"
          value={device.receivedDate}
          onChange={handleChange}
          InputLabelProps={{ shrink: true }}
          error={!!errors.receivedDate}
          helperText={errors.receivedDate}
        />

        <FormControl fullWidth margin="normal" error={!!errors.status}>
          <InputLabel>Trạng thái</InputLabel>
          <Select
            name="status"
            value={device.status}
            label="Trạng thái"
            onChange={handleChange}
          >
            <MenuItem value="Chưa sửa">Chưa sửa</MenuItem>
            <MenuItem value="Đang sửa">Đang sửa</MenuItem>
            <MenuItem value="Đã sửa xong">Đã sửa xong</MenuItem>
            <MenuItem value="Đã trả">Đã trả</MenuItem>
          </Select>
        </FormControl>

        <Box mt={2}>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            disabled={isSubmitting}
          >
            Cập nhật thiết bị
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default EditDeviceForm;
