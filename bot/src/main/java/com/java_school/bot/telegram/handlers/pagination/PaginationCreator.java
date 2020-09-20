package com.java_school.bot.telegram.handlers.pagination;

import com.java_school.bot.telegram.handlers.message.MessageType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationCreator {
    @SneakyThrows
    public InlineKeyboardMarkup createPagination(MessageType type, int currentPage, boolean isNext) {
        Field command = type.getClass().getDeclaredField("command");
        command.setAccessible(true);
        String message = (String)command.get(type);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        if (currentPage > 0) {
            row.add(new InlineKeyboardButton()
                    .setText("<")
                    .setCallbackData(message + " " + (currentPage - 1)));
        }
        if (isNext) {
            row.add(new InlineKeyboardButton()
                    .setText(">")
                    .setCallbackData(message + " " + (currentPage + 1)));
        }
        rowList.add(row);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
