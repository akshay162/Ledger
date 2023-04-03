package com.navi.ledger.service.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.impl.PaymentCommand;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.utils.Utils;

import java.util.List;

/**
 * This service class hosts the business logic for processing the PAYMENT command
 */
public class PaymentService implements ICommandExecutorService<PaymentCommand> {

    private final LoanDao loanDao;

    public PaymentService(final LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    /**
     * This method process the PAYMENT and adjusts the remaining EMIs
     *
     * @param bankName      name of the bank
     * @param borrowerName  name of the borrower
     * @param lumpsumAmount lumpsum about pad along with the EMIs
     * @param emiNumber     Count of the EMIs along with lumpsum
     */
    public void processPayment(final String bankName, final String borrowerName, final Integer lumpsumAmount, final Integer emiNumber) {
        final String key = Utils.getLoanHashKey(bankName, borrowerName);
        Loan loan = loanDao.getLoan(key);
        Payment payment = Payment.builder()
                .amount(lumpsumAmount)
                .emiNumber(emiNumber)
                .status(Payment.Status.PAID)
                .type(Payment.Type.LUMPSUM)
                .build();

        processPayment(loan, payment);
    }

    /**
     * @param loan    Loan for which payment is received
     * @param payment Payment received for the loan
     */
    private void processPayment(Loan loan, final Payment payment) {
        if (payment.getEmiNumber() >= loan.getEmiRemaining()) {
            throw new IllegalArgumentException("Emi number in payment exceeds total number of remaining EMIs");
        }

        List<Payment> payments = loan.getPayments();
        // Adding lumpsum payment to the list of payments
        payments.add(payment.getEmiNumber(), payment);

        // Marking EMIs paid so far as PAID
        Integer emiAmountPaid = markEmiAsPaid(loan, payment.getEmiNumber());

        Integer remainingAmount = loan.getAmountRemaining() - emiAmountPaid - payment.getAmount();

        // Adjusting EMI counts after getting the lumpsum payments
        Integer remainingEmiCounts = adjustAndReturnRemainingEMICounts(loan, remainingAmount);
        payment.setRemainingEMICounts(remainingEmiCounts);

        loan.setAmountRemaining(remainingAmount);
        loan.setEmiRemaining(remainingEmiCounts);

        loanDao.save(loan);
    }

    /**
     * This method marks all the DUE payments till the @emiNumber as PAID
     *
     * @param loan      Loan to get the payments to mark as PAID
     * @param emiNumber EMI number till which all payments are PAID
     * @return
     */
    private Integer markEmiAsPaid(Loan loan, Integer emiNumber) {
        int emiStartNumber = getUnpaidEmiIndex(loan.getPayments());
        int paidAmount = 0;
        for (int i = emiStartNumber; i < emiNumber; i++) {

            Payment payment = loan.getPayments().get(i);
            payment.setStatus(Payment.Status.PAID);
            paidAmount += payment.getAmount();
        }
        return paidAmount;

    }

    /**
     * Since the payments are in order, this method returns the index of the first occurance of DUE payment
     *
     * @param payments List of Payments
     * @return Return the index of unpaid payment
     */
    private int getUnpaidEmiIndex(List<Payment> payments) {
        Payment unpaidPayment = payments.stream().filter(payment -> Payment.Status.DUE.equals(payment.getStatus())).findFirst()
                .orElseThrow(() -> new IllegalStateException("All EMIs are paid"));
        return payments.indexOf(unpaidPayment);
    }

    /**
     * This method adjust the EMI count after the EMI and Lumpsum payments
     *
     * @param loan            Loan for the payment
     * @param remainingAmount Remaining amount after EMI and Lumpsum payments
     * @return Returns the remaining EMI accounts post adjustment
     */
    private Integer adjustAndReturnRemainingEMICounts(Loan loan, Integer remainingAmount) {
        int emiSum = 0;
        int emiCount = 0;
        int startEmiIndex = getUnpaidEmiIndex(loan.getPayments());
        int index = startEmiIndex;

        while (emiSum < remainingAmount) {
            Payment payment = loan.getPayments().get(index);

            if (remainingAmount - emiSum <= payment.getAmount()) {
                payment.setAmount(remainingAmount - emiSum);
                loan.getPayments().subList(index + 1, loan.getPayments().size()).clear();
            }
            emiSum += payment.getAmount();
            emiCount++;
            index++;

        }
        refreshEmiCounts(loan.getPayments(), startEmiIndex, index);
        return emiCount;
    }

    /**
     * Util method to refresh the EMI payment counts and EMI left after the EMI
     */
    private void refreshEmiCounts(List<Payment> payments, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            Payment payment = payments.get(i);
            payment.setEmiNumber(i);
            payment.setRemainingEMICounts(endIndex - i - 1);
        }
    }

    @Override
    public void executeCommand(BaseLedgerCommand command) {
        PaymentCommand paymentCommand = (PaymentCommand) command;
        processPayment(paymentCommand.getBankName(),
                paymentCommand.getBorrowerName(),
                paymentCommand.getLumpsumAmount(),
                paymentCommand.getEmiNumber());
    }
}