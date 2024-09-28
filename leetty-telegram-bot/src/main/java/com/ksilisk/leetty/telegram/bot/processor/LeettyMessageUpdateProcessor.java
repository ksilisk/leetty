package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.StateMachineUpdateProcessingResult;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.processor.MessageUpdateProcessor;
import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.MESSAGE;
import static com.ksilisk.leetty.telegram.bot.payload.LeettyBotCommand.resolve;
import static java.util.Collections.singletonMap;
import static org.springframework.messaging.support.MessageBuilder.createMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeettyMessageUpdateProcessor extends MessageUpdateProcessor {
    private final StateMachineService<LeettyBotStates, LeettyBotEvent> stateMachineService;

    @Override
    public void processUpdate(Update update) {
        sendUpdateEvent(update).subscribe();
    }

    @Override
    public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
        log.info("At update processor impl");
        updates.subscribe(u -> sendUpdateEvent(u).subscribe());
        return Flux.empty();
    }

    private Flux<StateMachineEventResult<LeettyBotStates, LeettyBotEvent>> sendUpdateEvent(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        StateMachine<LeettyBotStates, LeettyBotEvent> stateMachine = stateMachineService.acquireStateMachine(chatId);
        if (update.getMessage().isCommand()) {
            return stateMachine.sendEvent(
                    Mono.just(createMessage(resolve(update.getMessage().getText()),
                            new MessageHeaders(singletonMap(UPDATE_HEADER_KEY, update)))));
        } else {
            return stateMachine.sendEvent(
                    Mono.just(createMessage(MESSAGE, new MessageHeaders(singletonMap(UPDATE_HEADER_KEY, update)))));
        }
    }
}
