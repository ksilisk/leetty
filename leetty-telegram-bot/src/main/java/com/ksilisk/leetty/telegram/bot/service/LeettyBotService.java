package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalTime;
import java.util.List;

public interface LeettyBotService {
    List<Long> getUsersToSendDailyQuestion(LocalTime time);

    void updateTimeToSendDailyQuestion(Long chatId, String time);

    DailyCodingQuestion getDailyQuestion();

    void addUser(User user);

    void addChat(Chat chat);
}
