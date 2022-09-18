package org.example.telegramBot.feature.telegram;

import org.example.telegramBot.feature.currencies.CurrencyService;
import org.example.telegramBot.feature.currencies.PrivatBankCurrencyService;
import org.example.telegramBot.feature.dto.Currency;
import org.example.telegramBot.feature.telegram.command.HelpCommand;
import org.example.telegramBot.feature.telegram.command.StartCommand;
import org.example.telegramBot.feature.ui.PrettyPrintCurrencyService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private CurrencyService currencyService;
    private PrettyPrintCurrencyService prettyPrintCurrencyService;
    public CurrencyTelegramBot() {
        currencyService = new PrivatBankCurrencyService();
        prettyPrintCurrencyService = new PrettyPrintCurrencyService();

        register(new StartCommand());
        register(new HelpCommand());
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasCallbackQuery()){
            String callBackQuery = update.getCallbackQuery().getData();
            Currency currency = Currency.valueOf(callBackQuery);

            double currencyRate = currencyService.getRate(currency);
            String prettyText = prettyPrintCurrencyService.convert(currencyRate, currency);
            SendMessage responseMessage = new SendMessage();

            responseMessage.setText(prettyText);
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            responseMessage.setChatId(Long.toString(chatId));
            try {
                execute(responseMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
        if(update.hasMessage()){
            String message = update.getMessage().getText();

            String responseMessage = "Ви написали: " + message;
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(responseMessage);
            sendMessage.setChatId(Long.toString(update.getMessage().getChatId()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
