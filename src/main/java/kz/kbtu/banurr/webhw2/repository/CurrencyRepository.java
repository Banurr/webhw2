package kz.kbtu.banurr.webhw2.repository;

import kz.kbtu.banurr.webhw2.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CurrencyRepository {

    private final JdbcClient client;

    public List<Currency> fetchAll() {

        return client.sql("select * from currency where is_active = true")
                .query(Currency.class)
                .list();
    }

    public Optional<Currency> fetchByCode(String code) {

        return client.sql("select * from currency where is_active = true and code = :code")
                .param("code", code)
                .query(Currency.class)
                .optional();
    }

    public Set<String> findExisting(Set<String> currencyList) {

        return new HashSet<>(client.sql("select code from currency where code in (:currencyList)")
                .param("currencyList", currencyList)
                .query(String.class)
                .list());
    }
}
