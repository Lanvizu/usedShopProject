package toymay.usedshop.member.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static toymay.usedshop.common.exception.form.FormExceptionType.INVALID_EMAIL;
import static toymay.usedshop.common.exception.form.FormExceptionType.INVALID_PHONE_NUMBER;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Privacy {

    private String password;
    private String phoneNumber;
    private String email;


    public static Privacy create(String password, String phoneNumber, String email) {
        Privacy privacy = new Privacy();
        privacy.setPassword(password);
        privacy.setPhoneNumber(phoneNumber);
        privacy.setEmail(email);
        return privacy;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email){
        if(!isEmail(email)){
            throw INVALID_EMAIL.getException();
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
         if (!isPhoneNumber(phoneNumber)) {
            throw INVALID_PHONE_NUMBER.getException();
        }
    }

    public static boolean isEmail(String email) {
        return email.matches("^[A-Za-z\\d_.-]+@(.+)$");
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{9,11}");
    }
}