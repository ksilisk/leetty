package com.ksilisk.leetty.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.entity.Chat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class Test {
    private static final ObjectMapper OM = new ObjectMapper().setSerializationInclusion(NON_NULL);
    public static void main(String[] args) {
        Chat chat = new Chat();
        chat.setChatId(842L);
        chat.setTitle("sdjfksj");
        chat.setDescription("fjdsjfas");
        chat.setDailySendTime("10%20");
        ChatDto chatDto = OM.convertValue(chat, ChatDto.class);
        System.out.println(chatDto.toString());
    }
}
