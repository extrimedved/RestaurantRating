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
    @GetMapping("/{visitorId}/{restaurantId}")
    public VisitorRatingResponse get(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        return visitorRatingService.findById(visitorId, restaurantId);
    }

    @Operation(summary = "Получаем все созданные оценки")
    @GetMapping
    public List<VisitorRatingResponse> getAll() {
        return visitorRatingService.findAll();
    }

    @Operation(summary = "обновляем оценки по id")
    @PutMapping("/{visitorId}/{restaurantId}")
    public VisitorRatingResponse update(
            @PathVariable Long visitorId,
            @PathVariable Long restaurantId,
            @Valid @RequestBody VisitorRatingRequest dto
    ) {
        return visitorRatingService.update(visitorId, restaurantId, dto);
    }

    @Operation(summary = "Удаляем оценки по id")
    @DeleteMapping("/{visitorId}/{restaurantId}")
    public boolean delete(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        return visitorRatingService.remove(visitorId, restaurantId);
    }
}
