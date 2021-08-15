package top.lizec.smartreview.utils;

import java.io.FileOutputStream;
import java.io.IOException;
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
            log.info("没有检测到Key文件, 重新Key文件");
            createKeyPair();
        }
        log.info("Key加载完毕");
    }

    private static boolean hasGenerateKey() {
        Path publicKeyFile = Paths.get("RSA_PUBLIC");
        Path privateKeyFile = Paths.get("RSA_PRIVATE");
        return Files.exists(publicKeyFile) && Files.exists(privateKeyFile);
    }


    private static void createKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(4096, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

            try (FileOutputStream out = new FileOutputStream("RSA_PUBLIC")) {
                out.write(publicKey.getEncoded());
            }

            try (FileOutputStream out = new FileOutputStream("RSA_PRIVATE")) {
                out.write(privateKey.getEncoded());
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        Path file = Paths.get("RSA_PRIVATE");
        byte[] buffer = Files.readAllBytes(file);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey() throws Exception {
        Path file = Paths.get("RSA_PUBLIC");
        byte[] buffer = Files.readAllBytes(file);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }
}
