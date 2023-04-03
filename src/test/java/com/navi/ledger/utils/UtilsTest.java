package com.navi.ledger.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UtilsTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void test_validHashKey() {
        String bankName = UUID.randomUUID().toString();
        String borrowerName = UUID.randomUUID().toString();
        String hashKey = Utils.getLoanHashKey(bankName, borrowerName);

        Assertions.assertEquals(bankName + "_" + borrowerName, hashKey);
    }
}