// src/pages/Devices.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Devices = () => {
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    axios.get('/api/devices')
      .then(response => setDevices(response.data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div className="page-content">
      <h2>Device List</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Tên thiết bị</th>
            <th>Loại thiết bị</th>
            <th>Mô tả lỗi</th>
            <th>Ngày nhận</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          {devices.map(device => (
            <tr key={device.id}>
              <td>{device.id}</td>
              <td>{device.name}</td>
              <td>{device.type?.name || 'Không rõ'}</td>
              <td>{device.description}</td>
              <td>{device.receivedDate}</td>
              <td>{device.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Devices;
