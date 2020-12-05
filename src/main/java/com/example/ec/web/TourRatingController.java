package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import com.example.ec.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourRatingRepository tourRatingRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRating(@PathVariable(value = "tourId") Integer tourId, @RequestBody RatingDto rating) {
        Tour tour;
        tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, rating.getCustomerId()),
                rating.getScore(), rating.getComment()));
    }


    public List<RatingDto> lookupRatingByTourId(@PathVariable Integer tourId){
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDto::new).collect(Collectors.toList());
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable Integer tourId, @RequestBody RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable Integer tourId, @RequestBody RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if(ratingDto.getScore() != null) {
            rating.setScore(ratingDto.getScore());
        }
        if(ratingDto.getComment() != null) {
            rating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @GetMapping("/average")
    public Map<String, Double> getAvgRating(@PathVariable Integer tourId) {
        verifyTour(tourId);
        return Map.of("Average", tourRatingRepository.findByPkTourId(tourId).stream().mapToInt(TourRating::getScore).average().orElseThrow(
                () -> new NoSuchElementException("Tour has no rating")
        ));
    }

    @DeleteMapping(path = "/{customerId}")
    public void deleteRating(@PathVariable Integer tourId, @PathVariable Integer customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(Integer tourId, Integer customerId){
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId,customerId).orElseThrow(
                () -> new NoSuchElementException("No Rating available for Tour Id : "+tourId + " & Customer id : "+customerId+" combination")
        );
    }

    private Tour verifyTour(Integer tourId) {
       return tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("No such tour available"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String error400(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
