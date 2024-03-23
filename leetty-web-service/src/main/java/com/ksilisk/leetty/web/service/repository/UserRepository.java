package com.ksilisk.leetty.web.service.repository;

import com.ksilisk.leetty.web.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
