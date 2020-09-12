package com.java_school.cinemabot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class CinemaNetwork {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "cinemaNetwork")
    private List<Cinema> cinemas;
}
