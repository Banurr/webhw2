package kz.kbtu.banurr.webhw2.service;

import kz.kbtu.banurr.webhw2.exception.KaseException;
import kz.kbtu.banurr.webhw2.model.Currency;
import kz.kbtu.banurr.webhw2.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> fetchAll() {
        return currencyRepository.fetchAll();
    }

    public Currency fetchByCode(String code) {
        return currencyRepository.fetchByCode(code)
                .orElseThrow(() -> new KaseException("Not found currency by code: " + code));
    }
}
