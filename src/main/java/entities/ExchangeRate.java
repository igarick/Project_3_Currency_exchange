package entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


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
