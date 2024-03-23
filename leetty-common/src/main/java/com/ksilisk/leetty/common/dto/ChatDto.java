package com.ksilisk.leetty.common.dto;

import lombok.Builder;

@Builder
public record ChatDto(Long chatId, String title,
                      String description, String dailySendTime) {
}
