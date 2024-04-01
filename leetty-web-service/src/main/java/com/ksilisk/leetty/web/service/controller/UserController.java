package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.web.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public void putUser(@RequestBody UserDto userDto) {
        userService.putUser(userDto);
    }

    @GetMapping("/{id}")
    public void getUser(@PathVariable("id") Long userId) {
        userService.getUser(userId);
    }
}
