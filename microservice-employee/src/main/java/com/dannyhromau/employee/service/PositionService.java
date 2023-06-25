package com.dannyhromau.employee.service;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.exception.DeletingEmployeeException;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidPositionDataException;
import com.dannyhromau.employee.mapper.PositionMapper;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    public List<PositionDto> getPositions() {
        return positionMapper.mapToListPositionDto(positionRepository.findAll());
    }

    public PositionDto getPositionById(UUID id) {
        Optional<Position> position = positionRepository.findById(id);
        checkExistingPosition(id, position);
        return positionMapper.mapToPositionDto(position.get());
    }

    public PositionDto addPosition(PositionDto positionDto) {
        Position position = positionMapper.mapToPosition(positionDto);
        positionRepository.save(position);
        position = positionRepository.findByPosition(positionDto.getPosition());
        if (position == null) {
            throw new InvalidPositionDataException("Unable to create database entry");
        }
        return positionMapper.mapToPositionDto(position);
    }

    public UUID deleteById(UUID id) {
        Optional<Position> position = positionRepository.findById(id);
        checkExistingPosition(id, position);
        positionRepository.deleteById(id);
        position = positionRepository.findById(id);
        if (position.isPresent()) {
            throw new DeletingEmployeeException("Deleting data is failed");
        }
        return id;
    }

    public PositionDto updatePosition(PositionDto positionDto) {
        if (positionDto.getId() == null){
            throw new InvalidPositionDataException("The given id must not be null");
        }
        Optional<Position> positionOpt = positionRepository.findById(positionDto.getId());
        checkExistingPosition(positionDto.getId(), positionOpt);
        Position position = positionMapper.mapToPosition(positionDto);
        ZonedDateTime updateTime = ZonedDateTime.now();
        position.setUpdateOn(updateTime);
        positionRepository.save(position);
        if (positionRepository.findByUpdateOn(updateTime) == null) {
            throw new InvalidPositionDataException("Unable to update database entry");
        }
        return positionDto;
    }

    private static void checkExistingPosition(UUID id, Optional<Position> employeeOpt) {
        if (employeeOpt.isEmpty()) {
            throw new EntityNotfoundException(String.format("Employee with id : %s not exists", id));
        }
    }
}
