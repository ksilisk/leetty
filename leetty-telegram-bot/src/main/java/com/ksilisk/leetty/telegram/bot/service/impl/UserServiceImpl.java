package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import com.ksilisk.leetty.telegram.bot.feign.UserClient;
import com.ksilisk.leetty.telegram.bot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserClient userClient;

    @Override
    public void addUser(User user) {
        UserDto userDto = UserDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getLastName())
                .username(user.getUserName())
                .build();
        userClient.addUser(userDto);
    }

    @Override
    public void updateUser(UserDto user) {
        userClient.updateUser(user);
    }

    @Override
    public UserDto getUser(Long userId) {
        return userClient.getUser(userId);
    }

    @Override
    public UserProfile getUserProfile(Long userId) {
        return userClient.getUserProfile(userId);
    }
}
