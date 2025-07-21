import axios from 'axios'

const DEVICE_API_BASE_URL = "http://localhost:8085/api/devices";

const getDevices = () => {
  return axios.get(DEVICE_API_BASE_URL);
};

export default {
    getDevices
};