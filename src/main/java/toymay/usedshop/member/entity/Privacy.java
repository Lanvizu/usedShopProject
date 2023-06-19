package toymay.usedshop.member.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

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

//    public void updatePassword(String newPassword) {
//        this.password = newPassword;
//    }

//    public void updateEmailAndPhoneNumber(String email, String phoneNumber) {
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
//    private void setPassword(String password) {
//        this.password = password;
//    }
//    private void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//    private void setEmail(String email) {
//        this.email = email;
//    }
}