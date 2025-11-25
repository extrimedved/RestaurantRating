package com.example.restaurantrating.mapper;

import org.mapstruct.Mapper;

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.domain.entity.*;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    Visitor toEntity(VisitorRequest dto);

    VisitorResponse toDto(Visitor visitor);
}