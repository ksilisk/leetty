package com.ksilisk.leetty.telegram.bot.service;

import org.telegram.telegrambots.meta.api.objects.Chat;

import java.time.LocalTime;
import java.util.List;

public interface ChatService {
    List<Long> getUsersToSendDailyQuestion(LocalTime time);

    void updateTimeToSendDailyQuestion(Long chatId, String time);

    void addChat(Chat chat);
}
