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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmMessageHandlerTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    FilmMessageHandler filmMessageHandler;

    @Test
    void generateNullMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.FILMS + "/" + 0, Film.class))
                .thenReturn(null);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("/film_0");
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()).thenReturn(message);

        SendMessage result = filmMessageHandler.generateMessage(update);
        assertEquals(result.getText(), "");
    }

    @Test
    void generateSimpleMessage() {
        Mockito.when(restTemplate.getForObject(RestUrls.FILMS + "/" + 0, Film.class))
                .thenReturn(new Film(0, "Film", "Genre", 0, "Hi",
                        0, "Somebody", null, "Russia"));
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("/film_0");
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()).thenReturn(message);

        SendMessage result = filmMessageHandler.generateMessage(update);
        assertEquals(result.getText(),
                Stickers.FILM + "<b><i>" + "Film" + "</i></b>" + Stickers.FILM + "\n\n"
                        + "<b>Жанр:</b> " + "Genre" + "\n"
                        + "<b>Режиссер:</b> " + "Somebody" + "\n"
                        + "<b>Возрастное ограничение:</b> " + 0 + "+\n"
                        + "<b>Описание:</b> " + "Hi");
    }
}