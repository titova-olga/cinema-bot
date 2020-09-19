package com.java_school.informator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class CinemaUserChoiceDTO extends UserChoiceDTO {
    private int cinemaId;

    @Override
    public String toString() {
        return "CinemaUserChoiceDTO{" +
                "chatId=" + super.getChatId() +
                ",cinemaId=" + cinemaId +
                '}';
    }
}