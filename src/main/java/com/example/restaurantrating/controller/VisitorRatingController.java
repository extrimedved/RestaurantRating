package com.example.restaurantrating.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurantrating.dto.VisitorRatingRequest;
import com.example.restaurantrating.dto.VisitorRatingResponse;
import com.example.restaurantrating.service.VisitorRatingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visitorRatings")
public class VisitorRatingController {
    private final VisitorRatingService visitorRatingService;

    @Operation(summary = "Создаем новую оценку")
    @PostMapping
    public VisitorRatingResponse create(@Valid @RequestBody VisitorRatingRequest dto) {
        return visitorRatingService.create(dto);
    }

    @Operation(summary = "Получаем оценку по id")
    @GetMapping("/{id}")
    public VisitorRatingResponse get(@PathVariable Long id) {
        return visitorRatingService.findById(id);
    }

    @Operation(summary = "Получаем все созданные оценки")
    @GetMapping
    public List<VisitorRatingResponse> getAll() {
        return visitorRatingService.findAll();
    }

    @Operation(summary = "Обновляем оценку по id")
    @PutMapping("/{id}")
    public VisitorRatingResponse update(@PathVariable Long id, @Valid @RequestBody VisitorRatingRequest dto) {
        return visitorRatingService.update(id, dto);
    }

    @Operation(summary = "Удаляем оценку по id")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return visitorRatingService.remove(id);
    }
}
