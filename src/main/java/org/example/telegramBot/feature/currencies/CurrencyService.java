package org.example.telegramBot.feature.currencies;

import org.example.telegramBot.feature.dto.Currency;

public interface CurrencyService {
    double getRate(Currency currency);
}
