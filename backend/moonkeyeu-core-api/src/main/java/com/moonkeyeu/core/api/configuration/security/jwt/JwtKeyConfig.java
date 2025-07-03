package com.moonkeyeu.core.api.configuration.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@Slf4j
public class JwtKeyConfig {
    @Value("${jwt.public.access.key}")
    private String accessPublicKeyPath;

    @Value("${jwt.public.refresh.key}")
    private String refreshPublicKeyPath;

    @Value("${jwt.private.access.key}")
    private String accessPrivateKeyPath;

    @Value("${jwt.private.refresh.key}")
    private String refreshPrivateKeyPath;

    @Bean
    public RSAPublicKey accessTokenPublicKey() throws Exception {
        String key = loadKeys(accessPublicKeyPath);
        return getPublicKey(key);
    }

    @Bean
    public RSAPublicKey refreshTokenPublicKey() throws Exception {
        String key = loadKeys(refreshPublicKeyPath);
        return getPublicKey(key);
    }

    @Bean
    public RSAPrivateKey accessTokenPrivateKey() throws Exception {
        String key = loadKeys(accessPrivateKeyPath);
        return getPrivateKey(key);
    }

    @Bean
    public RSAPrivateKey refreshTokenPrivateKey() throws Exception {
        String key = loadKeys(refreshPrivateKeyPath);
        return getPrivateKey(key);
    }
    private String loadKeys(String path) throws Exception {
        try (InputStream inputStream = new FileInputStream(path)) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e){
            throw new IllegalStateException("Failed to load key file at path: " + path, e);
        }
    }
    private RSAPrivateKey getPrivateKey(String pem) throws Exception {
        String privateKeyContent = removeBeginEnd(pem);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent)));
    }

    private RSAPublicKey getPublicKey(String pem) throws Exception {
        String publicKeyContent = removeBeginEnd(pem);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent)));
    }

    private String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)-----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }
}
