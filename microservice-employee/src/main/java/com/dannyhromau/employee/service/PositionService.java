package com.dannyhromau.employee.service;

import com.dannyhromau.employee.core.base.BaseService;
import com.dannyhromau.employee.model.Position;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PositionService extends BaseService<Position> {
    @Override
    List<Position> getEntities(Pageable pageable);

    @Override
    Position getEntityById(UUID id);

    @Override
    Position addEntity(Position entity);

    @Override
    UUID deleteEntity(UUID id);

    @Override
    Position updateEntity(Position entity);
}
