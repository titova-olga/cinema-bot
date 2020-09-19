package com.java_school.informator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
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
