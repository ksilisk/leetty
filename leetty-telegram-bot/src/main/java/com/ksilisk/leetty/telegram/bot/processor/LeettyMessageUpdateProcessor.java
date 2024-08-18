package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.LeettyBotCommand;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.MessageUpdateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.MESSAGE;

@Component
@RequiredArgsConstructor
public class LeettyMessageUpdateProcessor extends MessageUpdateProcessor {
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    @Override
    public void processUpdate(Update update) {
        Message message = update.getMessage();
        StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine =
                stateMachineService.acquireStateMachine(message.getChatId().toString());
        if (message.isCommand()) {
            stateMachine.sendEvent(
                    Mono.just(MessageBuilder.createMessage(LeettyBotCommand.resolve(message.getText()),
                            new MessageHeaders(Collections.singletonMap(UPDATE_HEADER_KEY, update))))).subscribe();
        } else {
            stateMachine.sendEvent(
                    Mono.just(MessageBuilder.createMessage(MESSAGE,
                            new MessageHeaders(Collections.singletonMap(UPDATE_HEADER_KEY, update))))).blockLast();
        }
    }
}
