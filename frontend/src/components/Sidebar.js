import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

function Sidebar({ isOpen, toggleSidebar }) {
  return (
    <div className={`sidebar ${isOpen ? 'open' : ''}`}>
      <button className="close-button" onClick={toggleSidebar}>×</button>
      <h2>Admin</h2>
      <ul>
        <li><Link to="/devices" onClick={toggleSidebar}>Devices</Link></li>
        {/* Các link khác */}
      </ul>
    </div>
  );
}

export default Sidebar;
