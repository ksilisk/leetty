package com.ksilisk.telegram.bot.starter.processor;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageUpdateProcessorTest {
    @Test
    void processUpdateThrowException_shouldNotFail() {
        // given
        Update update = new Update();
        update.setMessage(new Message());
        MessageUpdateProcessor messageUpdateProcessor = new MessageUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                throw new RuntimeException("Some exception");
            }
        };
        // then
        assertDoesNotThrow(() -> messageUpdateProcessor.process(update));
    }

    @Test
    void updateHasNotMessage_shouldSkipUpdate() {
        // given
        Update update = new Update();
        update.setUpdateId(1);
        MessageUpdateProcessor updateProcessor = new MessageUpdateProcessor() {
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
        update.setMessage(new Message());
        MessageUpdateProcessor messageUpdateProcessor = new MessageUpdateProcessor() {
            @Override
            public void processUpdate(Update update) {
                update.setUpdateId(0);
            }
        };
        // then
        messageUpdateProcessor.process(update);
        // when
        assertEquals(0, update.getUpdateId());
    }
}