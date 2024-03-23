package com.ksilisk.leetty.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private Long chatId;
    private String title;
    private String description;
    private String dailySendTime;
}
