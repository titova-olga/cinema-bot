package com.java_school.cinemabot.services.database.impl;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.CinemaNetwork;
import com.java_school.cinemabot.repo.CinemaNetworkRepo;
import com.java_school.cinemabot.repo.CinemaRepo;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.parsing.dto.CinemaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatabaseCinemaServiceImpl implements DatabaseCinemaService {

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private CinemaNetworkRepo cinemaNetworkRepo;

    @Override
    @Transactional
    public void saveCinemas(List<CinemaDTO> cinemas) {
        for (CinemaDTO cinema : cinemas) {
            String networkName = cinema.getNetworkName();
            CinemaNetwork cinemaNetwork = cinemaNetworkRepo.findCinemaNetworkByName(networkName);
            if(cinemaNetwork == null) {
                cinemaNetwork = CinemaNetwork.builder().name(networkName).build();
            }
            cinemaRepo.save(Cinema.builder()
                    .name(cinema.getName())
                    .address(cinema.getAddress())
                    .cinemaNetwork(cinemaNetwork)
                    .build());
        }
    }

    @Override
    public Cinema getCinemaById(int cinemaId) {
        return cinemaRepo.findCinemaById(cinemaId);
    }

    @Override
    @Transactional
    public List<Cinema> getCinemasByNetwork(String network) {
        return cinemaRepo.findByCinemaNetworkName(network);
    }

    @Override
    @Transactional
    public List<Cinema> getAllCinemas() {
        return cinemaRepo.findAll();
    }
}
