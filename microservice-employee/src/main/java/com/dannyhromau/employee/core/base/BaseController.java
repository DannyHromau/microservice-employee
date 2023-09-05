package com.dannyhromau.employee.core.base;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface BaseController<Dto extends BaseDto> {

    @GetMapping(value = "/{id}")
    ResponseEntity<Dto> getById(@PathVariable UUID id);

    @GetMapping("/all")
    ResponseEntity<List<Dto>> getAll(Pageable page);

    @PostMapping("/create")
    ResponseEntity<Dto> create(@RequestBody Dto dto);

    @PutMapping("/update")
    ResponseEntity<Dto> update(@RequestBody Dto dto);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<UUID> deleteById(@PathVariable UUID id);
}
