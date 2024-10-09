package com.yt.furns.service.impl;

import com.yt.furns.dao.impl.AdminDAO;
import com.yt.furns.dao.impl.AdminDAOImpl;
import com.yt.furns.javaBean.Admin;

public class AdminServiceImpl implements AdminService {
    AdminDAO adminDAO = new AdminDAOImpl();
    @Override
    public boolean registerAdmin(Admin admin) {
        return adminDAO.addAdmin(admin) == 1;
    }

    @Override
    public boolean isExistsUsername(String username) {
        return adminDAO.getAdminByName(username) != null;
    }

    @Override
    public Admin login(Admin admin) {
        return adminDAO.queryAdminByName(admin.getUsername(), admin.getPassword());
    }
}
