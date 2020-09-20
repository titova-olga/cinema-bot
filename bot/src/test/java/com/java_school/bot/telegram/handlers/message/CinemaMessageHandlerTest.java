package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CinemaMessageHandlerTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CinemaMessageHandler cinemaMessageHandler;

    @Test
    void generateNullMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.CINEMAS + "/" + 0, Cinema.class))
                .thenReturn(null);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("/cinema_0");
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()).thenReturn(message);

        SendMessage result = cinemaMessageHandler.generateMessage(update);
        assertEquals(result.getText(), "");
    }

    @Test
    void generateSimpleMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.CINEMAS + "/" + 0, Cinema.class))
                .thenReturn(new Cinema(0, "Cinema", "Address", null));
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("/cinema_0");
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()).thenReturn(message);

        SendMessage result = cinemaMessageHandler.generateMessage(update);
        assertEquals(result.getText(), Stickers.CINEMA + "<b><i>" + "Cinema" + "</i></b>" + "\n"
                + "Address");
    }
}