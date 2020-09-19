package com.java_school.bot.telegram.handlers.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Stickers {
    FILM("\uD83C\uDFAC"),
    CINEMA("\uD83C\uDFDB"),
    MONEY("\uD83D\uDCB8"),
    SMILE("\uD83D\uDE01"); // "\uD83D\uDCB0" "\uD83D\uDCB5"

    private final String code;

    @Override
    public String toString() {
        return code;
    }
}
