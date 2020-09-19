package com.java_school.informator.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DateUserChoiceDTO extends UserChoiceDTO{
    private LocalDate date;

    @Override
    public String toString() {
        return "DateUserChoiceDTO{" +
                "chatId=" + super.getChatId() +
                ",date=" + date +
                '}';
    }
}
