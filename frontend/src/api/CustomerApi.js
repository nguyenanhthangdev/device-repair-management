import axios from "axios";

const CUSTOMER_URL = "http://localhost:8085/api/customers";

export const getAllCustomer = () => axios.get(CUSTOMER_URL);

export const getCustomerById = (id) => axios.get(`${CUSTOMER_URL}/${id}`);

export const createCustomer = (customer) => axios.post(CUSTOMER_URL, customer);

export const updateCustomer = (id, customer) => axios.put(`${CUSTOMER_URL}/${id}`, customer);

export const deleteCustomer = (id) => axios.delete(`${CUSTOMER_URL}/${id}`);