package com.navi.ledger.service;

import com.navi.ledger.TestUtils;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;
import com.navi.ledger.service.impl.PaymentService;
import com.navi.ledger.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// TODO : added similar unit tests here
class PaymentServiceTest {

    private LoanDao loanDao;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        this.loanDao = new LoanDao();
        this.paymentService = new PaymentService(loanDao);
    }

    @Test
    void test_paymentCreationAndAdjustment() {
        loanDao.save(TestUtils.getTestLoanWithoutLumpsum());

        String bankName = "IDIDI";
        String borrowerName = "Dale";
        Integer lumpsumAmount = 500;
        Integer emiNumber = 0;

        paymentService.processPayment(bankName, borrowerName, lumpsumAmount, emiNumber);

        Loan createdLoan = loanDao.getLoan(Utils.getLoanHashKey(bankName, borrowerName));

        assertEquals(createdLoan.getBankName(), bankName);
        assertEquals(createdLoan.getBorrowerName(), borrowerName);
        assertNotNull(createdLoan.getPayments());
        assertEquals(createdLoan.getPayments().size(), 2);

        assertEquals(createdLoan.getPayments().get(0).getAmount(), 500);
        assertEquals(createdLoan.getPayments().get(0).getStatus(), Payment.Status.PAID);
        assertEquals(createdLoan.getPayments().get(0).getType(), Payment.Type.LUMPSUM);
        assertEquals(createdLoan.getPayments().get(0).getEmiNumber(), 0);


        assertEquals(createdLoan.getPayments().get(1).getAmount(), 500);
        assertEquals(createdLoan.getPayments().get(1).getStatus(), Payment.Status.DUE);
        assertEquals(createdLoan.getPayments().get(1).getType(), Payment.Type.EMI);
        assertEquals(createdLoan.getPayments().get(1).getEmiNumber(), 1);
        assertEquals(createdLoan.getPayments().get(1).getRemainingEMICounts(), 0);
    }
}