package com.java_school.cinemabot.telegram.handler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
    START("/start", "Старт"),
    ALL_FILMS("/films", "Фильмы в прокате"),
    CHOSE_DAY("/day", "Выбор дня"),

    CALENDAR_IGNORE("", "CALENDAR IGNORE"),
    CALENDAR_DAY("", "CALENDAR DAY"),
    CALENDAR_PREV("", "CALENDAR PREV"),
    CALENDAR_NEXT("", "CALENDAR NEXT"),

    DEFAULT("", "");

    private final String command;
    private final String text;

    public static MessageType findMessageHandler(String message) {
        for (MessageType handler : values()) {
            if (handler.command.equals(message) || message.startsWith(handler.text)) {
                return handler;
            }
        }
        return MessageType.DEFAULT;
    }
}
