package com.ksilisk.leetty.web.service.repository;

import com.ksilisk.leetty.web.service.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Set<Chat> findChatsByDailySendTime(String sendTime);
}
