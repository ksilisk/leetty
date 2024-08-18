package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalTime;
import java.util.List;

public interface LeettyFacade {
    List<Long> getUsersToSendDailyQuestion(LocalTime time);

    void updateTimeToSendDailyQuestion(Long chatId, String time);

    DailyCodingQuestion getDailyQuestion();

    void addUser(User user);

    void addChat(Chat chat);

    Question parseQuestionFromUrl(String titleSlug);
}
