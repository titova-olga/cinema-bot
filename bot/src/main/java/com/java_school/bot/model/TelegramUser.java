package com.java_school.bot.model;

import lombok.Data;

@Data
public class TelegramUser {
    private int id;

    private int telegramId;
    private String firstName;
    private String lastName;
}
