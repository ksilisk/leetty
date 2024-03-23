package com.ksilisk.leetty.web.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.web.service.entity.User;
import com.ksilisk.leetty.web.service.exception.EntityAlreadyExistsException.UserAlreadyExistsException;
import com.ksilisk.leetty.web.service.exception.EntityNotFoundException.UserNotFountException;
import com.ksilisk.leetty.web.service.repository.UserRepository;
import com.ksilisk.leetty.web.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final ObjectMapper OM = new ObjectMapper().setSerializationInclusion(NON_NULL);

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.userId());
        if (user.isEmpty()) {
            User newUser = OM.convertValue(userDto, User.class);
            userRepository.save(newUser);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFountException::new);
        return OM.convertValue(user, UserDto.class);
    }
}
