package org.example.telegramBot.feature.telegram.command;

import org.example.telegramBot.feature.dto.Currency;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = "Курс якої валюти ви хочете дізнатись:";

        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.toString(chat.getId()));

        // Create list of buttons
        List<InlineKeyboardButton> buttons = Stream.of(Currency.USD, Currency.EUR, Currency.RUR)
                .map(Enum::name)
                .map(it -> InlineKeyboardButton.builder().text(it).callbackData(it).build())
                .collect(Collectors.toList());

        // Create keyboard
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singleton(
                        buttons
                ))
                .build();

        message.setReplyMarkup(keyboard);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
