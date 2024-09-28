package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

public interface UpdateProcessor {
    String UPDATE_HEADER_KEY = "update";
    String CALLBACK_DATA_HEADER_KEY = "callback_data";

    void process(Update update);

    Flux<UpdateProcessingResult> processFlux(Flux<Update> updates);
}
