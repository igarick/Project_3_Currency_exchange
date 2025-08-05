package dto;

public record CurrencyFilter(String code,
                             String fullName,
                             int limit,
                             int offset) {
}
