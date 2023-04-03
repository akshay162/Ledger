package com.navi.ledger.service.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.impl.BalanceCommand;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.model.Loan;
import com.navi.ledger.model.Payment;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.utils.Utils;

/**
 * This service class hosts the business logic for processing the BALANCE command
 */
public class BalanceService implements ICommandExecutorService<BalanceCommand> {

    private final LoanDao loanDao;

    public BalanceService(final LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    /**
     * It prints the total amount paid by the borrower, including all the Lump Sum amounts paid including that EMI number, and the no of EMIs remaining.
     *
     * @param bankName     name of the bank
     * @param borrowerName name of the borrower
     * @param emiNumber    emi number to calculate amount paid
     */
    public void processBalance(final String bankName, final String borrowerName, final Integer emiNumber) {
        String key = Utils.getLoanHashKey(bankName, borrowerName);

        Loan loan = loanDao.getLoan(key);
        int amountPaid = 0;
        Payment emiPayment = null;

        // Calculating the amount paid till the input emi number
        for (Payment payment : loan.getPayments()) {
            if (payment.getEmiNumber() <= emiNumber) {
                emiPayment = payment;
                amountPaid += payment.getAmount();
            }

        }

        int remainingEmiCount = emiPayment == null ? loan.getPayments().get(0).getRemainingEMICounts() + 1 : emiPayment.getRemainingEMICounts();
        printBalance(bankName, borrowerName, amountPaid, remainingEmiCount);
    }

    /**
     * @param bankName     name of the bank
     * @param borrowerName name of the borrower
     * @param amountPaid   total amount till emi number
     * @param emiLeft      number of EMIs left to pay the final amount
     */
    private void printBalance(final String bankName, final String borrowerName, final Integer amountPaid, final Integer emiLeft) {
        System.out.printf("%s %s %s %s%n", bankName, borrowerName, amountPaid, emiLeft);
    }

    @Override
    public void executeCommand(BaseLedgerCommand command) {
        BalanceCommand balanceCommand = (BalanceCommand) command;
        processBalance(balanceCommand.getBankName(),
                balanceCommand.getBorrowerName(),
                balanceCommand.getEmiNumber());
    }
}
