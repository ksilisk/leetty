package com.ksilisk.telegram.bot.starter.processor;

import org.telegram.telegrambots.meta.api.objects.Update;

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
}
