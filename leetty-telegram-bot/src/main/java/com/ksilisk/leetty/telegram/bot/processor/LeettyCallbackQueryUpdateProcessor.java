package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.CallbackQueryUpdateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INVALID;

@Component
@RequiredArgsConstructor
public class LeettyCallbackQueryUpdateProcessor extends CallbackQueryUpdateProcessor {
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    @Override
    public void processUpdate(Update update) {
        CallbackData data = CallbackData.from(update.getCallbackQuery().getData());
        LeettyBotEvent event = LeettyBotEvent.resolve(data.getEvent());
        if (event != INVALID) {
            StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine =
                    stateMachineService.acquireStateMachine(update.getCallbackQuery().getMessage().getChatId().toString());
            stateMachine.sendEvent(
                    Mono.just(MessageBuilder.createMessage(event, new MessageHeaders(
                            Map.of(UPDATE_HEADER_KEY, update, CALLBACK_DATA_HEADER_KEY, data))))).subscribe();
        }
    }
}
