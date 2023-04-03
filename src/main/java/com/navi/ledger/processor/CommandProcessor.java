package com.navi.ledger.processor;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.CommandType;
import com.navi.ledger.command.impl.BalanceCommand;
import com.navi.ledger.command.impl.LoanCommand;
import com.navi.ledger.command.impl.PaymentCommand;
import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.service.impl.BalanceService;
import com.navi.ledger.service.impl.LoanService;
import com.navi.ledger.service.impl.PaymentService;

public class CommandProcessor {

    private final ICommandExecutorService<BalanceCommand> balanceService;
    private final ICommandExecutorService<PaymentCommand> paymentService;
    private final ICommandExecutorService<LoanCommand> loanService;

    public CommandProcessor() {
        LoanDao loanDao = new LoanDao();
        this.balanceService = new BalanceService(loanDao);
        this.loanService = new LoanService(loanDao);
        this.paymentService = new PaymentService(loanDao);
    }

    /**
     * This method matches and process inputs based on the Commands available
     *
     * @param inputs Array of string arguments read from the command line
     */
    public void processCommand(final String[] inputs) {
        String command = inputs[0];
        BaseLedgerCommand ledgerCommand = null;
        switch (CommandType.valueOf(command)) {
            case BALANCE:
                ledgerCommand = new BalanceCommand(inputs, balanceService);
                break;
            case LOAN:
                ledgerCommand = new LoanCommand(inputs, loanService);
                break;
            case PAYMENT:
                ledgerCommand = new PaymentCommand(inputs, paymentService);
                break;
            default:
                throw new IllegalArgumentException(String.format("Command %s is not valid", command));
        }

        ledgerCommand.execute();
    }
}
