package validators;

import dto.CurrencyPairCodeDto;
import exception.ValidationException;
import exception.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;

public class RequestExchangeRateValidator extends AbstractValidator {
    private static final int MAX_EXCHANGE_RATE_SCALE = 6;

    public void validate(String baseCode, String targetCode, String rate) {
        if (isEmpty(baseCode) || isEmpty(targetCode) || isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(baseCode);
        validateCode(targetCode);
        checkIdentity(baseCode, targetCode);
        validateRate(rate);
    }

    private void validateRate(String rate) {
        validateDecimal(rate, ErrorInfo.EXCHANGE_RATE_ERROR, MAX_EXCHANGE_RATE_SCALE);
    }

    public BigDecimal extractAndValidateRate(HttpServletRequest req) throws IOException {
        String parameter = req.getReader().readLine();

        if (parameter == null || !parameter.contains("rate=")) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        String rate = parameter.replace("rate=", "");

        if (isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateRate(rate);
        return new BigDecimal(rate);
    }

    public CurrencyPairCodeDto extractAndValidateCurrencyPairCode(String path) {
        String rawPairCode = path.substring(1);

        if (!rawPairCode.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }

        String baseCode = rawPairCode.substring(0, 3).toUpperCase();
        String targetCode = rawPairCode.substring(3, 6).toUpperCase();

        checkIdentity(baseCode, targetCode);

        return new CurrencyPairCodeDto(baseCode, targetCode);
    }

    @Override
    public String extractAndValidatePath(HttpServletRequest request) {
        return super.extractAndValidatePath(request);
    }
}
