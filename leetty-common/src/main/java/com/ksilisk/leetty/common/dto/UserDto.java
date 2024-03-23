package com.ksilisk.leetty.common.dto;

import lombok.Builder;

@Builder
public record UserDto(Long userId, String firstName, String secondName, String username) {
}
