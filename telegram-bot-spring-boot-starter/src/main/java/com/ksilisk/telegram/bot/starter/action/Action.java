package com.ksilisk.telegram.bot.starter.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.UPDATE_HEADER_KEY;

@Slf4j
public abstract class Action<S, E> implements org.springframework.statemachine.action.Action<S, E> {
    public abstract E getEvent();

    public abstract void handle(Update update);

    @Override
    public void execute(StateContext<S, E> context) {
        try {
            Object update = context.getMessageHeaders().get(UPDATE_HEADER_KEY);
            if (update != null) {
                handle((Update) update);
            }
        } catch (Exception exception) {
            log.warn("Error while handle state context action. StateContext: {}", context, exception);
        }
    }
}
