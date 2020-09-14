package com.java_school.cinemabot.services.external.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CinemaDTO {
    private String name;
    private String address;
    private String networkName;
}
