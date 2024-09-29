package com.ksilisk.leetty.telegram.bot.state.customize.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.leetty.telegram.bot.state.customize.LeettyStateMachineStateTransitionCustomizer;
import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.*;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_NEW_COMMAND;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_SEND_LEETCODE_PROFILE;

@Component
public class WaitNewCommandLeettyStateMachineStateTransitionCustomizer implements LeettyStateMachineStateTransitionCustomizer {
    @Override
    public StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> customize(StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> transitionConfigurer,
                                                                                       DelegateAction<LeettyBotStates, LeettyBotEvent> delegateAction,
                                                                                       DelegateGuard<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> delegateGuard) throws Exception {
        // TODO: refactor this at better future
        return transitionConfigurer
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(START_COMMAND)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(SETTINGS_COMMAND)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(DAILY_QUESTION_COMMAND)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(INVALID)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(HELP_COMMAND)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(SET_SEND_DAILY_TIME)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(UPDATE_SEND_DAILY_TIME)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(INLINE_QUERY_COMMAND)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_ACCEPTANCE)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_CONSTRAINTS)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_DIFFICULTY)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_HINTS)
                .action(delegateAction)
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_EXAMPLES)
                .action(delegateAction)
                .and()

                // part of get_question_topics button
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_TOPICS)
                .action(delegateAction)
                .and()

                // part of /random_question command
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .action(delegateAction)
                .event(RANDOM_QUESTION_COMMAND)
                .and()

                // part of /profile command
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(PROFILE_COMMAND)
                .action(delegateAction)
                .and()

                // part of setting leetcode profile for user
                .withExternal()
                .source(WAIT_NEW_COMMAND)
                .target(WAIT_SEND_LEETCODE_PROFILE)
                .event(SET_LEETCODE_PROFILE)
                .action(delegateAction)
                .and();
    }
}
