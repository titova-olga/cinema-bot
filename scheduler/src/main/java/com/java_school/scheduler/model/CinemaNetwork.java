package com.java_school.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaNetwork {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "cinemaNetwork")
    //private List<Cinema> cinemas;
}
