package com.example.restaurantrating.mapper;

import org.mapstruct.Mapper;

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.domain.entity.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant toEntity(RestaurantRequest dto);

    RestaurantResponse toDto(Restaurant restaurant);
}
