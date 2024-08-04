package com.ksilisk.telegram.bot.starter.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BotProperties {
    @NotBlank
    private String username;
    @NotBlank
    private String token;
}
