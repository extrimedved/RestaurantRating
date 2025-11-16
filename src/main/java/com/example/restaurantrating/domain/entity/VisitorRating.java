package com.example.restaurantrating.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorRating {

    private Long visitorId;   
    private Long restaurantId; 
    private int rating;      
    private String reviewText;       
}

