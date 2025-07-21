import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import DeviceList from './components/DeviceList';
import './App.css';

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const toggleSidebar = () => setSidebarOpen(!sidebarOpen);

  return (
    <Router>
      <div className="app-layout">
        <Sidebar isOpen={sidebarOpen} toggleSidebar={toggleSidebar} />
        <div className="main-content">
          <button className="menu-button" onClick={toggleSidebar}>
            ☰
          </button>
          <Routes>
            <Route path="/devices" element={<DeviceList />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
