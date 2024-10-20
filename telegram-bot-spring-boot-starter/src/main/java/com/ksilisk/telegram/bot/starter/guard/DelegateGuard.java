package com.ksilisk.telegram.bot.starter.guard;

import lombok.NoArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@NoArgsConstructor
public class DelegateGuard<S, E, G> implements Guard<S, E> {
    private final Map<G, com.ksilisk.telegram.bot.starter.guard.Guard<S, E, G>> guardMap = new HashMap<>();

    public DelegateGuard(List<com.ksilisk.telegram.bot.starter.guard.Guard<S, E, G>> guards) {
        this.guardMap.putAll(guards.stream()
                .collect(toMap(com.ksilisk.telegram.bot.starter.guard.Guard::getGuard, Function.identity())));
    }

    @Override
    public boolean evaluate(StateContext<S, E> context) {
        throw new IllegalStateException("Should not get here");
    }

    public com.ksilisk.telegram.bot.starter.guard.Guard<S, E, G> getGuard(G guard) {
        return guardMap.get(guard);
    }
}
