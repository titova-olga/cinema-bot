package com.java_school.scheduler.services.database.impl;

import com.java_school.scheduler.model.CinemaNetwork;
import com.java_school.scheduler.repo.CinemaNetworkRepo;
import com.java_school.scheduler.services.database.DatabaseCinemaNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DatabaseCinemaNetworkServiceImpl implements DatabaseCinemaNetworkService {

    @Autowired
    private CinemaNetworkRepo cinemaNetworkRepo;

    @Override
    public void saveNetworks(List<CinemaNetwork> networks) {
        networks.stream()
                .filter(network -> cinemaNetworkRepo.findCinemaNetworkByName(network.getName()) == null)
                .forEach(network -> cinemaNetworkRepo.save(network));
    }
}
