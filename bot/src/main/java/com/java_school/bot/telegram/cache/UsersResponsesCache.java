package com.java_school.bot.telegram.cache;

import java.util.HashMap;
import java.util.Map;


public class UsersResponsesCache {
    private final static Map<Long, Map<ResponseType, Integer>> usersResponsesMap = new HashMap<>();

    public static void addUserResponse(long chatId, ResponseType type, int responseId) {
        if (!usersResponsesMap.containsKey(chatId)) {
            usersResponsesMap.put(chatId, new HashMap<>());
        }
        usersResponsesMap.get(chatId).put(type, responseId);
    }

    public static Map<ResponseType, Integer> getUsersResponses(long chatId){
        return usersResponsesMap.get(chatId);
    }

    public static void removeInfoAboutUserResponses(long chatId) {
        usersResponsesMap.remove(chatId);
    }
}
