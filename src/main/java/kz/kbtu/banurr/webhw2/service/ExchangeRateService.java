package kz.kbtu.banurr.webhw2.service;

import kz.kbtu.banurr.webhw2.exception.KaseException;
import kz.kbtu.banurr.webhw2.model.ExchangeRate;
import kz.kbtu.banurr.webhw2.model.ExchangeRateDto;
import kz.kbtu.banurr.webhw2.repository.CurrencyRepository;
import kz.kbtu.banurr.webhw2.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeRate fetchByParams(String fromCurrency, String toCurrency, LocalDate rateDate) {

        return exchangeRateRepository.fetchByParams(fromCurrency, toCurrency, rateDate)
                .orElseGet(() -> {
                    ExchangeRate fallback = exchangeRateRepository.fetchByParams(toCurrency, fromCurrency, rateDate)
                            .orElseThrow(() -> new KaseException("Not found exchange rate for this params"));

                    fallback.setRate(BigDecimal.ONE.divide(fallback.getRate(), 4, RoundingMode.HALF_UP));

                    fallback.setFromCurrency(fromCurrency);
                    fallback.setToCurrency(toCurrency);

                    return fallback;
                });
    }

    public ExchangeRate fetchLatest(String fromCurrency, String toCurrency) {

        return exchangeRateRepository.fetchLatest(fromCurrency, toCurrency)
                .orElseGet(() -> {
                    ExchangeRate fallback = exchangeRateRepository.fetchLatest(toCurrency, fromCurrency)
                            .orElseThrow(() -> new KaseException("Not found exchange rate for this params"));

                    fallback.setRate(BigDecimal.ONE.divide(fallback.getRate(), 4, RoundingMode.HALF_UP));

                    fallback.setFromCurrency(fromCurrency);
                    fallback.setToCurrency(toCurrency);

                    return fallback;
                });
    }

    public ExchangeRate create(ExchangeRateDto exchangeRate) {

        String fromCurrency = exchangeRate.getFromCurrency();
        String toCurrency = exchangeRate.getToCurrency();

        if (fromCurrency.equals(toCurrency)) {
            throw new KaseException("From and to currency can not be equal!");
        }

        Set<String> currencySet = Set.of(fromCurrency, toCurrency);

        Set<String> existing = currencyRepository.findExisting(currencySet);

        List<String> missing = currencySet.stream()
                .filter(currency -> !existing.contains(currency))
                .toList();

        if (!missing.isEmpty()) {
            throw new KaseException("This currencies does not exist in dictionary: " + missing);
        }

        return exchangeRateRepository.create(exchangeRate);
    }

    public void createBulk(List<ExchangeRateDto> exchangeRateList) {

        Set<String> currencySet = new HashSet<>();
        for (ExchangeRateDto exchangeRate : exchangeRateList) {

            String fromCurrency = exchangeRate.getFromCurrency();
            String toCurrency = exchangeRate.getToCurrency();

            if (fromCurrency.equals(toCurrency)) {
                throw new KaseException("From and to currency can not be equal!");
            }

            currencySet.add(fromCurrency);
            currencySet.add(toCurrency);
        }

        Set<String> existing = currencyRepository.findExisting(currencySet);

        List<String> missing = currencySet.stream()
                .filter(currency -> !existing.contains(currency))
                .toList();

        if (!missing.isEmpty()) {
            throw new KaseException("This currencies does not exist in dictionary: " + missing);
        }

        exchangeRateRepository.createBulk(exchangeRateList);
    }
}
