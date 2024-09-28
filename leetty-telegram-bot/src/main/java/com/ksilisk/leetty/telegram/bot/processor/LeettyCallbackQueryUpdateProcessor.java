package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.payload.StateMachineUpdateProcessingResult;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.CallbackQueryUpdateProcessor;
import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INVALID;

@Component
@RequiredArgsConstructor
public class LeettyCallbackQueryUpdateProcessor extends CallbackQueryUpdateProcessor {
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    @Override
    public void processUpdate(Update update) {
        StateMachinePayload stateMachinePayload = getPayload(update);
        if (stateMachinePayload.event() != INVALID) {
            stateMachinePayload.stateMachine().sendEvent(createEventMono(stateMachinePayload)).subscribe();
        }
    }

    @Override
    public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
        updates.map(this::getPayload)
                .filter(payload -> payload.event() != INVALID)
                .flatMap(payload -> payload.stateMachine().sendEvent(createEventMono(payload)))
                .subscribe();
        return Flux.empty();
    }

    private Mono<Message<LeettyBotEvent>> createEventMono(StateMachinePayload payload) {
        return Mono.just(MessageBuilder.createMessage(payload.event(), new MessageHeaders(
                Map.of(UPDATE_HEADER_KEY, payload.update(), CALLBACK_DATA_HEADER_KEY, payload.data()))));
    }

    private StateMachinePayload getPayload(Update update) {
        CallbackData data = CallbackData.from(update.getCallbackQuery().getData());
        LeettyBotEvent event = LeettyBotEvent.resolve(data.getEvent());
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine = stateMachineService.acquireStateMachine(chatId);
        return new StateMachinePayload(stateMachine, event, data, update);
    }

    private record StateMachinePayload(
            StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine, LeettyBotEvent event,
            CallbackData data, Update update) {
    }
}
