package com.java_school.informator.controllers;

import com.java_school.informator.dto.CinemaUserChoiceDTO;
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
        UserChoice userChoice = usersChoicesCache.getOrCreateUserChoice(filmUserChoiceDTO.getChatId());
        userChoice.addFilmChoice(filmUserChoiceDTO.getFilmId());
    }

    @PostMapping("/date")
    public void saveUserDataChoice(@RequestBody DateUserChoiceDTO dateUserChoiceDTO){
        UserChoice userChoice = usersChoicesCache.getOrCreateUserChoice(dateUserChoiceDTO.getChatId());
        userChoice.addDateChoice(dateUserChoiceDTO.getDate());
    }

    @PostMapping("/cinema")
    public void saveUserCinemaChoice(@RequestBody CinemaUserChoiceDTO cinemaUserChoiceDTO){
        UserChoice userChoice = usersChoicesCache.getOrCreateUserChoice(cinemaUserChoiceDTO.getChatId());
        userChoice.addCinemaChoice(cinemaUserChoiceDTO.getCinemaId());
    }

    @DeleteMapping
    public void deleteUserChoice(@RequestParam long chatId) {
        usersChoicesCache.removeInfoAboutUserChoices(chatId);
    }
}
