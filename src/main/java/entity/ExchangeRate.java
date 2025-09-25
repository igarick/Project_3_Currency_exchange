package entity;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor

public class ExchangeRate {
    private Long id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private BigDecimal rate;
}
