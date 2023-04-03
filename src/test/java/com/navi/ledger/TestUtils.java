package com.navi.ledger;

import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static Loan getTestLoan() {
        Loan loan = Loan.builder()
                .borrowerName("Dale")
                .bankName("IDIDI")
                .amountRemaining(1000)
                .emiRemaining(1)
                .build();

        Payment firstLumpsum = Payment.builder()
                .type(Payment.Type.LUMPSUM)
                .status(Payment.Status.PAID)
                .amount(500)
                .emiNumber(0)
                .remainingEMICounts(1)
                .build();

        Payment lastEMI = Payment.builder()
                .type(Payment.Type.EMI)
                .status(Payment.Status.DUE)
                .amount(500)
                .emiNumber(1)
                .remainingEMICounts(0)
                .build();

        List<Payment> payments = new ArrayList<>();
        payments.add(firstLumpsum);
        payments.add(lastEMI);

        loan.setPayments(payments);
        return loan;
    }

    public static Loan getTestLoanWithoutLumpsum() {
        Loan loan = Loan.builder()
                .borrowerName("Dale")
                .bankName("IDIDI")
                .amountRemaining(1000)
                .emiRemaining(1)
                .build();

        Payment lastEMI = Payment.builder()
                .type(Payment.Type.EMI)
                .status(Payment.Status.DUE)
                .amount(1000)
                .emiNumber(1)
                .remainingEMICounts(0)
                .build();

        List<Payment> payments = new ArrayList<>();
        payments.add(lastEMI);

        loan.setPayments(payments);
        return loan;
    }

}
