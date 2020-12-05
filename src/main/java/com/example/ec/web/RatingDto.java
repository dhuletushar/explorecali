package com.example.ec.web;

import com.example.ec.domain.TourRating;

public class RatingDto {

    private Integer score;

    private String comment;

    private Integer customerId;

    public RatingDto(TourRating tourRating) {
        this(
        tourRating.getScore(),
        tourRating.getComment(),
        tourRating.getPk().getCustomerId());
    }

    private RatingDto(Integer score, String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
