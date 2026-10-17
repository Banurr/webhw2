package kz.kbtu.banurr.webhw2.repository;

import kz.kbtu.banurr.webhw2.model.ExchangeRate;
import kz.kbtu.banurr.webhw2.model.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExchangeRateRepository {

    private final JdbcClient client;
    private final JdbcTemplate jdbcTemplate;

    public Optional<ExchangeRate> fetchByParams(String fromCurrency, String toCurrency, LocalDate rateDate) {
        return client.sql("select * from exchange_rate " +
                "where from_currency = :fromCurrency and to_currency = :toCurrency and rate_date = :rateDate limit 1")
                .param("fromCurrency", fromCurrency)
                .param("toCurrency", toCurrency)
                .param("rateDate", rateDate)
                .query(ExchangeRate.class)
                .optional();
    }

    public Optional<ExchangeRate> fetchLatest(String fromCurrency, String toCurrency) {
        return client.sql("select * from exchange_rate " +
                        "where from_currency = :fromCurrency and to_currency = :toCurrency order by rate_date desc limit 1")
                .param("fromCurrency", fromCurrency)
                .param("toCurrency", toCurrency)
                .query(ExchangeRate.class)
                .optional();
    }

    public ExchangeRate create(ExchangeRateDto exchangeRate) {
        return client.sql("insert into exchange_rate" +
                        "(from_currency, to_currency, rate, rate_date) values " +
                        "(:fromCurrency, :toCurrency, :rate, :rateDate)" +
                        "returning *")
                .param("fromCurrency", exchangeRate.getFromCurrency())
                .param("toCurrency", exchangeRate.getToCurrency())
                .param("rate", exchangeRate.getRate())
                .param("rateDate", exchangeRate.getRateDate())
                .query(ExchangeRate.class)
                .single();
    }

    public void createBulk(List<ExchangeRateDto> exchangeRateList) {

        List<Object[]> args = exchangeRateList.stream()
                .map(exchangeRate -> new Object[] {
                        exchangeRate.getFromCurrency(),
                        exchangeRate.getToCurrency(),
                        exchangeRate.getRate(),
                        exchangeRate.getRateDate()
                })
                .toList();

        jdbcTemplate.batchUpdate("INSERT INTO exchange_rate (from_currency, to_currency, rate, rate_date) VALUES (?, ?, ?, ?)", args);
    }
}
