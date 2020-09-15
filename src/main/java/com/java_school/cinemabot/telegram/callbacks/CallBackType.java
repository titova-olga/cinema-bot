package com.java_school.cinemabot.telegram.callbacks;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Call;

@RequiredArgsConstructor
public enum CallBackType {
    CALENDAR_IGNORE("CALENDAR", "IGNORE"),
    CALENDAR_DAY("CALENDAR", "DAY"),
    CALENDAR_PREV("CALENDAR", "PREV"),
    CALENDAR_NEXT("CALENDAR", "NEXT"),
    DEFAULT("", "");

    private final String command;
    private final String args;

    public static CallBackType findCallbackType(String data) {
        for (CallBackType handler : values()) {
            if (data.startsWith(handler.command) && data.contains(handler.args)) {
                return handler;
            }
        }
        return CallBackType.DEFAULT;
    }
}
