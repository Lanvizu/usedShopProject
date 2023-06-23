package toymay.usedshop.common.exception.form.signUp;

import toymay.usedshop.common.exception.form.CustomFormException;

public class TotalExceptionMessage extends CustomFormException {

    public TotalExceptionMessage(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
