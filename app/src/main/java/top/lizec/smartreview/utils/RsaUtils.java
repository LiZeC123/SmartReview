package top.lizec.smartreview.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RsaUtils {
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static final Path privateKeyPath = Paths.get("data", "RSA_PRIVATE");
    private static final Path publicKeyPath = Paths.get("data", "RSA_PUBLIC");

    static {
        try {
            initKey();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    private static void initKey() throws Exception {
        if (hasGenerateKey()) {
            log.info("从文件加载Key");
            privateKey = loadPrivateKey();
            publicKey = loadPublicKey();
        } else {
            log.info("没有检测到Key文件, 重新生成Key文件");
            createKeyPair();
        }
        log.info("Key加载完毕");
    }

    private static boolean hasGenerateKey() {
        return Files.exists(privateKeyPath) && Files.exists(publicKeyPath);
    }


    private static void createKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(4096, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

            try (OutputStream out = Files.newOutputStream(publicKeyPath)) {
                out.write(publicKey.getEncoded());
            }

            try (OutputStream out = Files.newOutputStream(privateKeyPath)) {
                out.write(privateKey.getEncoded());
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        byte[] buffer = Files.readAllBytes(privateKeyPath);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey() throws Exception {
        byte[] buffer = Files.readAllBytes(publicKeyPath);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }
}
