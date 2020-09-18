package com.java_school.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CinemaUserChoiceDTO {
    long chatId;
    int cinemaId;
}