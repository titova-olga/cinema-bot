package com.java_school.bot.telegram.cache;

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
