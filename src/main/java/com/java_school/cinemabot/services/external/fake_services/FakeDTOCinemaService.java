package com.java_school.cinemabot.services.external.fake_services;

import com.java_school.cinemabot.services.external.DTOCinemaService;
import com.java_school.cinemabot.services.external.model.CinemaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeDTOCinemaService implements DTOCinemaService {
    @Override
    public List<CinemaDTO> getExistingCinemas() {
        return List.of(CinemaDTO.builder().name("Каро 11 Охта Молл").networkName("Каро").build(),
                CinemaDTO.builder().name("Каро 5 Невский-2").networkName("Каро").build(),
                CinemaDTO.builder().name("Каро 7 Атмосфера").networkName("Каро").build(),
                CinemaDTO.builder().name("Мираж Синема Атлантик Сити").networkName("Мираж Синема").build(),
                CinemaDTO.builder().name("Мираж Синема Гулливер").networkName("Мираж Синема").build());
    }
}
