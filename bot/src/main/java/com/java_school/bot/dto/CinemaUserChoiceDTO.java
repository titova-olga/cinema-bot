package com.java_school.bot.dto;

import lombok.Getter;

@Getter
public class CinemaUserChoiceDTO extends UserChoiceDTO {
    private int cinemaId;

    public CinemaUserChoiceDTO(long chatId, int cinemaId) {
        super(chatId);
        this.cinemaId = cinemaId;
    }
}