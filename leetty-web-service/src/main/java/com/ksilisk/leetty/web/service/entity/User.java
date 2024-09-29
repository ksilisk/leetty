package com.ksilisk.leetty.web.service.entity;

import com.ksilisk.leetty.common.dto.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long userId;
    private String firstName;
    private String secondName;
    private String username;
    private String leetcodeUsername;

    public void patch(UserDto userDto) {
        if (userDto.firstName() != null) {
            this.firstName = userDto.firstName();
        }
        if (userDto.secondName() != null) {
            this.secondName = userDto.secondName();
        }
        if (userDto.username() != null) {
            this.username = userDto.username();
        }
        if (userDto.leetcodeUsername() != null) {
            this.leetcodeUsername = userDto.leetcodeUsername();
        }
    }
}
