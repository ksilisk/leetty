package com.ksilisk.leetty.telegram.bot.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.ZoneId;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("leetty")
public class LeettyProperties {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final String DEFAULT_MESSAGES_SAMPLES_PATH = "classpath:messages";

    private ZoneId zoneId = DEFAULT_ZONE_ID;
    private String messagesSamplesPath = DEFAULT_MESSAGES_SAMPLES_PATH;
    private InlineModeProperties inline = new InlineModeProperties();

    @Getter
    @Setter
    @Component
    @Validated
    public static class InlineModeProperties {
        private HelpMessageProperties helpMessage = new HelpMessageProperties();
        private ResultMessageProperties resultMessage = new ResultMessageProperties();

        @Getter
        @Setter
        @Validated
        public static class HelpMessageProperties {
            private static final String DEFAULT_HELP_MESSAGE_TITLE = "LeettyBot Usage:";
            private static final String DEFAULT_HELP_MESSAGE_DESCRIPTION = "@leettybot https://leetcode.com/problems/...";
            private static final String DEFAULT_HELP_MESSAGE_THUMBNAIL_URL = "https://upload.wikimedia.org/wikipedia/commons/1/19/LeetCode_logo_black.png";
            private static final ThumbnailProperties DEFAULT_HELP_MESSAGE_THUMBNAIL_PROPERTIES =
                    ThumbnailProperties.builder().url(DEFAULT_HELP_MESSAGE_THUMBNAIL_URL).build();

            @NotBlank
            private String title = DEFAULT_HELP_MESSAGE_TITLE;
            private String description = DEFAULT_HELP_MESSAGE_DESCRIPTION;
            private ThumbnailProperties thumbnail = DEFAULT_HELP_MESSAGE_THUMBNAIL_PROPERTIES;
        }

        @Getter
        @Setter
        @Validated
        public static class ResultMessageProperties {
            private static final String DEFAULT_RESULT_MESSAGE_THUMBNAIL_URL = "https://upload.wikimedia.org/wikipedia/commons/1/19/LeetCode_logo_black.png";
            private static final ThumbnailProperties DEFAULT_RESULT_MESSAGE_THUMBNAIL_PROPERTIES =
                    ThumbnailProperties.builder().url(DEFAULT_RESULT_MESSAGE_THUMBNAIL_URL).build();

            private ThumbnailProperties thumbnail = DEFAULT_RESULT_MESSAGE_THUMBNAIL_PROPERTIES;
        }

        @Getter
        @Setter
        @Builder
        @Validated
        public static class ThumbnailProperties {
            @URL
            private String url;
            @Positive
            private Integer width;
            @Positive
            private Integer height;
        }
    }
}
