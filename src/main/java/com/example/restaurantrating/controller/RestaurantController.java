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

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Operation(summary = "Создаем новый ресторан")
    @PostMapping
    public RestaurantResponse create(@Valid @RequestBody RestaurantRequest dto) {
        return restaurantService.create(dto);
    }

    @Operation(summary = "Получаем ресторан по id")
    @GetMapping("/{id}")
    public RestaurantResponse get(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @Operation(summary = "Получаем все созданные рестораны")
    @GetMapping
    public List<RestaurantResponse> getAll() {
        return restaurantService.findAll();
    }

    @Operation(summary = "Обновляем ресторан по id")
    @PutMapping("/{id}")
    public RestaurantResponse update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest dto
    ) {
        return restaurantService.update(id, dto);
    }

    @Operation(summary = "Удаляем ресторан по id")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return restaurantService.remove(id);
    }
}
