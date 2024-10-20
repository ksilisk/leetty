package com.ksilisk.telegram.bot.starter.config.customize;

import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public interface StateMachineStateTransitionCustomizer<S, E, G> {
    StateMachineTransitionConfigurer<S, E> customize(StateMachineTransitionConfigurer<S, E> transitionConfigurer,
                                                     DelegateAction<S, E> delegateAction,
                                                     DelegateGuard<S, E, G> delegateGuard) throws Exception;
}
