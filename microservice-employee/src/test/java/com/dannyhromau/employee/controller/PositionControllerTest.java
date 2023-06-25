package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.service.PositionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionControllerTest {
    @Mock
    PositionService positionService;
    @InjectMocks
    PositionController positionController;



    @Test
    void getPositions_ReturnsEmptyList(){
        List<PositionDto> emptyList = new ArrayList<>();
        when(this.positionService.getPositions()).thenReturn(emptyList);
        ResponseEntity<List<PositionDto>> responseEntity = this.positionController.getPositions();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(emptyList, responseEntity.getBody());
    }

    @Test
    void getPositions_ReturnsPositionDtosList(){
        List<PositionDto> positionList = List.of(new PositionDto());
        when(this.positionService.getPositions()).thenReturn(positionList);
        ResponseEntity<List<PositionDto>> responseEntity = this.positionController.getPositions();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(positionList, responseEntity.getBody());
    }
    @Test
    void getPosition_ReturnsPositionById_WhenItExists(){
        PositionDto positionDto = new PositionDto();
        UUID id = UUID.randomUUID();
        positionDto.setId(id);
        when(this.positionService.getPositionById(id))
                .thenReturn(positionDto);
        ResponseEntity<PositionDto> responseEntity = this.positionController.getPositionById(id);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(positionDto, responseEntity.getBody());
    }
    @Test
    void addPosition_ReturnsPosition_WhenPositionIsValid(){
        PositionDto positionDto = new PositionDto();
        UUID id = UUID.randomUUID();
        positionDto.setId(id);
        when(this.positionService.addPosition(positionDto))
                .thenReturn(positionDto);
        ResponseEntity<PositionDto> responseEntity = this.positionController.addPosition(positionDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(positionDto, responseEntity.getBody());
    }
    @Test
    void deletePosition_ReturnsId_WhenPositionIsValid(){
        PositionDto positionDto = new PositionDto();
        UUID id = UUID.randomUUID();
        positionDto.setId(id);
        when(this.positionService.deleteById(id))
                .thenReturn(id);
        ResponseEntity<UUID> responseEntity = this.positionController.deletePosition(id);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(id, responseEntity.getBody());
    }
    @Test
    void updatePosition_ReturnsPosition_WhenPositionIsValid(){
        PositionDto positionDto = new PositionDto();
        UUID id = UUID.randomUUID();
        positionDto.setId(id);
        when(this.positionService.updatePosition(positionDto))
                .thenReturn(positionDto);
        ResponseEntity<PositionDto> responseEntity = this.positionController.updatePosition(positionDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(positionDto, responseEntity.getBody());
    }


}