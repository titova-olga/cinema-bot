package com.java_school.informator.services.database.impl;

import com.java_school.informator.model.Cinema;
import com.java_school.informator.repo.CinemaNetworkRepo;
import com.java_school.informator.repo.CinemaRepo;
import com.java_school.informator.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DatabaseCinemaServiceImpl implements DatabaseCinemaService {

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private CinemaNetworkRepo cinemaNetworkRepo;

    @Override
    public Cinema getCinemaById(int cinemaId) {
        return cinemaRepo.findById(cinemaId).get();
    }

    @Override
    public List<Cinema> getCinemasByNetwork(String network) {
        return cinemaRepo.findByCinemaNetworkName(network);
    }

    @Override
    @Cacheable(value = "allCinemasCache")
    public List<Cinema> getAllCinemas() {
        return cinemaRepo.findAll();
    }
}
