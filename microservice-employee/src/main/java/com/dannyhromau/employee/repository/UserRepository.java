package com.dannyhromau.employee.repository;

import com.dannyhromau.employee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findByUpdatedOnAndId(ZonedDateTime updateTime, UUID id);
}
