package com.java_school.cinemabot.parsing;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.external.model.CinemaDTO;
import com.java_school.cinemabot.services.external.model.FilmDTO;
import com.java_school.cinemabot.services.external.model.SessionDTO;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MirazhCinemaNetworkParser implements Parser {

    private static final String NETWORK_NAME = "Мираж Синема";

    public static void main(String[] args) {
        Parser parser = new MirazhCinemaNetworkParser();
        for (SessionDTO session : parser.parseSessions("https://www.mirage.ru/schedule/20200915/0/2_4_8_10_11_13_14/0/0/0/schedule.htm")) {
            System.out.println(session);
        }

    }

    @SneakyThrows
    public List<FilmDTO> parseFilms(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.getElementsByClass("item-group").first()
                .getElementsByClass("item-film")
                .stream()
                .map(this::createFilm)
                .collect(Collectors.toList());
    }

    private FilmDTO createFilm(Element element) {
        return FilmDTO.builder()
                .name(element.getElementsByClass("title").select("h2 > a").text())
                .build();
    }

    @SneakyThrows
    public List<CinemaDTO> parseCinemas(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.getElementsByTag("h1").first()
                .nextElementSibling()
                .getElementsByTag("li")
                .stream()
                .map(this::createCinema)
                .collect(Collectors.toList());
    }

    private CinemaDTO createCinema(Element element) {
        return CinemaDTO.builder()
                .name(element.select("a").text())
                .networkName(NETWORK_NAME)
                .build();
    }

    @SneakyThrows
    public List<SessionDTO> parseSessions(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.getElementById("innerTable")
                .select("tbody > tr") //.getElementsByTag("tbody").first().getElementsByTag("tr")
                .stream()
                .map(this::createSession)
                .collect(Collectors.toList());
    }

    private SessionDTO createSession(Element element) {

        String cinemaNameWithRoom = element.getElementsByClass("col5").select("a").text();
        String cinemaName = cinemaNameWithRoom.substring(0, cinemaNameWithRoom.lastIndexOf("»") + 1);

        return SessionDTO.builder()
                .filmName(element.getElementsByClass("col2").select("a").text())
                .cinemaName(cinemaName)
                .time(LocalTime.parse(element.getElementsByClass("col1").first().select("b").first().text()))
                .build();
    }
}
