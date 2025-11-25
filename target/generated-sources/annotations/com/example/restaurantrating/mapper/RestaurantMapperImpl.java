package com.example.restaurantrating.mapper;

import com.example.restaurantrating.domain.KitchenType;
import com.example.restaurantrating.domain.entity.Restaurant;
import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-25T20:30:52+0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant toEntity(RestaurantRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName( dto.name() );
        restaurant.setDescription( dto.description() );
        restaurant.setCuisineType( dto.cuisineType() );
        restaurant.setAverageBill( dto.averageBill() );

        return restaurant;
    }

    @Override
    public RestaurantResponse toDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        KitchenType cuisineType = null;
        BigDecimal averageBill = null;
        BigDecimal userRating = null;

        id = restaurant.getId();
        name = restaurant.getName();
        description = restaurant.getDescription();
        cuisineType = restaurant.getCuisineType();
        averageBill = restaurant.getAverageBill();
        userRating = restaurant.getUserRating();

        RestaurantResponse restaurantResponse = new RestaurantResponse( id, name, description, cuisineType, averageBill, userRating );

        return restaurantResponse;
    }
}
