package com.java_school.scheduler.parsing.dto;

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
