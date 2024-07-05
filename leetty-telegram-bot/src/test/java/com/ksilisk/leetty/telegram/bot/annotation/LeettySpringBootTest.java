package com.ksilisk.leetty.telegram.bot.annotation;

import com.ksilisk.leetty.telegram.bot.config.RedisTestServerConfiguration;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = RedisTestServerConfiguration.class)
@AutoConfigureMockMvc
@AutoConfigureObservability
public @interface LeettySpringBootTest {
}
