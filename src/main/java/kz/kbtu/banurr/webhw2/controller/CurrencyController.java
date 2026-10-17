package kz.kbtu.banurr.webhw2.controller;

import kz.kbtu.banurr.webhw2.model.Currency;
import kz.kbtu.banurr.webhw2.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<Currency> fetchAll() {
        return currencyService.fetchAll();
    }

    @GetMapping("/{code}")
    public Currency fetchByCode(@PathVariable String code) {
        return currencyService.fetchByCode(code);
    }
}
