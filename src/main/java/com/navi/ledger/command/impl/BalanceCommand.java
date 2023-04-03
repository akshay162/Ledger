package com.navi.ledger.command.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.CommandType;
import com.navi.ledger.service.ICommandExecutorService;
import lombok.Data;

/**
 * Implementation for BALANCE command
 */
@Data
public class BalanceCommand extends BaseLedgerCommand<BalanceCommand> {
    private Integer emiNumber;

    public BalanceCommand(final String[] inputs, final ICommandExecutorService<BalanceCommand> balanceService) {
        super(CommandType.BALANCE, inputs, balanceService);
    }

    @Override
    public void extractValues() {
        this.bankName = inputs[1];
        this.borrowerName = inputs[2];
        this.emiNumber = Integer.parseInt(inputs[3]);
    }

    @Override
    public void validateInputs() {
        super.validateCommonInputParams();
        if (this.command.getParamCounts() != inputs.length) {
            throw new IllegalArgumentException("Invalid arguments for BALANCE command");
        }

        try {
            Integer.parseInt(inputs[3]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid input types for BALANCE command");
        }
    }
}
