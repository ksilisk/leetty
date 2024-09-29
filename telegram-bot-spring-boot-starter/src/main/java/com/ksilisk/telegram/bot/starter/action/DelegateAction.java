package com.ksilisk.telegram.bot.starter.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class DelegateAction<S, E> implements Action<S, E> {
    private final Map<E, com.ksilisk.telegram.bot.starter.action.Action<S, E>> actionMap = new HashMap<>();

    public DelegateAction(List<com.ksilisk.telegram.bot.starter.action.Action<S, E>> actions) {
        this.actionMap.putAll(actions.stream()
                .collect(toMap(com.ksilisk.telegram.bot.starter.action.Action::getEvent, Function.identity())));
    }

    @Override
    public void execute(StateContext<S, E> context) {
        actionMap.get(context.getEvent()).execute(context);
    }

    public com.ksilisk.telegram.bot.starter.action.Action<S, E> getAction(E event) {
        return actionMap.get(event);
    }
}
