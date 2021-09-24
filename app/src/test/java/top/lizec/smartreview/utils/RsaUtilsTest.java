package top.lizec.smartreview.utils;

import org.junit.jupiter.api.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RsaUtilsTest {

    @Test
    void getPrivateKey() {
        PrivateKey privateKey = RsaUtils.getPrivateKey();
        assertNotNull(privateKey);
    }

    @Test
    void getPublicKey() {
        PublicKey publicKey = RsaUtils.getPublicKey();
        assertNotNull(publicKey);
    }
}