package com.example.restaurantrating.dto;
import com.example.restaurantrating.domain.GenderType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Valid
public record VisitorRequest( 
    String name, 
    @NotNull(message = "Возраст обязателен")
    Integer age, 
    @NotNull(message = "Пол обязателен")
    GenderType gender
) {}


