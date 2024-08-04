package com.ksilisk.telegram.bot.starter.config;

import com.ksilisk.telegram.bot.starter.action.Action;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.data.redis.RedisPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class StateMachineConfiguration<S extends Enum<S>, E extends Enum<E>> extends EnumStateMachineConfigurerAdapter<S, E> {
    protected final StateMachineRuntimePersister<S, E, String> stateMachineRuntimePersister;
    protected final Map<E, Action<S, E>> eventActionMap;

    protected StateMachineConfiguration(RedisStateMachineRepository stateMachineRepository,
                                        List<Action<S, E>> actions) {
        this.stateMachineRuntimePersister = new RedisPersistingStateMachineInterceptor<>(stateMachineRepository);
        this.eventActionMap = actions.stream()
                .collect(Collectors.toMap(Action::getEvent, Function.identity()));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<S, E> config) throws Exception {
        config.withPersistence().runtimePersister(stateMachineRuntimePersister);
    }
}
