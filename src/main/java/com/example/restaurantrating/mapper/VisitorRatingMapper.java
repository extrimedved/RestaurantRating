package com.example.restaurantrating.mapper;

import org.mapstruct.Mapper;

import com.example.restaurantrating.dto.VisitorRatingRequest;
import com.example.restaurantrating.dto.VisitorRatingResponse;
import com.example.restaurantrating.domain.entity.*;

@Mapper(componentModel = "spring")
public interface VisitorRatingMapper {

    VisitorRating toEntity(VisitorRatingRequest dto);

    VisitorRatingResponse toDto(VisitorRating visitor);
}
