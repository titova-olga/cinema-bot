package com.java_school.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    private LocalDate date; // Date
    private LocalTime time;

    private int price;
    private String buyReference;
}
