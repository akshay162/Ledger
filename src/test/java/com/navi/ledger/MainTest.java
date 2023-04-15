package com.navi.ledger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void test_testOverallFunctionality() {
        String testInputFilePath = "src/inputs.txt";
        String absolutePath = new File(testInputFilePath).getAbsolutePath();
        String[] args = new String[]{absolutePath};
        Assertions.assertDoesNotThrow(() -> Main.main(args));
        //Uncomment to verify input by reading the console
//        assertEquals("MBI Dale 1000 40\r\nMBI Dale 3250 22\r\n", outContent.toString());
    }
}