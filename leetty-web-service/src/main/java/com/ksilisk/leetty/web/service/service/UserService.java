package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.dto.UserDto;

public interface UserService {
    void putUser(UserDto userDto);

    UserDto getUser(Long id);
}
