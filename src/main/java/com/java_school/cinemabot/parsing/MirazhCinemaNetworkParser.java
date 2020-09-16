package com.java_school.cinemabot.parsing;

import com.java_school.cinemabot.parsing.dto.CinemaDTO;
import com.java_school.cinemabot.parsing.dto.FilmDTO;
import com.java_school.cinemabot.parsing.dto.SessionDTO;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MirazhCinemaNetworkParser implements CinemaParser {

    private static final String NETWORK_NAME = "Мираж Синема";
    private static final String BASE_URL = "https://www.mirage.ru";
    private static final String CINEMAS_SUFFIX = "/cinemas/cinemas.htm";
    private static final String FILMS_SUFFIX = "/now/film_now.htm";
    private static final String SESSIONS_SUFFIX = "/schedule/20200920/0/2_4_8_10_11_13_14/0/0/0/schedule.htm";


    public static void main(String[] args) {
        CinemaParser cinemaParser = new MirazhCinemaNetworkParser();
        for (SessionDTO ssesionDTO : cinemaParser.parseSessions()) {
            System.out.println(ssesionDTO);
        }
    }

    @SneakyThrows
    public List<FilmDTO> parseFilms() {
        String url = BASE_URL + FILMS_SUFFIX;
        Document doc = Jsoup.connect(url).get();
        return doc.getElementsByClass("allfilms").first()
                .getElementsByClass("item-film")
                .stream()
                .map(this::parseOneFilmInformation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private FilmDTO parseOneFilmInformation(Element element) {
        try {
            Elements tagA = element.getElementsByClass("title").select("h2 > a[href]");
            String filmRef = tagA.attr("href");     // reference on page with detailed film description
            String filmName = tagA.text();                    // film name

            String url = BASE_URL + filmRef;
            Document doc = Jsoup.connect(url).get();    // go on page with detailed film description
            Element allFilmParameters = doc.getElementsByClass("one-film").first()
                    .getElementsByClass("col3").first();

            String minAge = allFilmParameters.getElementsMatchingText("Возрастное ограничение")
                    .last().nextSibling().toString().replaceAll("[^0-9]+", "");
            String producer = allFilmParameters.getElementsMatchingText("Режиссер")
                    .last().nextSibling().toString();
            String releaseDate = allFilmParameters.getElementsMatchingText("Прокат")
                    .last().nextSibling().toString().replaceAll("[^0-9.]+", "");
            String genre = allFilmParameters.getElementsMatchingText("Жанр")
                    .last().nextSibling().toString();
            String country = allFilmParameters.getElementsMatchingText("Страна")
                    .last().nextSibling().toString();

            String filmDescription = doc.getElementsByClass("film-article").first()
                    .select("p").text();

            return FilmDTO.builder()
                    .name(filmName)
                    .genre(genre)
                    .producer(producer)
                    .releaseDate(LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                    .minAge(Integer.parseInt(minAge))
                    .description(filmDescription)
                    .country(country)
                    .build();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @SneakyThrows
    public List<CinemaDTO> parseCinemas() {
        String url = BASE_URL + CINEMAS_SUFFIX;
        Document doc = Jsoup.connect(url).get();
        return doc.getElementsByClass("alltheaters").first()
                .getElementsByTag("li")
                .stream()
                .map(this::parseOneCinemaInformation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private CinemaDTO parseOneCinemaInformation(Element element) {
        try {
            Elements tagA = element.select("a[href]");
            String cinemaRef = tagA.attr("href");
            String cinemaName = tagA.text();

            String cinemaUrl = BASE_URL + cinemaRef;
            Document doc = Jsoup.connect(cinemaUrl).get();
            String cinemaAddress = doc.getElementsByClass("theater").first()
                    .select("h4")
                    .text();

            return CinemaDTO.builder()
                    .name(cinemaName)
                    .networkName(NETWORK_NAME)
                    .address(cinemaAddress)
                    .build();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @SneakyThrows
    public List<SessionDTO> parseSessions() {
        String url = BASE_URL + SESSIONS_SUFFIX;
        Document doc = Jsoup.connect(url).get();
        return doc.getElementById("innerTable")
                .select("tbody > tr") //.getElementsByTag("tbody").first().getElementsByTag("tr")
                .stream()
                .map(this::parseOneSessionInformation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SessionDTO parseOneSessionInformation(Element element) {

        try {
            String filmName = element.getElementsByClass("col2").select("a").text();
            String time = element.getElementsByClass("col1").select("b").first().text();
            String price = element.getElementsByClass("col6").select("span[title]").first().text();
            String buyRef = element.getElementsByClass("col7").select("div[onclick]").attr("onclick");
            if (!buyRef.equals("")) {
                buyRef = BASE_URL + buyRef.split("=")[1].replaceAll("\"", "");
            }

            String cinemaNameWithRoom = element.getElementsByClass("col5").select("a").text();
            String cinemaName = cinemaNameWithRoom.substring(0, cinemaNameWithRoom.lastIndexOf("»") + 1);

            SessionDTO sessionDTO = SessionDTO.builder()
                    .filmName(filmName)
                    .cinemaName(cinemaName)
                    .price(Integer.parseInt(price))
                    .buyReference(buyRef)
                    .time(LocalTime.parse(time))
                    .build();

            return sessionDTO;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
