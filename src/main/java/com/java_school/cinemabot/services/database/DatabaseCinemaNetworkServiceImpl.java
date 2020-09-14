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
    public void saveNetworks(List<CinemaNetwork> networks) {
        //todo: more complicated logic
        networks.stream()
                .filter(network -> cinemaNetworkRepo.getCinemaNetworkByName(network.getName()) == null)
                .forEach(network -> cinemaNetworkRepo.save(network));
    }

    @Override
    @Transactional
    public List<CinemaNetwork> getAllNetworks() {
        return cinemaNetworkRepo.findAll();
    }
}
