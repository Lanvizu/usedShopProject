package toymay.usedshop.common.exception.form;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toymay.usedshop.common.exception.CustomException;
import toymay.usedshop.common.exception.form.signUp.DuplicateEmailException;
import toymay.usedshop.common.exception.form.signUp.DuplicateNicknameException;
import toymay.usedshop.common.exception.form.signUp.DuplicatePhoneNumberException;
import toymay.usedshop.common.exception.form.signUp.TotalExceptionMessage;

@Getter
@RequiredArgsConstructor
public enum FormExceptionType {
        //로그인 관련 에러
//        NOT_FOUND_ACCOUNT_BY_NICKNAME(new NotFoundAccountByNicknameException(
//                "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.",
//                "signInId", "incorrect")),
//        NOT_FOUND_ACCOUNT_BY_Email(new NotFoundAccountByEmailException(
//                "입력한 이메일을 사용하는 계정을 찾을 수 없습니다. 사용자의 이메일을 확인하고 다시 시도하세요.",
//                "signInId", "incorrect")),
//        NOT_FOUND_ACCOUNT_BY_Phone_Number(new NotFoundAccountByPhoneNumberException(
//                "입력한 휴대폰 번호를 사용하는 계정을 찾을 수 없습니다. 사용자의 휴대폰 번호를 확인하고 다시 시도하세요.",
//                "password", "incorrect")),
//        INCORRECT_PASSWORD(new IncorrectPasswordException(
//                "잘못된 비밀번호입니다. 다시 확인하세요.", "password", "incorrect")),
//
//        // 회원가입 관련 에러
        DUPLICATE_NICKNAME(new DuplicateNicknameException(
                "다른 계정에서 이미 사용중인 닉네임입니다.", "nickname", "duplicate")),
        DUPLICATE_EMAIL(new DuplicateEmailException(
                "다른 계정에서 이미 사용중인 이메일입니다.", "email", "duplicate")),
        DUPLICATE_PHONE_NUMBER(new DuplicatePhoneNumberException(
                "다른 계정에서 이미 사용중인 휴대폰 번호입니다.", "phoneNumber", "duplicate")),
        INVALID_NICKNAME_BY_NUMBER(new TotalExceptionMessage(
                "사용자의 이름에 숫자만 포함할 수는 없습니다.", "nickname", "invalid")),
        INVALID_NICKNAME_BY_STRING(new TotalExceptionMessage(
                "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.", "nickname", "invalid")),
        INVALID_EMAIL(new TotalExceptionMessage(
                "유효한 이메일 주소를 입력하세요.", "email", "invalid")),
        INVALID_PHONE_NUMBER(new TotalExceptionMessage(
                "유효한 휴대폰 번호를 입력하세요.", "phoneNumber", "invalid"));
//
//        // 파일 관련
//        EMPTY_FILE(new EmptyFileException("파일을 하나 이상 첨부하세요.", "files", "required")),
//        INVALID_IMAGE_EXTENSION(new InvalidImageExtensionException(
//                "확장자는 JPG, JPEG, PNG만 가능합니다.", "files", "invalid"));

        private final CustomException exception;
}
