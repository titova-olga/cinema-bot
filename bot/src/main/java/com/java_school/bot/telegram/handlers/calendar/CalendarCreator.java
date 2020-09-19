package com.java_school.bot.telegram.handlers.calendar;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;

public interface CalendarCreator {
    InlineKeyboardMarkup createCalendar(LocalDate date);
}
