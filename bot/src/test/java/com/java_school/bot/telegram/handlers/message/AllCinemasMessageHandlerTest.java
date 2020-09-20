package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AllCinemasMessageHandlerTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AllCinemasMessageHandler allCinemasMessageHandler;

    @Test
    void generateSorryMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.CINEMAS, Cinema[].class))
                .thenReturn(null);
        SendMessage message = allCinemasMessageHandler.generateMessage(null);
        assertTrue(message.getText().startsWith("Извини"));
    }

    @Test
    void generateNullMessage() {
        Cinema[] list = {};
        Mockito.when(restTemplate.getForObject(RestUrls.CINEMAS, Cinema[].class))
                .thenReturn(list);
        SendMessage message = allCinemasMessageHandler.generateMessage(null);
        assertEquals(message.getText(), "");
    }

    @Test
    void generateSimpleMessage() {
        Cinema[] list = {new Cinema(0, "Cinema", "Adress", null)};
        Mockito.when(restTemplate.getForObject(RestUrls.CINEMAS, Cinema[].class))
                .thenReturn(list);
        SendMessage message = allCinemasMessageHandler.generateMessage(null);
        assertEquals(message.getText(), "1. Cinema /cinema_0");
    }
}