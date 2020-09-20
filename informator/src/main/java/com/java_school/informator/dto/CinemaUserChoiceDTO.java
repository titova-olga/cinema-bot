package com.java_school.informator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
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
