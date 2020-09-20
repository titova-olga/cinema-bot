package com.java_school.bot.telegram.cache;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionsPaginationCache {
    private final Map<Long, List<Integer>> sessionIndsList = new HashMap<>();

    public int getSessionInd(long chatId, int page) {
        initialize(chatId);
        return sessionIndsList.get(chatId).get(page);
    }

    public boolean hasSessionInd(long chatId, int page) {
        initialize(chatId);
        return page >= 0 && page < sessionIndsList.get(chatId).size();
    }

    public void addNextSessionInd(long chatId, int index) {
        initialize(chatId);
        sessionIndsList.get(chatId).add(index);
    }

    public void clearSelection(long chatId) {
        sessionIndsList.remove(chatId);
    }

    private void initialize(long chatId) {
        if (!sessionIndsList.containsKey(chatId)) {
            sessionIndsList.put(chatId, new ArrayList<>());
            sessionIndsList.get(chatId).add(0);
        }
    }
}
