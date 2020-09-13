package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.CinemaNetwork;
import com.java_school.cinemabot.repo.CinemaNetworkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatabaseCinemaNetworkServiceImpl implements DatabaseCinemaNetworkService {

    @Autowired
    private CinemaNetworkRepo cinemaNetworkRepo;

    @Override
    @Transactional
    public List<CinemaNetwork> getAllNetworks() {
        return cinemaNetworkRepo.findAll();
    }
}
