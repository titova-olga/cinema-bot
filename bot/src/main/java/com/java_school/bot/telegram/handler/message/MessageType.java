package com.java_school.bot.telegram.handler.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {

    START("/start"), // message

    ALL_FILMS("Фильмы"), // message
    FILM("/film_"), // message

    ALL_CINEMAS("Кинотеатры"), // message
    CINEMA("/cinema_"), //message

    ALL_DATES("Даты"),// message
    CALENDAR_IGNORE("CALENDAR IGNORE"),
    CALENDAR_DAY("CALENDAR DAY"),
    CALENDAR_PREV("CALENDAR PREV"),
    CALENDAR_NEXT("CALENDAR NEXT"),

    ALL_SESSIONS("Сеансы"), // message
    SESSION("/session_"), // message

    ANSWER("/answer"), // callback (command)

    DEFAULT("");

    private final String command;

    public static MessageType getMessageType(String message) {
        for (MessageType messageType : values()) {
            if (message.startsWith(messageType.command)) {
                return messageType;
            }
        }
        return MessageType.DEFAULT;
    }
}
