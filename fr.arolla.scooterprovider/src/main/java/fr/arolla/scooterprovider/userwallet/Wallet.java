package fr.arolla.scooterprovider.userwallet;

import io.github.benas.randombeans.randomizers.range.BigDecimalRangeRandomizer;

import java.math.BigDecimal;

/**
 * Let's say this class is still experimental and we don't want to let use it normally
 * but we allow its use by reflection just for test.
 */
public final class Wallet {

    public static BigDecimal getUserCreditAmountById(String userId) {
        return BigDecimalRangeRandomizer.aNewBigDecimalRangeRandomizer(Double.valueOf(10), Double.valueOf(1000))
                .getRandomValue();
    }

    public static void updateUserCreditAmount(String userId, BigDecimal newAmount) {
        // Imagine there are instructions to update the client account with the new amount
    }
}
