package com.ksilisk.leetty.telegram.bot.client;

import com.ksilisk.leetty.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userClient", url = "${leetty.web-service-url}")
public interface UserClient {
    @PostMapping("/api/users")
    void addUser(@RequestBody UserDto userDto);
}