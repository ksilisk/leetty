package com.ksilisk.leetty.telegram.bot.processor.impl;

import com.ksilisk.leetty.telegram.bot.command.LeettyBotCommand;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties.Bot;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessor;
import com.ksilisk.leetty.telegram.bot.service.CallbackData;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INVALID;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.MESSAGE;

@Slf4j
@Service
public class LeettyUpdateProcessor extends UpdateProcessor<LeettyBotStates, LeettyBotEvent> {
    public LeettyUpdateProcessor(StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService) {
        super(stateMachineService);
    }

    @Override
    public void process(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine =
                    stateMachineService.acquireStateMachine(message.getChatId().toString());
            if (message.isCommand()) {
                stateMachine.sendEvent(
                        Mono.just(MessageBuilder.createMessage(resolveCommand(message.getText()),
                                new MessageHeaders(Collections.singletonMap(UPDATE_HEADER_KEY, update))))).subscribe();
            } else {
                stateMachine.sendEvent(
                        Mono.just(MessageBuilder.createMessage(MESSAGE,
                                new MessageHeaders(Collections.singletonMap(UPDATE_HEADER_KEY, update))))).blockLast();
            }
        } else if (update.hasCallbackQuery()) {
            CallbackData data = CallbackData.from(update.getCallbackQuery().getData());
            LeettyBotEvent event = resolveEvent(data.getEvent());
            if (event != INVALID) {
                StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine =
                        stateMachineService.acquireStateMachine(update.getCallbackQuery().getMessage().getChatId().toString());
                stateMachine.sendEvent(
                        Mono.just(MessageBuilder.createMessage(event, new MessageHeaders(
                                Map.of(UPDATE_HEADER_KEY, update, CALLBACK_DATA_HEADER_KEY, data))))).subscribe();
            }
        } else {
            log.info("Unsupported update. Update: {}", update);
        }
    }

    private LeettyBotEvent resolveEvent(String givenEvent) {
        for (LeettyBotEvent event : LeettyBotEvent.values()) {
            if (event.name().equals(givenEvent)) {
                return event;
            }
        }
        return INVALID;
    }

    private LeettyBotEvent resolveCommand(String givenCommand) {
        for (LeettyBotCommand command : LeettyBotCommand.values()) {
            if (command.equals(givenCommand)) {
                return command.getEvent();
            }
        }
        return INVALID;
    }

    @Override
    public Bot getBot() {
        return Bot.LEETTY;
    }
}
