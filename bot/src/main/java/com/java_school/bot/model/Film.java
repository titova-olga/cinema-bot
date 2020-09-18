package com.java_school.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String genre;
    private int minAge;
    private String description;
    private double rating;
    private String producer;
    private LocalDate releaseDate;
    private String country;
}
