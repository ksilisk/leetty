package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackQueryUpdateProcessorTest {
    @Test
    void processUpdateThrowException_shouldNotFail() {
        // given
        Update update = new Update();
        update.setCallbackQuery(new CallbackQuery());
        CallbackQueryUpdateProcessor updateProcessor = new CallbackQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                throw new RuntimeException("Some exception");
            }

            @Override
            public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
                return null;
            }
        };
        // then
        assertDoesNotThrow(() -> updateProcessor.process(update));
    }

    @Test
    void updateHasNotMessage_shouldSkipUpdate() {
        // given
        Update update = new Update();
        update.setUpdateId(1);
        CallbackQueryUpdateProcessor updateProcessor = new CallbackQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                update.setUpdateId(0);
            }

            @Override
            public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
                return null;
            }
        };
        // when
        updateProcessor.process(update);
        // then
        assertEquals(1, update.getUpdateId());
    }

    @Test
    void updateHasMessage_shouldNotSkipUpdate() {
        // given
        Update update = new Update();
        update.setUpdateId(1);
        update.setCallbackQuery(new CallbackQuery());
        CallbackQueryUpdateProcessor updateProcessor = new CallbackQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                update.setUpdateId(0);
            }

            @Override
            public Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates) {
                return null;
            }
        };
        // then
        updateProcessor.process(update);
        // when
        assertEquals(0, update.getUpdateId());
    }
}