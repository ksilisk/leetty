package com.ksilisk.telegram.bot.starter.guard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;

@Slf4j
public abstract class Guard<S, E, G> implements org.springframework.statemachine.guard.Guard<S, E> {
    public abstract G getGuard();

    public abstract boolean guard(StateContext<S, E> context);

    @Override
    public boolean evaluate(StateContext<S, E> context) {
        try {
            return guard(context);
        } catch (Exception e) {
            log.error("Error while evaluating guard. StateContext: {}", context, e);
            return false;
        }
    }
}
