package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.entity.Chat;
import com.ksilisk.leetty.web.service.exception.EntityAlreadyExistsException.ChatAlreadyExistsException;
import com.ksilisk.leetty.web.service.exception.EntityNotFoundException.ChatNotFoundException;
import com.ksilisk.leetty.web.service.repository.ChatRepository;
import com.ksilisk.leetty.web.service.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ChatServiceImplTest {
    final ChatService chatService;
    final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImplTest(ChatRepository chatRepository) {
        this.chatService = new ChatServiceImpl(chatRepository);
        this.chatRepository = chatRepository;
    }

    @Test
    void addExistsChat_shouldThrowException() {
        // given
        ChatDto chatDto = ChatDto.builder().chatId(1L).description("desc").title("title").build();
        Chat chat = Chat.builder().chatId(1L).description("desc").title("title").build();
        chatRepository.save(chat);
        // then
        Assertions.assertThrowsExactly(ChatAlreadyExistsException.class, () -> chatService.addChat(chatDto));
    }

    @Test
    void addAndGetChat_shouldBeEquals() {
        // given
        ChatDto chatDto = ChatDto.builder().chatId(1L).description("desc").title("title").build();
        // when
        chatService.addChat(chatDto);
        // then
        Assertions.assertEquals(chatDto, chatService.getChat(1L));
    }

    @Test
    void getNotExistsChat_shouldThrowException() {
        // given
        Long charId = 10L;
        // then
        Assertions.assertThrowsExactly(ChatNotFoundException.class, () -> chatService.getChat(charId));
    }

    @Test
    void updateNotExistsChat_shouldThrowException() {
        // given
        ChatDto chatDto = ChatDto.builder().chatId(1L).build();
        // then
        Assertions.assertThrows(ChatNotFoundException.class, () -> chatService.update(chatDto));
    }

    @Test
    void updateExistChat_shouldUpdateAndEqualsToNew() {
        // given
        Chat existChat = Chat.builder()
                .chatId(1L)
                .title("sumer")
                .description("some").build();
        chatRepository.save(existChat);
        ChatDto updateChat = ChatDto.builder().chatId(1L).description("super")
                .title("someNew").dailySendTime("10:00").build();
        // when
        chatService.update(updateChat);
        // then
        Assertions.assertEquals(updateChat, chatService.getChat(1L));
    }

    @Test
    void addChatWithNullChatId_shouldThrowException() {
        // given
        ChatDto chatDto = ChatDto.builder().build();
        // then
        Assertions.assertThrows(RuntimeException.class, () -> chatService.addChat(chatDto));
    }
}