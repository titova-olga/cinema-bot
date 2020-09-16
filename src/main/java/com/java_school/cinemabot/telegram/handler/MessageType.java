package com.java_school.cinemabot.telegram.handler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
    START("/start", "Старт"),
    FILM("/film_", "Фильм"), //todo fix text
    ALL_FILMS("/films", "Фильмы в прокате"),
    ALL_CINEMAS("/cinemas", "Кинотеатры"),
    DEFAULT("", "");

    private final String command;
    private final String text;

    public static MessageType findMessageHandler(String message) {
        for (MessageType handler : values()) {
            if (message.equals(handler.text) || message.startsWith(handler.command)) {
                return handler;
            }
        }
        return MessageType.DEFAULT;
    }
}
