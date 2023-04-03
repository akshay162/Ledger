package com.navi.ledger;

import com.navi.ledger.processor.CommandProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandProcessor commandProcessor = new CommandProcessor();

        try {
            // the file to be opened for reading
//            FileInputStream fis = new FileInputStream(args[0]);

            String testInputFilePath = "sample_input/input1.txt";
            String absolutePath = new File(testInputFilePath).getAbsolutePath();
            FileInputStream fis = new FileInputStream(absolutePath);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String commandLine = sc.nextLine();
                commandProcessor.processCommand(commandLine.split(" "));
            }
            sc.close(); // closes the scanner
        } catch (IOException e) {
            System.out.println("Error while processing commands from file");
        }
    }
}
