package com.example.restaurantrating.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restaurantrating.domain.GenderType;
import com.example.restaurantrating.domain.entity.Visitor;
import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.mapper.VisitorMapper;
import com.example.restaurantrating.repository.VisitorRepository;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void create_success() {
        VisitorRequest request =
                new VisitorRequest("Alexandr", 25, GenderType.МУЖЧИНА);

        Visitor entity = new Visitor();
        Visitor saved = new Visitor();

        VisitorResponse response =
                new VisitorResponse(1L, "Alexandr", 25, GenderType.МУЖЧИНА);

        when(visitorMapper.toEntity(request)).thenReturn(entity);
        when(visitorRepository.save(entity)).thenReturn(saved);
        when(visitorMapper.toDto(saved)).thenReturn(response);

        VisitorResponse result = visitorService.create(request);

        assertEquals("Alexandr", result.name());
        verify(visitorRepository).save(entity);
    }

    @Test
    void findById_success() {
        Visitor visitor = new Visitor();
        VisitorResponse response =
                new VisitorResponse(1L, "Alexandr", 25, GenderType.МУЖЧИНА);

        when(visitorRepository.findById(1L))
                .thenReturn(Optional.of(visitor));
        when(visitorMapper.toDto(visitor))
                .thenReturn(response);

        VisitorResponse result = visitorService.findById(1L);

        assertEquals(1L, result.id());
    }

    @Test
    void findById_notFound() {
        when(visitorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> visitorService.findById(1L));
    }

     @Test
    void update_success() {
        VisitorRequest request =
                new VisitorRequest("Angelina", 30, GenderType.ЖЕНЩИНА);

        Visitor existing = new Visitor();
        Visitor updated = new Visitor();

        when(visitorRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(visitorRepository.save(existing))
                .thenReturn(updated);
        when(visitorMapper.toDto(updated))
                .thenReturn(new VisitorResponse(
                        1L, "Angelina", 30, GenderType.ЖЕНЩИНА));

        VisitorResponse result = visitorService.update(1L, request);

        assertEquals("Angelina", result.name());
        verify(visitorRepository).save(existing);
    }

    @Test
    void update_notFound() {
        when(visitorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> visitorService.update(1L, mock(VisitorRequest.class)));
    }

    @Test
    void remove_success() {
        when(visitorRepository.existsById(1L))
                .thenReturn(true);

        boolean result = visitorService.remove(1L);

        assertTrue(result);
        verify(visitorRepository).deleteById(1L);
    }

    @Test
    void remove_notFound() {
        when(visitorRepository.existsById(1L))
                .thenReturn(false);

        boolean result = visitorService.remove(1L);

        assertFalse(result);
        verify(visitorRepository, never()).deleteById(anyLong());
    }
}