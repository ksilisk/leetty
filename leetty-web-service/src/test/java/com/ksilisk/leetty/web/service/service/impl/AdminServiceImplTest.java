package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.dto.AdminDto;
import com.ksilisk.leetty.web.service.repository.AdminRepository;
import com.ksilisk.leetty.web.service.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@DataJpaTest
class AdminServiceImplTest {
    final AdminRepository adminRepository;
    final AdminService adminService;

    @Autowired
    public AdminServiceImplTest(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        this.adminService = new AdminServiceImpl(adminRepository);
    }

    @Test
    void addAndGetAdminInfos_thenGetCorrectly() {
        // given
        List<AdminDto> adminDtos = List.of(new AdminDto(12345L), new AdminDto(89234L));
        // when
        adminDtos.forEach(adminService::addAdmin);
        // then
        Assertions.assertIterableEquals(adminDtos, adminService.getAdmins());
    }

    @Test
    void addNonUniqueUserId_shouldThrowException() {
        // given
        List<AdminDto> adminDtos = List.of(new AdminDto(111L), new AdminDto(111L));
        // then
        Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () -> adminDtos.forEach(adminService::addAdmin));
    }

    @Test
    void addAdminWithUserIdNull_shouldThrowException() {
        // given
        AdminDto adminDto = AdminDto.builder().build();
        // then
        Assertions.assertThrows(RuntimeException.class, () -> adminService.addAdmin(adminDto));
    }
}