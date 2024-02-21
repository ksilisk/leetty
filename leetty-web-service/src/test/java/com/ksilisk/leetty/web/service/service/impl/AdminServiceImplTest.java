package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.payload.AdminInfo;
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
    @Autowired
    AdminRepository adminRepository;

    @Test
    public void addAndGetAdminInfos_thenGetCorrectly() {
        // given
        AdminService adminService = new AdminServiceImpl(adminRepository);
        List<AdminInfo> adminInfos = List.of(new AdminInfo(12345), new AdminInfo(89234));
        // when
        adminInfos.forEach(adminService::addAdmin);
        // then
        Assertions.assertIterableEquals(adminInfos, adminService.getAdmins());
    }

    @Test
    public void addNonUniqueUserId_shouldThrowException() {
        // given
        AdminService adminService = new AdminServiceImpl(adminRepository);
        List<AdminInfo> adminInfos = List.of(new AdminInfo(111), new AdminInfo(111));
        // then
        Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () -> adminInfos.forEach(adminService::addAdmin));
    }
}