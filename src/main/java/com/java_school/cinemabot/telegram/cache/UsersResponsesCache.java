package com.java_school.cinemabot.telegram.cache;

import org.springframework.stereotype.Component;

import java.util.*;


public class UsersResponsesCache {
    private final static Map<Long, Map<ResponseType, List<Integer>>> usersResponsesMap = new HashMap<>();

    public static void addUserResponse(long chatId, ResponseType type, int responseId) {
        if (!usersResponsesMap.containsKey(chatId)) {
            usersResponsesMap.put(chatId, createTemplateMapForResponses());
        }
        usersResponsesMap.get(chatId).get(type).add(responseId);
    }

    private static Map<ResponseType, List<Integer>> createTemplateMapForResponses() {
        Map<ResponseType, List<Integer>> responsesMap = new HashMap<>();
        Arrays.stream(ResponseType.values()).forEach(responseType -> responsesMap.put(responseType, new ArrayList<>()));
        return responsesMap;
    }

    public static Map<ResponseType, List<Integer>> getUsersResponses(long chatId){
        return usersResponsesMap.get(chatId);
    }

    public static void removeInfoAboutUserResponses(long chatId) {
        usersResponsesMap.remove(chatId);
    }
}
