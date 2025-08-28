package validators;

import jakarta.servlet.http.HttpServletRequest;

public class RequestParameterCreateValidator_1 extends AbstractValidator implements Validator_1<HttpServletRequest> {

    @Override
    public void validate(HttpServletRequest req) {
        validateCode(req.getParameter("code").toUpperCase());
        validateName(req.getParameter("name"));
        validateSign(req.getParameter("sign"));
    }

}
