package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.entity.Chat;
import com.ksilisk.leetty.web.service.exception.type.EntityNotFoundException.ChatNotFoundException;
import com.ksilisk.leetty.web.service.repository.ChatRepository;
import com.ksilisk.leetty.web.service.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    void addExistsChat_shouldOverwrite() {
        // given
        ChatDto chatDto = ChatDto.builder().chatId(1L).description("desc").title("title").build();
        Chat chat = Chat.builder().chatId(1L).build();
        chatRepository.save(chat);
        // then
        chatService.putChat(chatDto);
        Assertions.assertEquals(chatDto, chatService.getChat(1L));
    }

    @Test
    void addAndGetChat_shouldBeEquals() {
        // given
        ChatDto chatDto = ChatDto.builder().chatId(1L).description("desc").title("title").build();
        // when
        chatService.putChat(chatDto);
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
    void updateExistsChat_whenUpdatedValueIsEmpty_shouldUpdateAndBeEmpty() {
        // given
        Chat existsChat = Chat.builder().chatId(1L).dailySendTime("10:00").build();
        chatRepository.save(existsChat);
        ChatDto updateChat = ChatDto.builder().chatId(1L).dailySendTime("").build();
        // when
        chatService.update(updateChat);
        // then
        Assertions.assertEquals("", chatService.getChat(1L).dailySendTime());
    }

    @Test
    void addChatWithNullChatId_shouldThrowException() {
        // given
        ChatDto chatDto = ChatDto.builder().build();
        // then
        Assertions.assertThrows(RuntimeException.class, () -> chatService.putChat(chatDto));
    }

    @Test
    void getChatsToSendDaily_whenChatsNotEmpty_shouldReturnEmptyList() {
        // given
        List<Chat> expectedChats = List.of(
                Chat.builder().chatId(1L).dailySendTime("10:00").build(),
                Chat.builder().chatId(2L).dailySendTime("10:00").build(),
                Chat.builder().chatId(4L).dailySendTime("10:00").build());
        List<Chat> allChats = new ArrayList<>(expectedChats);
        allChats.addAll(List.of(
                Chat.builder().chatId(3L).dailySendTime("15:00").build(),
                Chat.builder().chatId(5L).dailySendTime("16:00").build()));
        List<Long> expectedIds = expectedChats.stream().map(Chat::getChatId).toList();
        // when
        chatRepository.saveAll(allChats);
        List<Long> actualIds = chatService.getChatsToSendDaily("10:00");
        // then
        Assertions.assertIterableEquals(expectedIds, actualIds);
    }

    @Test
    void getChatsToSendDaily_whenChatsEmpty_shouldReturnEmptyList() {
        // given
        List<Long> expectedChatList = Collections.emptyList();
        // when
        List<Long> actualChatList = chatService.getChatsToSendDaily("18:00");
        // then
        Assertions.assertEquals(expectedChatList, actualChatList);
    }
}