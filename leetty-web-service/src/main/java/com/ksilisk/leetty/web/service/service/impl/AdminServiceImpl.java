package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.dto.AdminDto;
import com.ksilisk.leetty.web.service.entity.Admin;
import com.ksilisk.leetty.web.service.repository.AdminRepository;
import com.ksilisk.leetty.web.service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public List<AdminDto> getAdmins() {
        return adminRepository.findAll().stream()
                .map(admin -> new AdminDto(admin.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public void addAdmin(AdminDto admin) {
        adminRepository.save(new Admin(admin.userId()));
    }
}
