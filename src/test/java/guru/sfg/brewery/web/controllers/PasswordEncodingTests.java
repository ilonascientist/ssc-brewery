package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.NoopProcessorErrorHandler;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.NoopCharAppender;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import javax.naming.ldap.LdapContext;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void hashingName() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salt = "mySaltValue";
        String salted = PASSWORD+salt;

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }

    @Test
    void testNoOp() {
        PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        System.out.println(encoder.encode(PASSWORD));

    }

}
