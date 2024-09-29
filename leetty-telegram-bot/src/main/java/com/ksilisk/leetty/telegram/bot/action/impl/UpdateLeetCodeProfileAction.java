package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.UserService;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateLeetCodeProfileAction extends LeettyAction {
    private static final String UPDATE_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME = "update_leetcode_profile_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;
    private final UserService userService;
    private final LeetCodeUrlParser leetCodeUrlParser;

    @Override
    public void handle(Update update) {
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();

//        UserDto userDto = UserDto.builder().userId(userId).leetcodeUsername().build();
//        userService.updateUser(); // todo implement this
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.UPDATE_LEETCODE_PROFILE;
    }
}
