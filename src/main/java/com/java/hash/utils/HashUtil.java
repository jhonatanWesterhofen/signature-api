package com.java.hash.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 *
 * @author Jhonatan
 */
public class HashUtil {

    public static String hashSHA512(InputStream input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] buffer = new byte[8192];
        int read;
        while ((read = input.read(buffer)) != -1) {
            digest.update(buffer, 0, read);
        }
        byte[] hashed = digest.digest();
        return bytesToHex(hashed);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int nRead;
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    public static String calculateSHA512Hex(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(data);
        return org.bouncycastle.util.encoders.Hex.toHexString(digest);
    }

}