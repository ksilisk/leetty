package com.ksilisk.leetty.telegram.bot.feign;

import com.ksilisk.leetty.common.dto.ChatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "chatClient", url = "${leetty.web-service-url}/v1/chats")
public interface ChatClient {
    @PatchMapping
    void updateChat(@RequestBody ChatDto chatDto);

    @PutMapping
    void addChat(@RequestBody ChatDto chatDto);

    @GetMapping("/daily-question")
    List<Long> getUsersToSendDaily(@RequestParam("time") String time);
}
