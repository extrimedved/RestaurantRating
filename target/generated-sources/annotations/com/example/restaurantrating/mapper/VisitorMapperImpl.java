package com.example.restaurantrating.mapper;

import com.example.restaurantrating.domain.GenderType;
import com.example.restaurantrating.domain.entity.Visitor;
import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T14:39:30+0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class VisitorMapperImpl implements VisitorMapper {

    @Override
    public Visitor toEntity(VisitorRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Visitor visitor = new Visitor();

        visitor.setName( dto.name() );
        visitor.setAge( dto.age() );
        visitor.setGender( dto.gender() );

        return visitor;
    }

    @Override
    public VisitorResponse toDto(Visitor visitor) {
        if ( visitor == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Integer age = null;
        GenderType gender = null;

        id = visitor.getId();
        name = visitor.getName();
        age = visitor.getAge();
        gender = visitor.getGender();

        VisitorResponse visitorResponse = new VisitorResponse( id, name, age, gender );

        return visitorResponse;
    }
}