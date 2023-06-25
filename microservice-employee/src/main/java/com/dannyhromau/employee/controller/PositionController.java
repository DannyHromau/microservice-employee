package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position")
@Tag(name = "Position service", description = "Position controller")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
public class PositionController {

    private final PositionService positionService;

    @GetMapping(value = "/all")
    @Operation(description = "Getting list of positions")
    ResponseEntity<List<PositionDto>> getPositions() {
        log.info("call getPositions");
        return ResponseEntity.ok(positionService.getPositions());
    }

    @GetMapping("{id}")
    @Operation(description = "Getting position by id")
    ResponseEntity<PositionDto> getPositionById(@PathVariable UUID id) {
        log.info("call getPositionById with id: {}, id", id);
        return ResponseEntity.ok(positionService.getPositionById(id));
    }

    @PostMapping("/create")
    @Operation(description = "Creating position")
    ResponseEntity<PositionDto> addPosition(@RequestBody PositionDto positionDto) {
        log.info("call addPosition: {}, position", positionDto);
        return ResponseEntity.ok(positionService.addPosition(positionDto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Deleting position")
    ResponseEntity<UUID> deletePosition(@PathVariable UUID id) {
        log.info("call deletePosition with id: {}, id", id);
        return ResponseEntity.ok(positionService.deleteById(id));
    }

    @PutMapping("/update")
    @Operation(description = "Updating position")
    ResponseEntity<PositionDto> updatePosition(@RequestBody PositionDto positionDto) {
        log.info("call updatePosition: {}, position", positionDto);
        return ResponseEntity.ok(positionService.updatePosition(positionDto));
    }
}
