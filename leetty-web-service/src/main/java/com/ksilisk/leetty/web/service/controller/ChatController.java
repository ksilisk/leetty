package com.ksilisk.leetty.web.service.controller;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chats")
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

    @PostMapping
    public void addChar(@RequestBody ChatDto chatDto) {
        chatService.addChat(chatDto);
    }
}
