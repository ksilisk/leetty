package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.payload.AdminInfo;

import java.util.List;

public interface AdminService {
    List<AdminInfo> getAdmins();

    void addAdmin(AdminInfo admin);
}
