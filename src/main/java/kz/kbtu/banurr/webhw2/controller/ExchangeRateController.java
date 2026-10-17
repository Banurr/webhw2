package kz.kbtu.banurr.webhw2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.kbtu.banurr.webhw2.model.ExchangeRate;
import kz.kbtu.banurr.webhw2.model.ExchangeRateDto;
import kz.kbtu.banurr.webhw2.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public ExchangeRate fetchByParams(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam LocalDate rateDate) {
        return exchangeRateService.fetchByParams(fromCurrency, toCurrency, rateDate);
    }

    @GetMapping("/latest")
    public ExchangeRate fetchLatest(@RequestParam String fromCurrency, @RequestParam String toCurrency) {
        return exchangeRateService.fetchLatest(fromCurrency, toCurrency);
    }

    @PostMapping
    public ExchangeRate create(@Valid @RequestBody ExchangeRateDto request) {
        return exchangeRateService.create(request);
    }

    @PostMapping("/bulk")
    public void create(@NotEmpty @RequestBody List<@NotNull @Valid ExchangeRateDto> request) {
        exchangeRateService.createBulk(request);
    }
}
