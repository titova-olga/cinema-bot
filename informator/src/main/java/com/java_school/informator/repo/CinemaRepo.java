package com.java_school.informator.repo;

import com.java_school.informator.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CinemaRepo extends JpaRepository<Cinema, Integer> {
    List<Cinema> findByCinemaNetworkName(String cinemaNetwork);
    Cinema findCinemaByName(String name);
}
