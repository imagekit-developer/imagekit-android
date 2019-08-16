package com.imagekit.android;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Locale;

public class SignatureUtil {
    private static final String PRIVATE_CLIENT_KEY = "dI7GuFhK/BrmAAzKOcm3JXdnJjc=";

    public static String createSignature(String filename, Long timestamp, String apiKey) {
        try {
            String signature =
                    String.format(Locale.getDefault(), "apikey=%s&filename=%s&timestamp=%d", apiKey, filename, timestamp);
            return hmacSha1(signature, PRIVATE_CLIENT_KEY);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hmacSha1(String value, String key)
            throws NoSuchAlgorithmException,
            InvalidKeyException {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = Mac.getInstance(type);
        mac.init(secret);
        byte[] bytes = mac.doFinal(value.getBytes());
        return bytesToHex(bytes);
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static final String ALGORITHM = "HmacSHA1";

    public static String sign(String content) {
        String encoded;
        try {
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
