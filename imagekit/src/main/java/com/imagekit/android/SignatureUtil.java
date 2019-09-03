package com.imagekit.android;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureUtil {
    private static final String PRIVATE_CLIENT_KEY = "dI7GuFhK/BrmAAzKOcm3JXdnJjc=";

    private static final String ALGORITHM = "HmacSHA1";

    public static String sign(String content, String expire) {
        String encoded;
        try {
            content = content.concat(expire);
            SecretKeySpec signingKey = new SecretKeySpec(PRIVATE_CLIENT_KEY.getBytes(), ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(signingKey);
            encoded = toHexString(mac.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot create signature.", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Cannot create signature.", e);
        }
        return encoded;
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
