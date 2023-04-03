package com.navi.ledger.dao;

import com.navi.ledger.model.Loan;
import com.navi.ledger.utils.Utils;
import com.navi.ledger.utils.exceptions.ElementNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanDaoTest {

    private LoanDao loanDao;

    @BeforeEach
    void setUp() {
        this.loanDao = new LoanDao();
    }

    @Test
    void test_loanNotFound() {
        Loan loan = Loan.builder()
                .bankName("IDIDI")
                .borrowerName("Dale")
                .build();
        String hash = Utils.getLoanHashKey(loan.getBankName(), loan.getBorrowerName());
        Exception e = assertThrows(ElementNotFoundException.class, () -> loanDao.getLoan(hash));
        assertEquals("Loan with key: " + hash + " not found", e.getMessage());

        assertFalse(loanDao.doesLoanExists(hash));
    }

    @Test
    void test_loanSaveAndGetFound() {
        Loan loan = Loan.builder()
                .bankName("IDIDI")
                .borrowerName("Dale")
                .build();
        String hash = Utils.getLoanHashKey(loan.getBankName(), loan.getBorrowerName());
        loanDao.save(loan);

        Loan expected = loanDao.getLoan(hash);
        assertEquals(expected, loan);
        assertTrue(loanDao.doesLoanExists(hash));
    }
}