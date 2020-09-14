package com.java_school.cinemabot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
//@Table(name = "SCHEDULE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Cinema cinema;

    @ManyToOne
    private Film film;

    //@Transient
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date; // Date
    private LocalTime time;

    @PrePersist // preload listener - back from database
    protected void calculateLocalDate() {
        // calculate date for database
    }
}
