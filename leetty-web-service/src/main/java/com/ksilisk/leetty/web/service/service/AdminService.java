package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.dto.AdminDto;

import java.util.List;

public interface AdminService {
    List<AdminDto> getAdmins();

    void addAdmin(AdminDto admin);
}
