package com.java_school.cinemabot.telegram.cache;

import com.java_school.cinemabot.telegram.handler.message.MessageType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseType {
    FILM("/answer/film"),
    CINEMA("/answer/cinema");

    private final String response;

    public static ResponseType getResponseType(String message) {
        for (ResponseType responseType : values()) {
            if (message.startsWith(responseType.response)) {
                return responseType;
            }
        }
        return null;
    }
}
