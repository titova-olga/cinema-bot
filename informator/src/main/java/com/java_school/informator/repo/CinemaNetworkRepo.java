package com.java_school.informator.repo;

import com.java_school.informator.model.CinemaNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaNetworkRepo extends JpaRepository<CinemaNetwork, Integer> {
    CinemaNetwork findCinemaNetworkByName(String name);
}

