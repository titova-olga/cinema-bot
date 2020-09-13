package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.CinemaNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaNetworkRepo extends JpaRepository<CinemaNetwork, Integer> {
    CinemaNetwork getCinemaNetworkByName(String name);
}

