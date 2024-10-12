package com.ericmignardi.gms;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretGeneratorTest {

    @Test
    void generateSecretKey() {
        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        String encodedSecretKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
        System.out.println(encodedSecretKey);
    }
}
