package org.example;

import org.example.telegramBot.feature.currencies.CurrencyService;
import org.example.telegramBot.feature.currencies.PrivatBankCurrencyService;
import org.example.telegramBot.feature.dto.Currency;
import org.example.telegramBot.feature.telegram.TelegramBotService;
import org.example.telegramBot.feature.ui.PrettyPrintCurrencyService;

public class TelegramBotApp {
    public static void main(String[] args) {
        TelegramBotService telegramBotService = new TelegramBotService();
    }
}