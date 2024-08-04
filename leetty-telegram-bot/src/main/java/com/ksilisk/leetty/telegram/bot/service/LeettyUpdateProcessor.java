package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.payload.LeettyBotCommand;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INVALID;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.MESSAGE;

@Slf4j
@Service
public class LeettyUpdateProcessor implements UpdateProcessor {
    private static final Map<String, LeettyBotEvent> EVENTS_MAP =
            Arrays.stream(LeettyBotEvent.values())
                    .collect(Collectors.toMap(Enum::toString, Function.identity()));

    private static final Map<String, LeettyBotCommand> COMMANDS_MAP =
            Arrays.stream(LeettyBotCommand.values())
                    .collect(Collectors.toMap(LeettyBotCommand::getCommand, Function.identity()));
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    public LeettyUpdateProcessor(StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService) {
        this.stateMachineService = stateMachineService;
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
        return EVENTS_MAP.getOrDefault(givenEvent, INVALID);
    }

    private LeettyBotEvent resolveCommand(String givenCommand) {
        if (COMMANDS_MAP.containsKey(givenCommand)) {
            return COMMANDS_MAP.get(givenCommand).getEvent();
        }
        return INVALID;
    }
}
