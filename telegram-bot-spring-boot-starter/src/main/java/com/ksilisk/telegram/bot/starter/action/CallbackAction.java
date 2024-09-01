package com.ksilisk.telegram.bot.starter.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.CALLBACK_DATA_HEADER_KEY;
import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.UPDATE_HEADER_KEY;

@Slf4j
public abstract class CallbackAction<S, E, T> extends Action<S, E> {
    public abstract void handle(Update update, T callbackData);

    public void handleInaccessible(Update update, T callbackData) {
        log.info("Handled callback action with inaccessible message. Update: {}", update);
    }

    @Override
    public void handle(Update update) {
        throw new IllegalStateException("Method unavailable. Callback Action need callback data.");
    }

    @Override
    public void execute(StateContext<S, E> context) {
        try {
            Object update = context.getMessageHeaders().get(UPDATE_HEADER_KEY);
            Object callbackData = context.getMessageHeaders().get(CALLBACK_DATA_HEADER_KEY);
            if (update != null && callbackData != null) {
                if (((Update) update).getCallbackQuery().getMessage().getDate() == 0) {
                    handleInaccessible((Update) update, (T) callbackData);
                } else {
                    handle((Update) update, (T) callbackData);
                }
            }
        } catch (Exception exception) {
            log.warn("Error while handling state context callback action. StateContext: {}", context, exception);
        }
    }
}
