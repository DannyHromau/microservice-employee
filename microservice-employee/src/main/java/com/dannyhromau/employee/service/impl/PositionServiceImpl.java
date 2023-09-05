package com.dannyhromau.employee.service.impl;

import com.dannyhromau.employee.core.config.ErrorMessages;
import com.dannyhromau.employee.exception.DeletingEntityException;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidInputDataException;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.PositionRepository;
import com.dannyhromau.employee.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    public List<Position> getEntities(Pageable pageable) {
        return positionRepository.findAll(pageable).toList();
    }

    @Override
    public Position getEntityById(UUID id) {
        return checkValidData(id);
    }

    @Override
    public Position addEntity(Position position) {
        position.setCreatedOn(ZonedDateTime.now());
        position.setUpdatedOn(null);
        positionRepository.save(position);
        position = positionRepository.findByPosition(position.getPosition());
        if (position == null) {
            throw new InvalidInputDataException(ErrorMessages.CREATING_DATA_ERROR_MESSAGE.label);
        }
        return position;
    }

    @Override
    public UUID deleteEntity(UUID id) {
        Position position = checkValidData(id);
        positionRepository.deleteById(id);
        if (positionRepository.findByPosition(position.getPosition()) != null) {
            throw new DeletingEntityException(ErrorMessages.DELETING_DATA_ERROR_MESSAGE.label);
        }
        return id;
    }

    @Override
    public Position updateEntity(Position position) {
        ZonedDateTime updateTime = ZonedDateTime.now();
        position.setUpdatedOn(updateTime);
        positionRepository.save(position);
        if (positionRepository.findByUpdatedOnAndId(updateTime, position.getId()) == null) {
            throw new InvalidInputDataException(ErrorMessages.UPDATING_DATA_ERROR_MESSAGE.label);
        }
        return position;
    }

    private Position checkValidData(@NonNull UUID id) {
        Optional<Position> positionOpt = positionRepository.findById(id);
        if (positionOpt.isEmpty()) {
            throw new EntityNotfoundException(String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, id));
        } else {
            return positionOpt.get();
        }
    }
}
