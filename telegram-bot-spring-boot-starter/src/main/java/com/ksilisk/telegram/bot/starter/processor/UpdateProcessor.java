package com.ksilisk.telegram.bot.starter.processor;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProcessor {
    String UPDATE_HEADER_KEY = "update";
    String CALLBACK_DATA_HEADER_KEY = "callback_data";

    void process(Update update);
}
