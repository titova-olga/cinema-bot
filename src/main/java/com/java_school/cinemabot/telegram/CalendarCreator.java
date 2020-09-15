package com.java_school.cinemabot.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;

public interface CalendarCreator {
    public InlineKeyboardMarkup createCalendar(LocalDate date);
}
