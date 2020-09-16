package com.java_school.cinemabot.telegram.handler.calendar;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class CalendarCreatorImpl implements CalendarCreator {
    public InlineKeyboardMarkup createCalendar(LocalDate date) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        var month = date.getMonth();
        var year = date.getYear();
        var isLeapYear = date.isLeapYear();

        final String dataIgnore = "CALENDAR IGNORE";
        final String dataDay = "CALENDAR DAY " + month.getValue() + " " + year + " ";
        final String dataPrev = "CALENDAR PREV " + month.getValue() + " " + year;
        final String dataNext = "CALENDAR NEXT " + month.getValue() + " " + year;

        //First row - Month and Year
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(new InlineKeyboardButton()
                .setText(month.toString() + " " + year)
                .setCallbackData(dataIgnore));
        rowList.add(row);

        //Second row - Week Days
        row = new ArrayList<>();
        Stream.of("Mo","Tu","We","Th","Fr","Sa","Su")
                .map(day -> new InlineKeyboardButton().setText(day).setCallbackData(dataIgnore))
                .forEach(row::add);
        rowList.add(row);

        var firstDayOfWeek = LocalDate.ofYearDay(year, month.firstDayOfYear(isLeapYear))
                .getDayOfWeek().getValue();

        row = new ArrayList<>();
        for (int i = 1; i < firstDayOfWeek; i++)
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(dataIgnore));
        for (int i = firstDayOfWeek; i <= 7; i++)
            row.add(new InlineKeyboardButton().setText("" + (i - firstDayOfWeek + 1))
                    .setCallbackData(dataDay + (i - firstDayOfWeek + 1)));
        rowList.add(row);
        for (int week = 1; week < 5; week++) {
            row = new ArrayList<>();
            for (int day = 1; day <= 7; day++) {
                int curDay = week * 7 + day - firstDayOfWeek + 1;
                if (curDay < month.length(isLeapYear))
                    row.add(new InlineKeyboardButton().setText("" + curDay).setCallbackData(dataDay + curDay));
                else {
                    if (day == 1)
                        break;
                    row.add(new InlineKeyboardButton().setText(" ").setCallbackData(dataIgnore));
                }
            }
            rowList.add(row);
        }

        //Last row - Buttons
        row = new ArrayList<>();
        row.add(new InlineKeyboardButton().setText("<").setCallbackData(dataPrev));
        row.add(new InlineKeyboardButton().setText(" ").setCallbackData(dataIgnore));
        row.add(new InlineKeyboardButton().setText(">").setCallbackData(dataNext));
        rowList.add(row);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
