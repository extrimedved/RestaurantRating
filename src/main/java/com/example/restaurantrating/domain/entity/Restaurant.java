package com.example.restaurantrating.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.example.restaurantrating.domain.KitchenType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private Long id;                
    private String name;             
    private String description;      
    private KitchenType cuisineType; 
    private BigDecimal averageBill; 
    private BigDecimal UserRating; 
}