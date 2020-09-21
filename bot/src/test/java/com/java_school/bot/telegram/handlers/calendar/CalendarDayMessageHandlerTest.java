package com.java_school.bot.telegram.handlers.calendar;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Film;
import com.java_school.bot.telegram.handlers.message.FilmMessageHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalendarDayMessageHandlerTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CalendarDayMessageHandler calendarDayMessageHandler;

    @Test
    void generateLateMessage() {
        CallbackQuery message = Mockito.mock(CallbackQuery.class);
        Mockito.when(message.getData()).thenReturn("CALENDAR DAY 9 1999 9");
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getCallbackQuery()).thenReturn(message);

        SendMessage result = calendarDayMessageHandler.generateMessage(update);
        assertTrue(result.getText().contains("опоздал"));
    }

    @Test
    void generateCorrectMessage() {
        LocalDate date = LocalDate.now();
        String s = "CALENDAR DAY " + date.getMonth().getValue() + " " + date.getYear() + " "
                + date.getDayOfMonth() + " ";

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn((long)0);

        CallbackQuery query = Mockito.mock(CallbackQuery.class);
        Mockito.when(query.getData()).thenReturn(s);
        Mockito.when(query.getMessage()).thenReturn(message);

        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getCallbackQuery()).thenReturn(query);

        SendMessage result = calendarDayMessageHandler.generateMessage(update);
        assertTrue(result.getText().contains("Продолжай дальше"));
    }
}