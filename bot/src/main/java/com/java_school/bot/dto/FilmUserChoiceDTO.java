package com.java_school.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmUserChoiceDTO {
    long chatId;
    int filmId;
}
