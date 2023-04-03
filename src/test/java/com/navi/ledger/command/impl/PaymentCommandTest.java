package com.navi.ledger.command.impl;

import com.navi.ledger.dao.LoanDao;
import com.navi.ledger.service.ICommandExecutorService;
import com.navi.ledger.service.impl.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class PaymentCommandTest {

    private ICommandExecutorService<PaymentCommand> paymentService;

    @BeforeEach
    void setUp() {
        LoanDao loanDao = mock(LoanDao.class);
        this.paymentService = mock(PaymentService.class);
    }


    @Test
    void test_failOnSizeOfArgumentInputs() {
        String[] args = new String[]{"PAYMENT", "MBI", "Dale"};
        PaymentCommand paymentCommand = new PaymentCommand(args, paymentService);
        Exception e = assertThrows(IllegalArgumentException.class, paymentCommand::execute);
        assertEquals("Invalid arguments for PAYMENT command", e.getMessage());
    }

    @Test
    void test_failOnWrongDataTypeOfArgumentInputs() {
        String[] args = new String[]{"INVALID", "MBI", "Dale", "Hello", "4"};
        PaymentCommand paymentCommand = new PaymentCommand(args, paymentService);
        Exception e = assertThrows(IllegalArgumentException.class, paymentCommand::execute);
        assertEquals("Invalid values types for PAYMENT command", e.getMessage());
    }

}