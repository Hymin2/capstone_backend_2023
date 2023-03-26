package ac.kr.tukorea.capstone.config.Security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class WebConfigSecurityTest {
    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode(){
        WebConfigSecurity webConfigSecurity = new WebConfigSecurity();

        String password = "password";
        String encode = webConfigSecurity.getPasswordEncoder().encode(password);

        assertThat(password).isNotEqualTo(encode);

        System.out.println(encode);
    }

    @Test
    @DisplayName("서로 다른 패스워드가 암호화 될 때 다른지 테스트")
    void encodedPasswordIsNotSame(){
        WebConfigSecurity webConfigSecurity = new WebConfigSecurity();

        String password1 = "password1";
        String encode_password1 = webConfigSecurity.getPasswordEncoder().encode(password1);

        String password2 = "password2";
        String encode_password2 = webConfigSecurity.getPasswordEncoder().encode(password2);

        assertThat(encode_password1).isNotEqualTo(encode_password2);
    }
}