package com.java_school.bot.telegram.cache;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionsPaginationCache {
    private final List<Integer> sessionIndsList = new ArrayList<>();

    public int getSessionInd(int page) {
        return sessionIndsList.get(page);
    }

    public boolean hasSessionInd(int page) {
        return page >= 0 && page < sessionIndsList.size();
    }

    public void addNextSessionInd(int index) {
        sessionIndsList.add(index);
    }
}
