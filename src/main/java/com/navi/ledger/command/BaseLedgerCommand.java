package com.navi.ledger.command;

import com.navi.ledger.service.ICommandExecutorService;
import lombok.Data;

/**
 * Base class for creating commands
 */
@Data
public abstract class BaseLedgerCommand<T> {

    public CommandType command;
    public ICommandExecutorService<T> commandExecutorService;
    public String[] inputs;

    public String bankName;
    public String borrowerName;

    public BaseLedgerCommand() {
    }

    public BaseLedgerCommand(final CommandType command, final String[] inputs, final ICommandExecutorService<T> commandExecutorService) {
        this.command = command;
        this.inputs = inputs;
        this.commandExecutorService = commandExecutorService;
    }

    /**
     * Validation across all commands for common data types
     */
    public void validateCommonInputParams() {
        if (this.command.getParamCounts() != inputs.length) {
            throw new IllegalArgumentException(String.format("Invalid arguments for %s command", command.getValue()));
        }

        if (inputs[1].length() == 0) {
            throw new IllegalArgumentException(String.format("Invalid bank name for %s command", command.getValue()));
        }

        if (inputs[2].length() == 0) {
            throw new IllegalArgumentException(String.format("Invalid borrower name for %s command", command.getValue()));
        }
    }

    public abstract void extractValues();

    public abstract void validateInputs();


    public void execute() {
        validateInputs();
        extractValues();
        commandExecutorService.executeCommand(this);

    }
}
