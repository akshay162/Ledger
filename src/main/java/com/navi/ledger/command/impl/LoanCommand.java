package com.navi.ledger.command.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.CommandType;
import com.navi.ledger.service.ICommandExecutorService;
import lombok.Data;

/**
 * Implementation for LOAN command
 */
@Data
public class LoanCommand extends BaseLedgerCommand<LoanCommand> {

    private Integer principal;
    private Integer timeInYears;
    private Integer rateOfInterest;

    public LoanCommand(String[] inputs, final ICommandExecutorService<LoanCommand> loanService) {
        super(CommandType.LOAN, inputs, loanService);

    }

    @Override
    public void extractValues() {
        this.bankName = inputs[1];
        this.borrowerName = inputs[2];
        this.principal = Integer.parseInt(inputs[3]);
        this.timeInYears = Integer.parseInt(inputs[4]);
        this.rateOfInterest = Integer.parseInt(inputs[5]);
    }

    @Override
    public void validateInputs() {
        super.validateCommonInputParams();
        try {
            Integer.parseInt(inputs[3]);
            Integer.parseInt(inputs[4]);
            Integer.parseInt(inputs[5]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid values types for LOAN command");
        }
    }
}
