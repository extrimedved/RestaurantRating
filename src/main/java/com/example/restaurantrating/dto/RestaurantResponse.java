package com.example.restaurantrating.dto;
import java.math.BigDecimal;
import com.example.restaurantrating.domain.KitchenType;
public record RestaurantResponse(Long id, String name, String description, KitchenType cuisineType, BigDecimal averageBill, BigDecimal userRating) {}
