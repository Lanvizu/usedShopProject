package toymay.usedshop.common.exception.form.signUp;

import toymay.usedshop.common.exception.form.CustomFormException;

public class DuplicateNicknameException extends CustomFormException {

    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException(String message, String field) {
        super(message, field);
    }

    public DuplicateNicknameException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
