import axios from "axios";

const BASE_URL = "http://localhost:8085/api/devices";

export const getAllDevices = () => axios.get(BASE_URL);
export const createDevice = (device) => axios.post(BASE_URL, device);
export const updateDevice = (id, device) => axios.put(`${BASE_URL}/${id}`, device);
export const deleteDevice = (id) => axios.delete(`${BASE_URL}/${id}`);
