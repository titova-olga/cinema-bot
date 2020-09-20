package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import com.java_school.bot.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AllFilmsMessageHandlerTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AllFilmsMessageHandler allFilmsMessageHandler;

    @Test
    void generateSorryMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.FILMS, Film[].class))
                .thenReturn(null);
        SendMessage message = allFilmsMessageHandler.generateMessage(null);
        assertTrue(message.getText().startsWith("Извини"));
    }

    @Test
    void generateNullMessage() {
        Film[] list = {};
        Mockito.when(restTemplate.getForObject(RestUrls.FILMS, Film[].class))
                .thenReturn(list);
        SendMessage message = allFilmsMessageHandler.generateMessage(null);
        assertEquals(message.getText(), "");
    }

    @Test
    void generateSimpleMessage() {
        Film[] list = {new Film(0, "Film", "", 0, "",
                0, "", null, "")};
        Mockito.when(restTemplate.getForObject(RestUrls.FILMS, Film[].class))
                .thenReturn(list);
        SendMessage message = allFilmsMessageHandler.generateMessage(null);
        assertEquals(message.getText(), "1. Film /film_0");
    }
}