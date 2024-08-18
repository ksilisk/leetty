package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.dto.ChatDto;

import java.util.List;

public interface ChatService {
    void update(ChatDto chatDto);

    void putChat(ChatDto chatDto);

    ChatDto getChat(Long id);

    List<Long> getChatsToSendDaily(String time);
}
