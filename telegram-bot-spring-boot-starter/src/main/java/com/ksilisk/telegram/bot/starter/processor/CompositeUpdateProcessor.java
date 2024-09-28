package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import org.springframework.statemachine.StateMachineEventResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CompositeUpdateProcessor implements UpdateProcessor {
    private final Set<UpdateProcessor> processors = new CopyOnWriteArraySet<>();

    public CompositeUpdateProcessor(Iterable<UpdateProcessor> updateProcessors) {
        updateProcessors.forEach(processors::add);
    }

    @Override
    public void process(Update update) {
        processors.forEach(proc -> proc.process(update));
    }

    @Override
    public Flux<UpdateProcessingResult> processFlux(Flux<Update> updates) {
        return Flux.concat(processors.stream().map(proc -> proc.processFlux(updates)).toList());
    }
}
