package com.navi.ledger.service;

import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;
import com.navi.ledger.service.impl.LoanService;
import com.navi.ledger.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanServiceTest {

    private LoanDao loanDao;
    private LoanService loanService;
    @BeforeEach
    void setUp() {
        this.loanDao = new LoanDao();
        this.loanService = new LoanService(loanDao);
    }

    @Test
    void test_loanCreation() {
        String bankName = "IDIDI";
        String borrowerName = "Dale";
        Integer principle = 1200;
        Integer rate = 1;
        Integer time = 1;

        loanService.processLoan(bankName, borrowerName, principle, time, rate);

        Loan createdLoan = loanDao.getLoan(Utils.getLoanHashKey(bankName, borrowerName));
        assertEquals(createdLoan.getBankName(), bankName);
        assertEquals(createdLoan.getBorrowerName(), borrowerName);
        assertEquals(createdLoan.getPrinciple(), principle);
        assertEquals(createdLoan.getRateOfInterest(), rate);
        assertEquals(createdLoan.getTimeInYears(), time);
        assertNotNull(createdLoan.getPayments());
        assertEquals(createdLoan.getEmiRemaining(), 12);
        assertEquals(createdLoan.getInterest(), 12);


        assertEquals(createdLoan.getPayments().get(0).getAmount(), 101);
        assertEquals(createdLoan.getPayments().get(0).getStatus(), Payment.Status.DUE);
        assertEquals(createdLoan.getPayments().get(0).getType(), Payment.Type.EMI);
        assertEquals(createdLoan.getPayments().get(0).getEmiNumber(), 1);


        assertEquals(createdLoan.getPayments().get(5).getAmount(), 101);
        assertEquals(createdLoan.getPayments().get(5).getStatus(), Payment.Status.DUE);
        assertEquals(createdLoan.getPayments().get(5).getType(), Payment.Type.EMI);
        assertEquals(createdLoan.getPayments().get(5).getEmiNumber(), 6);
        assertEquals(createdLoan.getPayments().get(5).getRemainingEMICounts(), 6);
    }
}