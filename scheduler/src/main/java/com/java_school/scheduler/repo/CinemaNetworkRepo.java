package com.java_school.scheduler.repo;

import com.java_school.scheduler.model.CinemaNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaNetworkRepo extends JpaRepository<CinemaNetwork, Integer> {
    CinemaNetwork findCinemaNetworkByName(String name);
}

