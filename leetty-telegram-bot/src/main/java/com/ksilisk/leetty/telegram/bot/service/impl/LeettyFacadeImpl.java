package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.dto.ChatDto;
import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.telegram.bot.feign.ChatClient;
import com.ksilisk.leetty.telegram.bot.feign.UserClient;
import com.ksilisk.leetty.telegram.bot.service.LeettyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalTime;
import java.util.List;

import static com.ksilisk.leetty.telegram.bot.action.impl.UpdateSendDailyTimeAction.TIME_FORMATTER;

@Service
@RequiredArgsConstructor
class LeettyFacadeImpl implements LeettyFacade {
    private final ChatClient chatClient;
    private final UserClient userClient;

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
    public void addUser(User user) {
        UserDto userDto = UserDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getLastName())
                .username(user.getUserName())
                .build();
        userClient.addUser(userDto);
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
