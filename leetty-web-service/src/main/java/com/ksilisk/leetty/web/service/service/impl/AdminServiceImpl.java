package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.payload.AdminInfo;
import com.ksilisk.leetty.web.service.entity.Admin;
import com.ksilisk.leetty.web.service.repository.AdminRepository;
import com.ksilisk.leetty.web.service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public List<AdminInfo> getAdmins() {
        return adminRepository.findAll().stream()
                .map(admin -> new AdminInfo(admin.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public void addAdmin(AdminInfo admin) {
        adminRepository.save(new Admin(admin.userId()));
    }
}
