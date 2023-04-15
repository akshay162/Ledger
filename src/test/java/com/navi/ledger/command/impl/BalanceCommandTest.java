package com.navi.ledger.command.impl;

import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.service.impl.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class BalanceCommandTest {

    private ICommandExecutorService<BalanceCommand> balanceService;

    @BeforeEach
    void setUp() {
        LoanDao loanDao = mock(LoanDao.class);
        this.balanceService = mock(BalanceService.class);
    }

    @Test
    void test_failOnSizeOfArgumentInputs() {
        String[] args = new String[]{"BALANCE", "MBI", "Dale"};
        BalanceCommand command = new BalanceCommand(args, balanceService);
        Exception e = assertThrows(IllegalArgumentException.class, command::execute);
        assertEquals("Invalid arguments for BALANCE command", e.getMessage());
    }

    @Test
    void test_failOnWrongDataTypeOfArgumentInputs() {
        String[] args = new String[]{"BALANCE", "MBI", "Dale", "Hello"};
        BalanceCommand command = new BalanceCommand(args, balanceService);
        Exception e = assertThrows(IllegalArgumentException.class, command::execute);
        assertEquals("Invalid input types for BALANCE command", e.getMessage());
    }
}