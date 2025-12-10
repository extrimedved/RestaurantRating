package com.example.restaurantrating.repository;

import com.example.restaurantrating.domain.entity.VisitorRating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRatingRepository extends JpaRepository<VisitorRating, Long> {
    List<VisitorRating> findByRestaurantId(Long restaurantId);
}
