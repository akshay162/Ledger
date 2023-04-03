package com.navi.ledger.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandProcessorTest {
    private CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        this.commandProcessor = new CommandProcessor();
    }

    @Test
    void test_validInputCommand() {
        String[] args = new String[]{"LOAN", "MBI", "Dale", "5000", "4", "5"};
        Assertions.assertDoesNotThrow(() -> commandProcessor.processCommand(args));
    }

    @Test
    void test_throwsIllegalArgumentExceptionOnUnknownCommand() {
        String[] args = new String[]{"INVALID", "MBI", "Dale", "5000", "4", "5"};
        assertThrows(IllegalArgumentException.class, () -> commandProcessor.processCommand(args));
    }


}