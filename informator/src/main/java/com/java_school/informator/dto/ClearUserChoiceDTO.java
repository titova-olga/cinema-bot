package com.java_school.informator.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ClearUserChoiceDTO extends UserChoiceDTO {
    @Override
    public String toString() {
        return "ClearUserChoiceDTO{" +
                "chatId=" + super.getChatId() +
                "}";
    }
}
