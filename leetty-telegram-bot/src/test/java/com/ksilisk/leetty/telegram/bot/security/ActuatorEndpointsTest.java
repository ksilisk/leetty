package com.ksilisk.leetty.telegram.bot.security;

import com.ksilisk.leetty.telegram.bot.annotation.LeettySpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@LeettySpringBootTest
class ActuatorEndpointsTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    void checkAvailabilityHealthEndpoint_shouldBeAvailableAndStatus200() throws Exception {
        // when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/actuator/health")).andReturn();
        // then
        Assertions.assertNull(mvcResult.getResolvedException());
        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    @WithAnonymousUser
    void checkAvailabilityPrometheusEndpoint_shouldBeAvailableAndStatus200() throws Exception {
        // when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/actuator/prometheus")).andReturn();
        // then
        Assertions.assertNull(mvcResult.getResolvedException());
        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }
}