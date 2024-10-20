package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import org.telegram.telegrambots.meta.api.objects.User;

public interface UserService {
    void addUser(User user);

    void updateUser(UserDto user);

    UserDto getUser(Long userId);

    UserProfile getUserProfile(Long userId);
}
