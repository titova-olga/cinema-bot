package com.java_school.informator.services.database.impl;

import com.java_school.informator.model.CinemaNetwork;
import com.java_school.informator.repo.CinemaNetworkRepo;
import com.java_school.informator.services.database.DatabaseCinemaNetworkService;
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
    public List<CinemaNetwork> getAllNetworks() {
        return cinemaNetworkRepo.findAll();
    }

    @Override
    public CinemaNetwork getCinemaNetworkByName(String name) {
        return cinemaNetworkRepo.findCinemaNetworkByName(name);
    }
}
