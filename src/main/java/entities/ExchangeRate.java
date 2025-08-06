package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRate {
    private int ID;
    private String BaseCurrencyId;
    private String TargetCurrencyId;
    private BigDecimal Rate;

    public ExchangeRate(int ID, String baseCurrencyId, String targetCurrencyId, BigDecimal rate) {
        this.ID = ID;
        this.BaseCurrencyId = baseCurrencyId;
        this.TargetCurrencyId = targetCurrencyId;
        this.Rate = rate;
    }

    public ExchangeRate() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBaseCurrencyId() {
        return BaseCurrencyId;
    }

    public void setBaseCurrencyId(String baseCurrencyId) {
        BaseCurrencyId = baseCurrencyId;
    }

    public String getTargetCurrencyId() {
        return TargetCurrencyId;
    }

    public void setTargetCurrencyId(String targetCurrencyId) {
        TargetCurrencyId = targetCurrencyId;
    }

    public BigDecimal getRate() {
        return Rate;
    }

    public void setRate(BigDecimal rate) {
        Rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
               "ID=" + ID +
               ", BaseCurrencyId='" + BaseCurrencyId + '\'' +
               ", TargetCurrencyId='" + TargetCurrencyId + '\'' +
               ", Rate=" + Rate +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return ID == that.ID && Objects.equals(BaseCurrencyId, that.BaseCurrencyId) && Objects.equals(TargetCurrencyId, that.TargetCurrencyId) && Objects.equals(Rate, that.Rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, BaseCurrencyId, TargetCurrencyId, Rate);
    }
}
