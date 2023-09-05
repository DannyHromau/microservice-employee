package com.dannyhromau.employee.controller.impl;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.controller.PositionController;
import com.dannyhromau.employee.facade.PositionFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PositionControllerImpl implements PositionController {

    private final PositionFacade positionFacade;

    @Override
    public ResponseEntity<List<PositionDto>> getAll(Pageable page) {
        log.info("call getPositions");
        return ResponseEntity.ok(positionFacade.getDtos(page));
    }

    @Override
    public ResponseEntity<PositionDto> getById(@PathVariable @NonNull UUID id) {
        log.info("call getPositionById with id: {}, id", id);
        return ResponseEntity.ok(positionFacade.getDtoByID(id));
    }

    @Override
    public ResponseEntity<PositionDto> create(@RequestBody @NonNull PositionDto positionDto) {
        log.info("call addPosition: {}, position", positionDto);
        return ResponseEntity.ok(positionFacade.addDto(positionDto));
    }

    @Override
    public ResponseEntity<PositionDto> update(@RequestBody @NonNull PositionDto positionDto) {
        log.info("call updatePosition: {}, position", positionDto);
        return ResponseEntity.ok(positionFacade.updateDto(positionDto));
    }

    @Override
    public ResponseEntity<UUID> deleteById(@PathVariable @NonNull UUID id) {
        log.info("call deletePosition with id: {}, id", id);
        return ResponseEntity.ok(positionFacade.deleteDtoById(id));
    }
}
