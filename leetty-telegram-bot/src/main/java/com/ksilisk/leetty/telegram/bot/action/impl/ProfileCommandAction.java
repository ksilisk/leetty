package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.common.user.UserProfile;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.UserService;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileCommandAction extends LeettyAction {
    private static final String LEETCODE_PROFILE_NOT_SET_MESSAGE_SAMPLE_FILENAME = "leetcode_profile_not_set_message.txt";

    private final Sender sender;
    private final UserService userService;
    private final MessageSampleReader messageSampleReader;

    @Override
    public void handle(Update update) {
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();
        UserDto user = userService.getUser(userId);
        if (user.leetcodeUsername() == null) {
            String message = messageSampleReader.read(LEETCODE_PROFILE_NOT_SET_MESSAGE_SAMPLE_FILENAME);
            sender.execute(SendMessage.builder().chatId(chatId).text(message).build());
        } else {
            UserProfile userProfile = userService.getUserProfile(userId);
            sender.execute(prepareProfileMessage(userProfile, chatId));
        }
    }

    private SendMessage prepareProfileMessage(UserProfile userProfile, Long chatId) {
        StringBuilder message = new StringBuilder();
        message.append("Here's your LeetCode profile info:\n\n");
        if (userProfile.name() != null) {
            message.append(String.format("Name: %s\n", userProfile.name()));
        }
        message.append(String.format("Username: %s\n", userProfile.username()));
        if (userProfile.rank() != null) {
            message.append(String.format("Rank: %d\n", userProfile.rank()));
        }
        if (userProfile.solvedQuestions() != null) {
            message.append(String.format("Solved questions: %d\n", userProfile.solvedQuestions()));
        }
        if (userProfile.country() != null) {
            message.append(String.format("Country: %s\n", userProfile.country()));
        }
        if (userProfile.work() != null) {
            message.append(String.format("Work: %s\n", userProfile.work()));
        }
        if (userProfile.education() != null) {
            message.append(String.format("Education: %s\n", userProfile.education()));
        }
        if (userProfile.githubProfile() != null) {
            message.append(String.format("GitHub profile: %s\n", userProfile.githubProfile()));
        }
        if (userProfile.linkedInProfile() != null) {
            message.append(String.format("LinkedIn profile: %s\n", userProfile.linkedInProfile()));
        }
        if (userProfile.twitterProfile() != null) {
            message.append(String.format("Twitter profile: %s\n", userProfile.twitterProfile()));
        }
        return SendMessage.builder()
                .chatId(chatId)
                .text(message.toString())
                .disableWebPagePreview(true)
                .build();
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.PROFILE_COMMAND;
    }
}
