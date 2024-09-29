package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.telegram.bot.feign.ChatClient;
import com.ksilisk.leetty.telegram.bot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.time.LocalTime;
import java.util.List;

import static com.ksilisk.leetty.telegram.bot.action.impl.UpdateSendDailyTimeAction.TIME_FORMATTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatClient chatClient;

    @Override
    public List<Long> getUsersToSendDailyQuestion(LocalTime time) {
        return chatClient.getUsersToSendDaily(TIME_FORMATTER.format(time));
    }

    @Override
    public void updateTimeToSendDailyQuestion(Long chatId, String time) {
        chatClient.updateChat(ChatDto.builder()
                .chatId(chatId)
                .dailySendTime(time)
                .build());
    }

    @Override
    public void addChat(Chat chat) {
        ChatDto chatDto = ChatDto.builder()
                .chatId(chat.getId())
                .description(chat.getDescription())
                .title(chat.getTitle())
                .build();
        chatClient.addChat(chatDto);
    }
}
