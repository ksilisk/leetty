package com.ksilisk.leetty.web.service.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.web.service.entity.Chat;
import com.ksilisk.leetty.web.service.exception.type.EntityNotFoundException.ChatNotFoundException;
import com.ksilisk.leetty.web.service.repository.ChatRepository;
import com.ksilisk.leetty.web.service.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private static final ObjectMapper OM = new ObjectMapper().setSerializationInclusion(NON_NULL);

    private final ChatRepository chatRepository;

    @Override
    @Transactional
    public void update(ChatDto chatDto) {
        try {
            Chat chat = chatRepository.findById(chatDto.chatId())
                    .orElseThrow(ChatNotFoundException::new);
            OM.updateValue(chat, chatDto);
            chatRepository.save(chat);
        } catch (JsonMappingException e) {
            log.warn("Error while update chat from given DTO. ChatDTO: {}", chatDto, e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    @Transactional
    public void putChat(ChatDto chatDto) {
        Chat newChat = OM.convertValue(chatDto, Chat.class);
        chatRepository.save(newChat);
    }

    @Override
    public ChatDto getChat(Long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(ChatNotFoundException::new);
        return OM.convertValue(chat, ChatDto.class);
    }

    @Override
    public List<Long> getChatsToSendDaily(String time) {
        return chatRepository.findChatsByDailySendTime(time).stream()
                .map(Chat::getChatId)
                .toList();
    }
}
