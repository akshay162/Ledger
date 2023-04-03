package com.navi.ledger.utils;

public class Utils {

    /*
     * Method generated a unique hash key for each loan created in the ledger.
     */
    public static String getLoanHashKey(final String bankName, final String borrowerName) {
        return String.format(bankName + "_" + borrowerName);
    }
}
