package com.java_school.cinemabot.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    String generateAnswer(Update update);
    CallBackType getCallbackType();
}