import React, { useEffect, useState } from "react";
import {
  TextField,
  Button,
  Container,
  Typography,
  Box,
} from "@mui/material";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { getDeviceTypeById, updateDeviceType } from "../api/DeviceTypeApi";

const EditDeviceTypeForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  const [deviceType, setDeviceType] = useState({
    name: ""
  });
  
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    getDeviceTypeById(id).then((res) => {
      const deviceTypeData = res.data;
      setDeviceType({
        ...deviceTypeData,
      });
    });
  }, [id]);

  const validate = () => {
    const newErrors = {};

    if (!deviceType.name.trim())
      newErrors.name = "Tên loại thiết bị không được để trống.";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDeviceType({ ...deviceType, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    if (isSubmitting) return;
    setIsSubmitting(true);

    try {
      await updateDeviceType(id, deviceType);
      navigate("/device-types", {
        state: {
          success: true,
          message: "Cập nhật loại thiết bị thành công!",
        },
      });
    } catch (err) {
      console.error("Lỗi cập nhật:", err);
      navigate("/device-types", {
        state: {
          success: false,
          message: "Cập nhật loại thiết bị thất bại!",
        },
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Container maxWidth="100%">
      <Typography variant="h5" gutterBottom>
        Chỉnh sửa loại thiết bị
      </Typography>

      <Box component="form" onSubmit={handleSubmit} noValidate>
        <TextField
          fullWidth
          margin="normal"
          label="Tên thiết bị"
          name="name"
          value={deviceType.name}
          onChange={handleChange}
          error={!!errors.name}
          helperText={errors.name}
        />

        <Box mt={2}>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            disabled={isSubmitting}
          >
            Cập nhật loại thiết bị
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default EditDeviceTypeForm;
