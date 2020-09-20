package com.java_school.informator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FilmUserChoiceDTO extends UserChoiceDTO{
    private int filmId;

    @Override
    public String toString() {
        return "FilmUserChoiceDTO{" +
                "chatId=" + super.getChatId() +
                ",filmId=" + filmId +
                '}';
    }
}
