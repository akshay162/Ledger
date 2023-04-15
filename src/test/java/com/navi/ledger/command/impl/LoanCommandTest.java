package com.navi.ledger.command.impl;

import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.service.impl.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class LoanCommandTest {

    private ICommandExecutorService<LoanCommand> loanService;

    @BeforeEach
    void setUp() {
        LoanDao loanDao = mock(LoanDao.class);
        this.loanService = mock(LoanService.class);
    }

    @Test
    void test_failOnSizeOfArgumentInputs() {
        String[] args = new String[]{"LOAN", "MBI", "Dale", "10000", "2"};
        LoanCommand command = new LoanCommand(args, loanService);
        Exception e = assertThrows(IllegalArgumentException.class, command::execute);
        assertEquals("Invalid arguments for LOAN command", e.getMessage());
    }

    @Test
    void test_failOnWrongDataTypeOfArgumentInputs() {
        String[] args = new String[]{"LOAN", "MBI", "Dale", "10000", "2", "Hello"};
        LoanCommand command = new LoanCommand(args, loanService);
        Exception e = assertThrows(IllegalArgumentException.class, command::execute);
        assertEquals("Invalid values types for LOAN command", e.getMessage());
    }
}