package com.ksilisk.telegram.bot.starter.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class WebhookProperties {
    private boolean reactive;
    private boolean enabled;
    @NotBlank
    private String kafkaBrokers;
    private Map<String, String> consumerConfigs = new HashMap<>();
    @NotBlank
    private String consumerGroup;
    @NotBlank
    private String certificatePath;
    @NotBlank
    private String url;
    @NotBlank
    private String ipAddress;
    @Min(1)
    @Max(100)
    private int maxConnections = 40;
    private List<String> allowedUpdates = new ArrayList<>();
    private String secretToken;
    private boolean dropPendingUpdates;
}
