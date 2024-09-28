package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.StateMachineUpdateProcessingResult;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.InlineQueryUpdateProcessor;
import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class LeettyInlineQueryUpdateProcessor extends InlineQueryUpdateProcessor {
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    @Override
    public void processUpdate(Update update) {
        sendEvent(update).subscribe();
    }

    @Override
    public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
        return updates.flatMap(this::sendEvent)
                .map(eventResult -> new StateMachineUpdateProcessingResult());
    }

    private Flux<StateMachineEventResult<LeettyBotStates, LeettyBotEvent>> sendEvent(Update update) {
        StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine =
                stateMachineService.acquireStateMachine(update.getInlineQuery().getFrom().getId().toString());
        return stateMachine.sendEvent(
                Mono.just(MessageBuilder.createMessage(LeettyBotEvent.INLINE_QUERY_COMMAND,
                        new MessageHeaders(Collections.singletonMap(UPDATE_HEADER_KEY, update)))));
    }
}
