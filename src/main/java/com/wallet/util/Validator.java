package com.wallet.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Validator {

    private static final BigDecimal BALANCE_MAX_VALUE = new BigDecimal("99,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999.99");

    public static BigDecimal safeSumOfDecimal(BigDecimal a, BigDecimal b)
            throws ArithmeticException {

        BigDecimal sum = a.add(b);

        if (sum.compareTo(BALANCE_MAX_VALUE) > 0) {
            throw new ArithmeticException("Значение превышает максимально допустимое");
        }

        if (sum.signum() != -1) {
            throw new ArithmeticException("Недостаточно средств");
        }

        return sum;
    }
}
