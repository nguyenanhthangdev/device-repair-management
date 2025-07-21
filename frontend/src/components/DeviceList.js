import React, { useEffect, useState } from 'react';
import DeviceService from '../services/DeviceService';
import './DeviceList.css';

function DeviceList() {
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    DeviceService.getDevices().then(res => {
      setDevices(res.data);
    });
  }, []);

  return (
    <div className="table-container">
      <h2>Danh sách thiết bị</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Tên thiết bị</th>
            <th>Loại</th>
            <th>Mô tả lỗi</th>
            <th>Ngày sửa</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          {devices.map(device => (
            <tr key={device.id}>
              <td>{device.id}</td>
              <td>{device.name}</td>
              <td>{device.ame}</td>
              <td>{device.description}</td>
              <td>{device.receivedDate}</td>
              <td>{device.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default DeviceList;
