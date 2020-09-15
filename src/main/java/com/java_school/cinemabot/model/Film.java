package com.java_school.cinemabot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition="TEXT")
    private String genre;
    private int minAge;

    @Column(columnDefinition="TEXT")
    private String description;
    private double rating;
    private String producer;
    private LocalDate releaseDate;
    private String country;
}
