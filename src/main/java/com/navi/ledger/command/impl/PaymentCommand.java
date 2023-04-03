package com.navi.ledger.command.impl;

import com.navi.ledger.command.BaseLedgerCommand;
import com.navi.ledger.command.CommandType;
import com.navi.ledger.service.ICommandExecutorService;
import lombok.Data;

/**
 * Implementation for PAYMENT command
 */
@Data
public class PaymentCommand extends BaseLedgerCommand<PaymentCommand> {

    private Integer lumpsumAmount;
    private Integer emiNumber;

    public PaymentCommand(final String[] inputs, final ICommandExecutorService<PaymentCommand> paymentService) {
        super(CommandType.PAYMENT, inputs, paymentService);
    }

    @Override
    public void extractValues() {
        this.bankName = inputs[1];
        this.borrowerName = inputs[2];
        this.lumpsumAmount = Integer.parseInt(inputs[3]);
        this.emiNumber = Integer.parseInt(inputs[4]);
    }

    @Override
    public void validateInputs() {
        super.validateCommonInputParams();
        try {
            Integer.parseInt(inputs[3]);
            Integer.parseInt(inputs[4]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid values types for PAYMENT command");
        }
    }
}
