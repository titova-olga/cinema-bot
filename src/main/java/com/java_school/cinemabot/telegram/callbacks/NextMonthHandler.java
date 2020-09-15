package com.java_school.cinemabot.telegram.callbacks;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NextMonthHandler implements CallbackHandler {
    @Override
    public String generateAnswer(Update update) {
        return null;
    }

    @Override
    public CallBackType getCallbackType() {
        return CallBackType.CALENDAR_NEXT;
    }
}
