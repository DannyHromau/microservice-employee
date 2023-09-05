package com.dannyhromau.employee.core.base;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<Entity extends BaseEntity> {
    List<Entity> getEntities(Pageable pageable);

    Entity getEntityById(UUID id);

    Entity addEntity(Entity entity);

    UUID deleteEntity(UUID id);

    Entity updateEntity(Entity entity);
}
