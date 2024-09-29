package com.ksilisk.telegram.bot.starter.config;

import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.config.customize.StateMachineStateTransitionCustomizer;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.redis.RedisPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.ArrayList;
import java.util.List;

public abstract class StateMachineConfiguration<S extends Enum<S>, E extends Enum<E>, G> extends EnumStateMachineConfigurerAdapter<S, E> {
    protected final List<StateMachineStateTransitionCustomizer<S, E, G>> transitionCustomizers = new ArrayList<>();
    protected final StateMachineRuntimePersister<S, E, String> stateMachineRuntimePersister;
    protected final DelegateGuard<S, E, G> delegateGuard;
    protected final DelegateAction<S, E> delegateAction;

    protected StateMachineConfiguration(RedisStateMachineRepository stateMachineRepository,
                                        DelegateAction<S, E> delegateAction, DelegateGuard<S, E, G> delegateGuard,
                                        List<StateMachineStateTransitionCustomizer<S, E, G>> transitionCustomizers) {
        this.stateMachineRuntimePersister = new RedisPersistingStateMachineInterceptor<>(stateMachineRepository);
        this.delegateAction = delegateAction;
        this.delegateGuard = delegateGuard;
        this.transitionCustomizers.addAll(transitionCustomizers);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<S, E> config) throws Exception {
        config.withPersistence().runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<S, E> transitions) throws Exception {
        for (StateMachineStateTransitionCustomizer<S, E, G> transitionCustomizer : this.transitionCustomizers) {
            transitions = transitionCustomizer.customize(transitions, this.delegateAction, this.delegateGuard);
        }
    }
}
