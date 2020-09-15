package com.java_school.cinemabot.telegram.callbacks;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DayHandler implements CallbackHandler {
    @Override
    public String generateAnswer(Update update) {
        String[] s = update.getCallbackQuery().getData().split(" ");
        int chosenDay = Integer.parseInt(s[4]);
        return "You choose day number " + chosenDay;
    }

    @Override
    public CallBackType getCallbackType() {
        return CallBackType.CALENDAR_DAY;
    }
}
