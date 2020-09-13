package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.CinemaNetwork;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeExternalCinemaService implements ExternalCinemaService {
    @Override
    public List<ExternalCinema> getExistingCinemas() {
        return List.of(ExternalCinema.builder().name("Каро 11 Охта Молл").networkName("Каро").build(),
                ExternalCinema.builder().name("Каро 5 Невский-2").networkName("Каро").build(),
                ExternalCinema.builder().name("Каро 7 Атмосфера").networkName("Каро").build(),
                ExternalCinema.builder().name("Мираж Синема Атлантик Сити").networkName("Мираж Синема").build(),
                ExternalCinema.builder().name("Мираж Синема Гулливер").networkName("Мираж Синема").build());
    }
}
