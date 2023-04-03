package com.navi.ledger.service;

import com.navi.ledger.command.BaseLedgerCommand;

public interface ICommandExecutorService<T> {

    /*
     * Service interface to execute the command
     */
    void executeCommand(BaseLedgerCommand command);
}
