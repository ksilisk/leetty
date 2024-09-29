package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.codegen.types.User;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userClient", url = "${leetty.web-service-url}/v1/users")
public interface UserClient {
    @PutMapping
    void addUser(@RequestBody UserDto userDto);

    @PatchMapping
    void updateUser(@RequestBody UserDto userDto);

    @GetMapping("{id}")
    UserDto getUser(@PathVariable("id") Long userId);

    @GetMapping("/{id}/profile")
    UserProfile getUserProfile(@PathVariable("id") Long userId);
}
