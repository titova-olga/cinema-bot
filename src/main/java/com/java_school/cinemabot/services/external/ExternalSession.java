package com.java_school.cinemabot.services.external;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ExternalSession {
    private String filmName;
    private String cinemaName;
    private LocalDate date;
    private LocalTime time;
}
