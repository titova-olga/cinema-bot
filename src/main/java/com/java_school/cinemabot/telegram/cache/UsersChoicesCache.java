package com.java_school.cinemabot.telegram.cache;

import java.util.*;


public class UsersChoicesCache {
    private final static Map<Long, UserChoice> usersChoicesMap = new HashMap<>();

    public static UserChoice getOrCreateUserChoice(long chatId) {
        if (!usersChoicesMap.containsKey(chatId)) {
            usersChoicesMap.put(chatId, new UserChoice());
        }
        return usersChoicesMap.get(chatId);
    }

    public static UserChoice getUserChoice(long chatId){
        return usersChoicesMap.get(chatId);
    }

    public static void removeInfoAboutUserChoices(long chatId) {
        usersChoicesMap.remove(chatId);
    }
}
