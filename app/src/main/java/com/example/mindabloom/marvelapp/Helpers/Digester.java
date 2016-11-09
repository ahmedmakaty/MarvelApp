package com.example.mindabloom.marvelapp.Helpers;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class Digester {
    public static String md5Digest(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
