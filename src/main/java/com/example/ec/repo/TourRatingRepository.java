package com.example.ec.repo;

import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {

    /*Look up all tour rating by tour id*/
    List<TourRating> findByPkTourId(Integer tourId);

    /*Look up tour rating by tour id and customer id*/
    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);

}
