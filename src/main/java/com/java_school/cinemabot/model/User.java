package com.java_school.cinemabot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private int id;

    private int telegramId;
    private String firstName;
    private String lastName;
}
