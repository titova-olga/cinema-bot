package com.java_school.cinemabot.telegram.cache;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UsersChoicesCache {
    private final Map<Long, UserChoice> usersChoicesMap = new HashMap<>();

    public UserChoice getOrCreateUserChoice(long chatId) {
        if (!usersChoicesMap.containsKey(chatId)) {
            usersChoicesMap.put(chatId, new UserChoice());
        }
        return usersChoicesMap.get(chatId);
    }

    public UserChoice getUserChoice(long chatId){
        return usersChoicesMap.get(chatId);
    }

    public void removeInfoAboutUserChoices(long chatId) {
        usersChoicesMap.remove(chatId);
    }
}
