package com.ring.ringblogbackend.utils;

import java.security.MessageDigest;

// SHA-256加密
public class Encrypted {

    public static String encryptedPwd(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(pwd.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (Exception ignored) {
        }
        return pwd;
    }
}
