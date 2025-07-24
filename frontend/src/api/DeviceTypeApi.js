import axios from "axios";

const DEVICE_TYPE_URL = "http://localhost:8085/api/device-types";

export const getAllDeviceTypes = () => axios.get(DEVICE_TYPE_URL);

export const getDeviceTypeById = (id) => axios.get(`${DEVICE_TYPE_URL}/${id}`);

export const createDeviceType = (deviceType) => axios.post(DEVICE_TYPE_URL, deviceType);

export const updateDeviceType = (id, deviceType) => axios.put(`${DEVICE_TYPE_URL}/${id}`, deviceType);

export const deleteDeviceType = (id) => axios.delete(`${DEVICE_TYPE_URL}/${id}`);