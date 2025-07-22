import axios from "axios";

const DEVICE_TYPE_URL = "http://localhost:8085/api/device-types";

export const getAllDeviceTypes = () => axios.get(DEVICE_TYPE_URL);
