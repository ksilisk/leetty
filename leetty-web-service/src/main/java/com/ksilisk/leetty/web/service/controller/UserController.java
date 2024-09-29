package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.codegen.types.User;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import com.ksilisk.leetty.web.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v{ver}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void putUser(@RequestBody UserDto userDto) {
        userService.putUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void patchUser(@RequestBody UserDto userDto) {
        userService.patchUser(userDto);
    }

    @GetMapping("/{id}/profile")
    public UserProfile getUserProfile(@PathVariable("id") Long userId) {
        return userService.getUserProfile(userId);
    }
}
