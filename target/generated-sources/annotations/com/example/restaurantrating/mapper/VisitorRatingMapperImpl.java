package com.example.restaurantrating.mapper;

import com.example.restaurantrating.domain.entity.VisitorRating;
import com.example.restaurantrating.dto.VisitorRatingRequest;
import com.example.restaurantrating.dto.VisitorRatingResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T14:44:05+0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class VisitorRatingMapperImpl implements VisitorRatingMapper {

    @Override
    public VisitorRating toEntity(VisitorRatingRequest dto) {
        if ( dto == null ) {
            return null;
        }

        VisitorRating visitorRating = new VisitorRating();

        visitorRating.setRating( dto.rating() );
        visitorRating.setReviewText( dto.reviewText() );

        return visitorRating;
    }

    @Override
    public VisitorRatingResponse toDto(VisitorRating visitor) {
        if ( visitor == null ) {
            return null;
        }

        int rating = 0;
        String reviewText = null;

        rating = visitor.getRating();
        reviewText = visitor.getReviewText();

        Long visitorId = null;
        Long restaurantId = null;

        VisitorRatingResponse visitorRatingResponse = new VisitorRatingResponse( visitorId, restaurantId, rating, reviewText );

        return visitorRatingResponse;
    }
}
