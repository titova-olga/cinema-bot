package com.java_school.scheduler.repo;

import com.java_school.scheduler.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepo extends JpaRepository<Cinema, Integer> {
    Cinema findCinemaByName(String name);
}
