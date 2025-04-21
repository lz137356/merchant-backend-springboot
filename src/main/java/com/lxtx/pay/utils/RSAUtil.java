package com.lxtx.pay.utils;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class RSAUtil {

    // 生成密钥对，并返回 fastjson JSONObject
    public static JSONObject generateKeyPairJson(){
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (Throwable e) {
            return null;
        }
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        JSONObject json = new JSONObject();
        json.put("publicKey", publicKey);
        json.put("privateKey", privateKey);

        return json;
    }

    // 公钥加密
    public static String encrypt(String plaintext, String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        PublicKey pubKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(keyBytes));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 私钥解密
    public static String decrypt(String encryptedBase64, String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedBase64));
        return new String(decryptedBytes, "UTF-8");
    }

    // 测试方法
    public static void main(String[] args) throws Exception {
        // 生成密钥对并存入 fastjson 对象
        JSONObject keyJson = generateKeyPairJson();
        String publicKey = keyJson.getString("publicKey");
        String privateKey = keyJson.getString("privateKey");

        System.out.println("🔐 公钥: " + publicKey);
        System.out.println("🔏 私钥: " + privateKey);

        // 明文
        String text = "amount=10000&currency=INR&merchant_id=202366100&notify_url=https://www.google.com&order_id=202366100160101&pay_type=1&return_url=https://www.google.com&key=keyValue";
        System.out.println("📤 原文: " + text);

        // 加密
        String encrypted = encrypt(text, publicKey);
        System.out.println("🔒 加密后: " + encrypted);

        // 解密
        String decrypted = decrypt(encrypted, privateKey);
        System.out.println("🔓 解密后: " + decrypted);
    }
}