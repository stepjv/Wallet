package com.wallet.util;

import java.security.SecureRandom;

public class WalletNumberGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String getNumber() {
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
