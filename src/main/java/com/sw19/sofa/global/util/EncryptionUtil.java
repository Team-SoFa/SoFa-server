package com.sw19.sofa.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static String ALGORITHM;
    private static String SECRET_KEY;

    @Value("${encryption.algorithm}")
    private void setAlgorithm(String algorithm){
        ALGORITHM = algorithm;
    }
    @Value("${encryption.secretKey}")
    private void setSecretKey(String secretkey){
        SECRET_KEY = secretkey;
    }

    public static String encrypt(Long input)  {
        if (input == null) {
            return null;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(String.valueOf(input).getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().encodeToString(encryptedBytes);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public static Long decrypt(String input) {
        if (input == null) {
            return null;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

            byte[] decodedValue = Base64.getUrlDecoder().decode(input);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(decodedValue);
            return Long.parseLong(new String(decryptedBytes, StandardCharsets.UTF_8));

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
