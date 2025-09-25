package validator;

import dto.CurrencyPairCodeDto;
import exception.ValidationException;
import exception.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;

public class RequestExchangeRateValidator {
    private static final int MAX_EXCHANGE_RATE_SCALE = 6;

    private final BaseValidator baseValidator;

    public RequestExchangeRateValidator(BaseValidator baseValidator) {
        this.baseValidator = baseValidator;
    }

    public void validateParams(String baseCode, String targetCode, String rate) {
        if (baseValidator.isEmpty(baseCode) || baseValidator.isEmpty(targetCode) || baseValidator.isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        baseValidator.validateCode(baseCode);
        baseValidator.validateCode(targetCode);
        baseValidator.checkIdentity(baseCode, targetCode);
        baseValidator.validateDecimal(rate, ErrorInfo.EXCHANGE_RATE_ERROR, MAX_EXCHANGE_RATE_SCALE);
    }

    public BigDecimal extractAndValidateRate(HttpServletRequest req) throws IOException {
        String parameter = req.getReader().readLine();

        if (parameter == null || !parameter.contains("rate=")) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        String rate = parameter.replace("rate=", "");

        if (baseValidator.isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        baseValidator.validateDecimal(rate, ErrorInfo.EXCHANGE_RATE_ERROR, MAX_EXCHANGE_RATE_SCALE);
        return new BigDecimal(rate);
    }

    public CurrencyPairCodeDto extractAndValidateCurrencyPairCode(String path) {
        String rawPairCode = path.substring(1);

        if (!rawPairCode.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }

        String baseCode = rawPairCode.substring(0, 3).toUpperCase();
        String targetCode = rawPairCode.substring(3, 6).toUpperCase();

        baseValidator.checkIdentity(baseCode, targetCode);

        return new CurrencyPairCodeDto(baseCode, targetCode);
    }
}
