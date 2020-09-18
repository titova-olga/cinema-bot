package com.java_school.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private int id;
    private Cinema cinema;
    private Film film;

    private LocalDate date; // Date
    private LocalTime time;

    private int price;
    private String buyReference;
}
