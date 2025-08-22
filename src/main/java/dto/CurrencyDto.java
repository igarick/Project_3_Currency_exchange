package dto;

import java.util.Objects;

public class CurrencyDto {

    private final int id;
    private final String code;
    private final String name;
    private final String sign;

    public CurrencyDto(int id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.name = fullName;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDto that = (CurrencyDto) o;
        return id == that.id && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, sign);
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
               "id=" + id +
               ", code='" + code + '\'' +
               ", name='" + name + '\'' +
               ", sign='" + sign + '\'' +
               '}';
    }
}
