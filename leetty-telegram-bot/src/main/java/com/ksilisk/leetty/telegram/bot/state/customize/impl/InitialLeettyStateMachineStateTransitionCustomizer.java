package com.ksilisk.leetty.telegram.bot.state.customize.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.leetty.telegram.bot.state.customize.LeettyStateMachineStateTransitionCustomizer;
import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.START_COMMAND;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.INITIAL;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_NEW_COMMAND;

@Component
public class InitialLeettyStateMachineStateTransitionCustomizer implements LeettyStateMachineStateTransitionCustomizer {
    @Override
    public StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> customize(StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> transitionConfigurer,
                                                                                       DelegateAction<LeettyBotStates, LeettyBotEvent> delegateAction,
                                                                                       DelegateGuard<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> delegateGuard) throws Exception {
        return transitionConfigurer
                .withExternal()
                .source(INITIAL)
                .target(WAIT_NEW_COMMAND)
                .event(START_COMMAND)
                .action(delegateAction)
                .and();
    }
}
