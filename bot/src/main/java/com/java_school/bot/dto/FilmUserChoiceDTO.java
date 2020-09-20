package com.java_school.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FilmUserChoiceDTO extends UserChoiceDTO {
    private int filmId;

    public FilmUserChoiceDTO(long chatId, int filmId) {
        super(chatId);
        this.filmId = filmId;
    }
}
