package com.java_school.cinemabot.telegram.handler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
    START("/start", "Старт"),
    ALL_FILMS("/films", "Фильмы в прокате"),
    DEFAULT("", "");

    private final String command;
    private final String text;

    public static MessageType findMessageHandler(String message) {
        for (MessageType handler : values()) {
            if (handler.text.equals(message) || handler.command.equals(message)) {
                return handler;
            }
        }
        return MessageType.DEFAULT;
    }
}
