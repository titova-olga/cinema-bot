package com.java_school.cinemabot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.List;

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
