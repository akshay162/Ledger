package com.navi.ledger.service;

import com.navi.ledger.TestUtils;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.service.impl.BalanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BalanceServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private LoanDao loanDao;
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        this.loanDao = mock(LoanDao.class);
        this.balanceService = new BalanceService(loanDao);
        when(loanDao.getLoan(anyString())).thenReturn(TestUtils.getTestLoan());
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void test_processEMINumberZeroBalance() {

        balanceService.processBalance("IDIDI", "Dale", 0);
        //Uncomment to verify input by reading the console
        //assertEquals("IDIDI Dale 500 1\r\n", outContent.toString());
    }

    @Test
    void test_processLastEMIPostLumpsumBalance() {
        balanceService.processBalance("IDIDI", "Dale", 1);
        //Uncomment to verify input by reading the console
        //assertEquals("IDIDI Dale 1000 0\r\n", outContent.toString());

    }
}