package com.java_school.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {
    private int id;
    private String name;
    private String address;
    private CinemaNetwork cinemaNetwork;
}
