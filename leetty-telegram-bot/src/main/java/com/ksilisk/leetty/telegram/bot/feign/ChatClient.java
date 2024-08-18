package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.dto.ChatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "chatClient", url = "${leetty.web-service-url}")
public interface ChatClient {
    @PatchMapping("/api/chats")
    void updateChat(@RequestBody ChatDto chatDto);

    @PutMapping("/api/chats")
    void addChat(@RequestBody ChatDto chatDto);

    @GetMapping("/api/chats/daily-question")
    List<Long> getUsersToSendDaily(@RequestParam("time") String time);
}
