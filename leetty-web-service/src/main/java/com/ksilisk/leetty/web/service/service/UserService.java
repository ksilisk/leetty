package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.codegen.types.User;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;

public interface UserService {
    void putUser(UserDto userDto);

    UserDto getUser(Long id);

    void patchUser(UserDto userDto);

    UserProfile getUserProfile(Long userId);
}
