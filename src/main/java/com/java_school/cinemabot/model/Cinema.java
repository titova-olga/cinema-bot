package com.java_school.cinemabot.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Cinema {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String address;

    @ManyToOne
    private CinemaNetwork cinemaNetwork;
}
