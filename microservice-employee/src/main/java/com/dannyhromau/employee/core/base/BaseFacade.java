package com.dannyhromau.employee.core.base;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseFacade<Dto extends BaseDto> {

    List<Dto> getDtos(Pageable pageable);

    Dto getDtoByID(UUID id);

    Dto addDto(Dto dto);

    UUID deleteDtoById(UUID id);

    Dto updateDto(Dto dto);
}
