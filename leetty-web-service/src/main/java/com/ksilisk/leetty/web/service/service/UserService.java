package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.dto.UserDto;

public interface UserService {
    void addUser(UserDto userDto);

    UserDto getUser(Long id);
}
