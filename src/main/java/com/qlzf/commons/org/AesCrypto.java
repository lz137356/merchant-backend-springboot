package com.qlzf.commons.org;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * 兼容 com.qlzf.commons.org.AesCrypto 的 AES 加密工具类
 */
public class AesCrypto {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(String data, byte[] password) throws UnsupportedEncodingException {
        try {
            Key key = new SecretKeySpec(password, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    public static byte[] decrypt(byte[] data, byte[] password) {
        try {
            Key key = new SecretKeySpec(password, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}
