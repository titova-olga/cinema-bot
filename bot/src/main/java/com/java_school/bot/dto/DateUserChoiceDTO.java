package com.java_school.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DateUserChoiceDTO {
    long chatId;
    LocalDate date;
}
