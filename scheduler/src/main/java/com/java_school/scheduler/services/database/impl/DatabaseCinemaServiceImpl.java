package com.java_school.scheduler.services.database.impl;

import com.java_school.scheduler.model.Cinema;
import com.java_school.scheduler.model.CinemaNetwork;
import com.java_school.scheduler.parsing.dto.CinemaDTO;
import com.java_school.scheduler.repo.CinemaNetworkRepo;
import com.java_school.scheduler.repo.CinemaRepo;
import com.java_school.scheduler.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
