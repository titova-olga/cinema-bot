package com.java_school.cinemabot.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
//@Table(name = "SCHEDULE")
@Data
public class Session {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    //@JoinColumn(name = "id")
    private Cinema cinema;

    @ManyToOne
    //@JoinColumn(name = "id")
    private Film film;

    private LocalDate date;
    private LocalTime time;
}
