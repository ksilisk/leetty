package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v{ver}/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{id}")
    public ChatDto getChat(@PathVariable("id") Long chatId) {
        return chatService.getChat(chatId);
    }

    @PatchMapping
    public void updateChat(@RequestBody ChatDto chatDto) {
        chatService.update(chatDto);
    }

    @PutMapping
    public void putChat(@RequestBody ChatDto chatDto) {
        chatService.putChat(chatDto);
    }

    @GetMapping("/daily-question")
    public List<Long> getChatsToSendDaily(@RequestParam("time") String time) {
        return chatService.getChatsToSendDaily(time);
    }
}
