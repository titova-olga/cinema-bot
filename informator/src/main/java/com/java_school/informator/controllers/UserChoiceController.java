package com.java_school.informator.controllers;

import com.java_school.informator.dto.CinemaUserChoiceDTO;
import com.java_school.informator.dto.ClearUserChoiceDTO;
import com.java_school.informator.dto.DateUserChoiceDTO;
import com.java_school.informator.dto.FilmUserChoiceDTO;
import com.java_school.informator.users_choices_cache.UserChoice;
import com.java_school.informator.users_choices_cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/userChoice")
public class UserChoiceController {

    @Autowired
    private UsersChoicesCache usersChoicesCache;

    @PostMapping("/film")
    public void saveUserFilmChoice(@RequestBody FilmUserChoiceDTO filmUserChoiceDTO){
        usersChoicesCache.addFilmChoice(filmUserChoiceDTO.getChatId(), filmUserChoiceDTO.getFilmId());
    }

    @PostMapping("/date")
    public void saveUserDataChoice(@RequestBody DateUserChoiceDTO dateUserChoiceDTO){
        usersChoicesCache.addDateChoice(dateUserChoiceDTO.getChatId(), dateUserChoiceDTO.getDate());
    }

    @PostMapping("/cinema")
    public void saveUserCinemaChoice(@RequestBody CinemaUserChoiceDTO cinemaUserChoiceDTO){
        usersChoicesCache.addCinemaChoice(cinemaUserChoiceDTO.getChatId(), cinemaUserChoiceDTO.getCinemaId());
    }

    @PostMapping // todo delete mapping
    public void deleteUserChoice(@RequestBody ClearUserChoiceDTO clearUserChoiceDTO) {
        usersChoicesCache.removeInfoAboutUserChoices(clearUserChoiceDTO.getChatId());
    }
}
