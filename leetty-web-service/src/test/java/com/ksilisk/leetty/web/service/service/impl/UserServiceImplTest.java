package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.web.service.exception.EntityAlreadyExistsException.UserAlreadyExistsException;
import com.ksilisk.leetty.web.service.exception.EntityNotFoundException.UserNotFountException;
import com.ksilisk.leetty.web.service.repository.UserRepository;
import com.ksilisk.leetty.web.service.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserServiceImplTest {
    final UserRepository userRepository;
    final UserService userService;

    @Autowired
    public UserServiceImplTest(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    void addNotExistUser_shouldAddAndEquals() {
        // given
        UserDto userDto = UserDto.builder().userId(1L).username("some")
                .firstName("some").build();
        // when
        userService.addUser(userDto);
        // then
        Assertions.assertEquals(userDto, userService.getUser(1L));
    }

    @Test
    void addExistsUser_shouldThrowException() {
        // given
        UserDto userDto = UserDto.builder().userId(1L).secondName("test").build();
        // when
        userService.addUser(userDto);
        // then
        Assertions.assertThrowsExactly(UserAlreadyExistsException.class, () -> userService.addUser(userDto));
    }

    @Test
    void addUserWithNullUserId_shouldThrowException() {
        // given
        UserDto userDto = UserDto.builder().build();
        // then
        Assertions.assertThrows(RuntimeException.class, () -> userService.addUser(userDto));
    }

    @Test
    void getNotExistUser_shouldThrowException() {
        // given
        Long userId = 10L;
        // then
        Assertions.assertThrowsExactly(UserNotFountException.class, () -> userService.getUser(userId));
    }

    @Test
    void getNullUserId_shouldThrowException() {
        // given
        Long userId = null;
        // then
        Assertions.assertThrows(RuntimeException.class, () -> userService.getUser(userId));
    }
}