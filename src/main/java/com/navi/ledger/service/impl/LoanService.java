package com.navi.ledger.service.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.impl.LoanCommand;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This service class hosts the business logic for processing the LOAN command
 */
public class LoanService implements ICommandExecutorService<LoanCommand> {

    final private LoanDao loanDao;

    public LoanService(final LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    /**
     * @param bankName       name of the bank for the loan
     * @param borrowerName   name of the borrower
     * @param principal      principal amount for the loan
     * @param timeInYears    total time in years
     * @param rateOfInterest yearly rate of interest
     *                       <p>
     *                       This method create a new Loan entity in the loan ledger and generated the EMI payments based on amount and time
     */
    public void processLoan(final String bankName, final String borrowerName, final Integer principal, final Integer timeInYears, final Integer rateOfInterest) {
        String key = Utils.getLoanHashKey(bankName, borrowerName);
        if (loanDao.doesLoanExists(key)) {
            throw new IllegalArgumentException(String.format("Loan for borrower %s from bank %s already exists", borrowerName, bankName));
        }

        Double interest = Math.ceil((principal * rateOfInterest * timeInYears) / 100.0);
        double amount = principal + interest;

        // Ceiling EMI amount to the nearest integer
        int emiAmount = (int) Math.ceil(amount / (timeInYears * 12));
        // Ceiling EMI count
        Integer emiCount = (int) Math.ceil(amount / emiAmount);

        // Creating all EMI payments in order of payments except the last EMI payment
        List<Payment> paymentList = generateLoanPayments(emiAmount, emiCount);

        // Ceiling the monthly EMI amount might result in paying more money in each EMI.
        // Adjusting this in the last EMI payment.
        Integer finalEmiAmount = (int) amount - (emiAmount * (emiCount - 1));
        Payment lastEmi = Payment.builder()
                .amount(finalEmiAmount)
                .remainingEMICounts(0)
                .emiNumber(emiCount)
                .status(Payment.Status.DUE)
                .type(Payment.Type.EMI)
                .build();

        paymentList.add(lastEmi);

        Loan loan = Loan.builder()
                .bankName(bankName)
                .borrowerName(borrowerName)
                .amountRemaining((int) amount)
                .interest(interest.intValue())
                .emiRemaining(emiCount)
                .principle(principal)
                .timeInYears(timeInYears)
                .rateOfInterest(rateOfInterest)
                .payments(paymentList)
                .build();

        loanDao.save(loan);
    }

    /**
     * @param emiAmount EMI amount for each monthly installment
     * @param count     Total EMI count
     * @return Returns a list of Payments created for the Loan. This does not add the last EMI payment
     * While creating the loan, the status of EMIs are marked as DUE.
     */
    private List<Payment> generateLoanPayments(final Integer emiAmount, final Integer count) {
        List<Payment> paymentList = new ArrayList<>();

        for (int i = 1; i < count; i++) {
            Payment payment = Payment.builder()
                    .amount(emiAmount)
                    .emiNumber(i)
                    .remainingEMICounts(count - i)
                    .status(Payment.Status.DUE)
                    .type(Payment.Type.EMI)
                    .build();

            paymentList.add(payment);
        }
        return paymentList;
    }

    @Override
    public void executeCommand(BaseLedgerCommand command) {
        LoanCommand loanCommand = (LoanCommand) command;
        processLoan(loanCommand.getBankName(),
                loanCommand.getBorrowerName(),
                loanCommand.getPrincipal(),
                loanCommand.getTimeInYears(),
                loanCommand.getRateOfInterest());
    }
}
