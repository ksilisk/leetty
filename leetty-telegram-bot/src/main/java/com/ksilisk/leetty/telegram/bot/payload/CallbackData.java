package com.ksilisk.leetty.telegram.bot.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class CallbackData {
    private static final ObjectMapper om = new ObjectMapper();

    private final String event;
    private final Map<String, Object> map;

    public CallbackData() {
        this((String) null, null);
    }

    public <E extends Enum<E>> CallbackData(Enum<E> event) {
        this(event.name(), new HashMap<>());
    }

    public <E extends Enum<E>> CallbackData(Enum<E> event, Map<String, Object> data) {
        this(event.name(), new HashMap<>(data));
    }

    public String getEvent() {
        return event;
    }

    public Object getKey(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public static CallbackData from(String stringData) {
        try {
            new CallbackData(LeettyBotEvent.START_COMMAND);
            return om.readValue(stringData, CallbackData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while convert stringData to object CallbackData. StringData: " + stringData, e);
        }
    }

    @Override
    public String toString() {
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while convert CallbackData to string", e);
        }
    }
}
