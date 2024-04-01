package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.dto.ChatDto;

public interface ChatService {
    void update(ChatDto chatDto);

    void putChat(ChatDto chatDto);

    ChatDto getChat(Long id);
}
