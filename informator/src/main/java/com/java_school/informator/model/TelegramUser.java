package com.java_school.informator.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class TelegramUser {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private int telegramId;
    private String firstName;
    private String lastName;
}
