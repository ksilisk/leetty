package com.ksilisk.leetty.telegram.bot.guard.impl;

import com.ksilisk.leetty.common.dto.UserDto;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.exception.type.LeetCodeValidationUrlException;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.guard.LeettyGuard;
import com.ksilisk.leetty.telegram.bot.service.UserService;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.UPDATE_HEADER_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateLeetCodeProfileGuard extends LeettyGuard {
    private static final String UPDATE_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME = "update_leetcode_profile_message.txt";
    private static final String INCORRECT_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME = "incorrect_leetcode_profile_message.txt";

    private final LeetCodeUrlParser leetCodeUrlParser;
    private final Sender sender;
    private final MessageSampleReader messageSampleReader;
    private final UserService userService;

    @Override
    public boolean guard(StateContext<LeettyBotStates, LeettyBotEvent> context) {
        MessageHeaders messageHeaders = context.getMessageHeaders();
        Update update = (Update) messageHeaders.get(UPDATE_HEADER_KEY);
        try {
            String leetCodeUsername = leetCodeUrlParser.parseUsername(update.getMessage().getText());
            UserDto userDto = UserDto.builder()
                    .leetcodeUsername(leetCodeUsername)
                    .userId(update.getMessage().getFrom().getId())
                    .build();
            userService.updateUser(userDto);
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(messageSampleReader.read(UPDATE_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME))
                    .build();
            sender.execute(sendMessage);
            return true;
        } catch (LeetCodeValidationUrlException ex) {
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(messageSampleReader.read(INCORRECT_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME))
                    .build();
            sender.execute(sendMessage);
            log.warn("Error while parsing leetcode user profile url. Update: {}", update, ex);
            return false;
        }
    }

    @Override
    public LeettyBotGuard getGuard() {
        return LeettyBotGuard.UPDATE_LEETCODE_PROFILE;
    }
}
