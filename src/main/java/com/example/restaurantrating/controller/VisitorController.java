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

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.service.VisitorService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visitor")
public class VisitorController {
    private final VisitorService visitorService;

    @Operation(summary = "Создаем нового посетителя")
    @PostMapping
    public VisitorResponse create(@Valid @RequestBody VisitorRequest dto) {
        return visitorService.create(dto);
    }

    @Operation(summary = "Получаем посетителя по id")
    @GetMapping("/{id}")
    public VisitorResponse get(@PathVariable Long id) {
        return visitorService.findById(id);
    }

    @Operation(summary = "Получаем всех созданных посетителей")
    @GetMapping
    public List<VisitorResponse> getAll() {
        return visitorService.findAll();
    }

    @Operation(summary = "обновляем посетителя по id")
    @PutMapping("/{id}")
    public VisitorResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VisitorRequest dto
    ) {
        return visitorService.update(id, dto);
    }

    @Operation(summary = "Удаляем посетителя по id")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return visitorService.remove(id);
    }
}
