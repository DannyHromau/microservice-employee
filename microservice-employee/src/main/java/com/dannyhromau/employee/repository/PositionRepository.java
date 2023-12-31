package com.dannyhromau.employee.repository;

import com.dannyhromau.employee.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
    Position findByPosition(String position);

    Position findByUpdatedOnAndId(ZonedDateTime updateTime, UUID id);
}
