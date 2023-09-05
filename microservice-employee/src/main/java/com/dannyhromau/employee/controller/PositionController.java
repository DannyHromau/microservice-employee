package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.core.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/position")
@Tag(name = "Position service", description = "Position controller")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
public interface PositionController extends BaseController<PositionDto> {

    @Override
    @GetMapping(value = "/{id}")
    @Operation(description = "Getting position by id")
    ResponseEntity<PositionDto> getById(@PathVariable @NonNull UUID id);

    @Override
    @Operation(description = "Getting list of employees")
    ResponseEntity<List<PositionDto>> getAll(Pageable page);

    @Override
    @PostMapping(value = "create")
    @Operation(description = "Creating position")
    ResponseEntity<PositionDto> create(@RequestBody @NonNull PositionDto dto);

    @Override
    @PutMapping(value = "update")
    @Operation(description = "Updating position")
    ResponseEntity<PositionDto> update(@RequestBody @NonNull PositionDto dto);

    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleting employee")
    ResponseEntity<UUID> deleteById(@PathVariable @NonNull UUID id);
}
