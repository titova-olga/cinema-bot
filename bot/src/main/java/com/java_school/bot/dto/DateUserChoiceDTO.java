package com.java_school.bot.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateUserChoiceDTO extends UserChoiceDTO{
    private LocalDate date;

    public DateUserChoiceDTO(long chatId, LocalDate date) {
        super(chatId);
        this.date = date;
    }
}
