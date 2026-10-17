package kz.kbtu.banurr.webhw2.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExchangeRateDto {

    @NotBlank
    @Size(min = 3, max = 3)
    private String fromCurrency;

    @NotBlank
    @Size(min = 3, max = 3)
    private String toCurrency;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 2)
    private BigDecimal rate;

    @NotNull
    private LocalDate rateDate;
}
