package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.web.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v{ver}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public void putUser(@RequestBody UserDto userDto) {
        userService.putUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }
}
