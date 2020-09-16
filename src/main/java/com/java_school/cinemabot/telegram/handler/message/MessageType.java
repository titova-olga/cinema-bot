package com.java_school.cinemabot.telegram.handler.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {

    START("/start", "Старт"), // message
    ALL_FILMS("/films","Фильмы"), // message
    FILM("/film_", "Фильм"), // message
    ALL_CINEMAS("/cinemas","Кинотеатры"), // message
    CINEMA("/cinema_", "Кинотеатр"), //message
    ALL_DATES("/dates","Даты"),// message
    ALL_SESSIONS("/sessions","Сеансы"), // message
    SESSION("/session_", "Сеанс"), // message
    ANSWER("/answer", "Ответ"), // callback (command)
    DEFAULT("","");

    private final String command;
    private final String text;

    public static MessageType getMessageType(String message) {
        for (MessageType messageType : values()) {
            if (message.equals(messageType.text) || message.startsWith(messageType.command)) {
                return messageType;
            }
        }
        return MessageType.DEFAULT;
    }
}
