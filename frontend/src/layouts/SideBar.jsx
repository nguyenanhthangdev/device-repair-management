import React from "react";
import {
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Box,
  Divider,
  Typography,
} from "@mui/material";
import DevicesIcon from "@mui/icons-material/Devices";
import HomeIcon from "@mui/icons-material/Home";
import PeopleIcon from "@mui/icons-material/People";
import BuildIcon from "@mui/icons-material/Build";
import { Link } from "react-router-dom";

const drawerWidth = 240;

const Sidebar = () => {
  return (
    <Drawer
      variant="permanent"
      anchor="left"
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          width: drawerWidth,
          boxSizing: "border-box",
        },
      }}
    >
      <Box
        sx={{
          height: 50,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          p: 1,
        }}
      >
        <Typography variant="h6" noWrap>
          Admin-Repair
        </Typography>
      </Box>

      <Divider />

      <List>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/">
            <ListItemIcon>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText primary="Trang chủ" />
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton component={Link} to="/devices">
            <ListItemIcon>
              <DevicesIcon />
            </ListItemIcon>
            <ListItemText primary="Thiết bị" />
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton component={Link} to="/customers">
            <ListItemIcon>
              <PeopleIcon />
            </ListItemIcon>
            <ListItemText primary="Khách hàng" />
          </ListItemButton>
        </ListItem>

        <ListItem disablePadding>
          <ListItemButton component={Link} to="/repairs">
            <ListItemIcon>
              <BuildIcon />
            </ListItemIcon>
            <ListItemText primary="Đơn sửa chữa" />
          </ListItemButton>
        </ListItem>
      </List>
    </Drawer>
  );
};

export default Sidebar;
