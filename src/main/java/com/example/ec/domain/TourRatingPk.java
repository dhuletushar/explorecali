package com.example.ec.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TourRatingPk implements Serializable {

    @ManyToOne
    private Tour tour;

    @Column(nullable = false, insertable = false, updatable = false)
    private Integer customerId;

    public TourRatingPk(Tour tour, Integer customerId) {
        this.tour = tour;
        this.customerId = customerId;
    }

    public TourRatingPk() {
    }

    public Tour getTour() {
        return tour;
    }

    public Integer getCustomerId() {
        return customerId;
    }
}
