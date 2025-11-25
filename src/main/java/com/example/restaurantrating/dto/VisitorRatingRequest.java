package com.example.restaurantrating.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Valid
public record VisitorRatingRequest(
    @NotNull(message = "ID посетителя обязателен")
    Long visitorId, 
    @NotNull(message = "ID ресторана обязателен")
    Long restaurantId, 
    @Max(value = 5, message = "Рейтинг максимум 5")
    @Min(value = 1, message = "Рейтинг минимум 1")
    int rating, 
    String reviewText
) {}
