package com.java_school.cinemabot.services.external.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class SessionDTO {
    private String filmName;
    private String cinemaName;
    private LocalDate date;
    private LocalTime time;
    private int price;
    private String buyReference;
}
