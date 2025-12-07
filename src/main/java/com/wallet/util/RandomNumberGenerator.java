package com.wallet.util;

import java.security.SecureRandom;

// покрыть unit tests c помощью ии
public class RandomNumberGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String MIXED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();

    public static String getTransactionNumber() {
        return getRandomString(MIXED_STRING, 16);
    }

    public static String getWalletNumber() {
        return getRandomString(LETTERS, 2) +
                getRandomString(DIGITS, 2) +
                getRandomString(LETTERS, 2) +
                getRandomString(DIGITS, 4);
    }

    private static String getRandomString(String characters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
