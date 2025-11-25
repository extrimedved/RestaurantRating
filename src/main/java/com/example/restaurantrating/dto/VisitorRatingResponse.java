package com.example.restaurantrating.dto;

public record VisitorRatingResponse(Long visitorId, Long restaurantId, int rating, String reviewText) {}
