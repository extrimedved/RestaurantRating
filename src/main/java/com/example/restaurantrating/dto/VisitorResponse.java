package com.example.restaurantrating.dto;
import com.example.restaurantrating.domain.GenderType;

public record VisitorResponse(Long id, String name, Integer age, GenderType gender) {}
