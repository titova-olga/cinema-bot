package com.java_school.bot.constants;

public interface RestUrls {
    String PREFIX = "http://INFORMATOR/api/";
    String FILMS = PREFIX + "/films";
    String CINEMAS = PREFIX + "/cinemas";
    String SESSIONS = PREFIX + "/sessions";
    String USER_CHOICE_FILM = PREFIX + "/userChoice/film";
    String USER_CHOICE_CINEMA = PREFIX + "/userChoice/cinema";
    String USER_CHOICE_CLEAR = PREFIX + "/userChoice";
    String USER_CHOICE_DATE = PREFIX + "/userChoice/date";
}
