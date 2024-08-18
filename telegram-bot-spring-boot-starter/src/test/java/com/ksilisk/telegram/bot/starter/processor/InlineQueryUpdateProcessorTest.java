package com.ksilisk.telegram.bot.starter.processor;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InlineQueryUpdateProcessorTest {
    @Test
    void processUpdateThrowException_shouldNotFail() {
        // given
        Update update = new Update();
        update.setInlineQuery(new InlineQuery());
        InlineQueryUpdateProcessor updateProcessor = new InlineQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                throw new RuntimeException("Some exception");
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
        InlineQueryUpdateProcessor updateProcessor = new InlineQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                update.setUpdateId(0);
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
        update.setInlineQuery(new InlineQuery());
        InlineQueryUpdateProcessor updateProcessor = new InlineQueryUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                update.setUpdateId(0);
            }
        };
        // then
        updateProcessor.process(update);
        // when
        assertEquals(0, update.getUpdateId());
    }
}