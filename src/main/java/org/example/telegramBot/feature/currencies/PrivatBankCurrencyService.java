package org.example.telegramBot.feature.currencies;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.telegramBot.feature.dto.Currency;
import org.example.telegramBot.feature.dto.CurrencyItem;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivatBankCurrencyService implements CurrencyService {
    @Override
    public double getRate(Currency currency) {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        //Get Json
        String json;
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to Privat API");
        }

        //Convert Json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItem.class)
                .getType();

        List<CurrencyItem> currencyItems = new Gson().fromJson(json, typeToken);

        //Find UAH/USD
        return currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyItem::getBuy)
                .findFirst()
                .orElseThrow();
    }
}
