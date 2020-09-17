package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.telegram.handler.message.CinemaMessageHandler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepo extends JpaRepository<Cinema, Integer> {
    List<Cinema> findByCinemaNetworkName(String cinemaNetwork);
    Cinema findCinemaById(int cinemaId);
    Cinema findCinemaByName(String name);

}
