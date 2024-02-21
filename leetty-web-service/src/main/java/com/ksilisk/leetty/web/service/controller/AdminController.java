package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.payload.AdminInfo;
import com.ksilisk.leetty.web.service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public List<AdminInfo> getAdmins() {
        return adminService.getAdmins();
    }

    @PostMapping
    public void addAdmin(@RequestBody AdminInfo admin) {
        adminService.addAdmin(admin);
    }
}
