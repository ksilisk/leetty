package com.ksilisk.leetty.web.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@Entity(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private long userId;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp addedDate;

    public Admin(long userId) {
        this.userId = userId;
    }
}
