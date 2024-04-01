package com.ksilisk.leetty.telegram.bot.client;

import com.ksilisk.leetty.common.dto.ChatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "chatClient", url = "${leetty.web-service-url}")
public interface ChatClient {
    @PatchMapping("/api/chats")
    void updateChat(@RequestBody ChatDto chatDto);

    @PutMapping("/api/chats")
    void addChat(@RequestBody ChatDto chatDto);
}
