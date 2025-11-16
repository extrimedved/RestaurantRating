package com.example.restaurantrating.domain.entity;

import com.example.restaurantrating.domain.GenderType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    private Long id;     
    private String name;  
    private Integer age; 
    private GenderType gender; 
}
