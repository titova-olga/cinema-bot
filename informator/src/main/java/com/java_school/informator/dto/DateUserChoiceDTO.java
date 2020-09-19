package com.java_school.informator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateUserChoiceDTO extends UserChoiceDTO{
    private LocalDate date;
}
