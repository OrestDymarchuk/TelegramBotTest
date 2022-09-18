package org.example.telegramBot.feature.ui;

import org.example.telegramBot.feature.dto.Currency;

public class PrettyPrintCurrencyService {
    public String convert(double rate, Currency currency){
        String template = "Курс ${currency} => UAH => ${rate}";

        float roundedCurrency = Math.round(rate * 100d) / 100.f;

        return template
                .replace("${currency}", currency.name())
                .replace("${rate}", roundedCurrency + "");
    }
}
