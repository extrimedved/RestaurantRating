package com.example.restaurantrating.dto;

import com.example.restaurantrating.domain.KitchenType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import jakarta.validation.Valid;

@Valid
public record RestaurantRequest(
    @NotBlank(message = "Название обязательно")
    String name, 
    String description, 
    @NotNull(message = "Тип кухни обязателен")
    KitchenType cuisineType, 
    @NotNull(message = "Средний чек обязателен") 
    @DecimalMin(value = "0.01", message = "Средний чек > 0")
    BigDecimal averageBill
) {}
