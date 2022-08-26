package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void hashingName() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salt = "mySaltValue";
        String salted = PASSWORD+salt;

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
