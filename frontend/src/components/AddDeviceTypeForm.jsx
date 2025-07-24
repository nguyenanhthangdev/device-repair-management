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
import { createDeviceType } from "../api/DeviceTypeApi";
import { useNavigate } from "react-router-dom";

function AddDeviceTypeForm() {
  const [deviceType, setDeviceType] = useState({
    name: "",
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const validate = () => {
    const newErrors = {};

    if (!deviceType.name.trim()) newErrors.name = "Tên loại thiết bị không được để trống.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDeviceType((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) return;
    if (isSubmitting) return;

    setIsSubmitting(true);

    try {
      await createDeviceType(deviceType);
      navigate("/device-types", {
        state: { success: true, message: "Thêm loại thiết bị thành công!" },
      });
    } catch (err) {
      console.error("Lỗi thêm loại thiết bị:", err);
      navigate("/device-types", {
        state: { success: false, message: "Thêm loại thiết bị thất bại!" },
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Box sx={{ maxWidth: "100%", mx: "auto", p: 3 }}>
      <Typography variant="h5" gutterBottom>
        Thêm Loại Thiết Bị
      </Typography>

      <form onSubmit={handleSubmit}>
        <TextField
          label="Tên loại thiết bị"
          name="name"
          value={deviceType.name}
          onChange={handleChange}
          fullWidth
          margin="normal"
          error={Boolean(errors.name)}
          helperText={errors.name}
        />

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

export default AddDeviceTypeForm;
