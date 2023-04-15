package com.navi.ledger;

import com.navi.ledger.processor.CommandProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandProcessor commandProcessor = new CommandProcessor();

        try {

            FileInputStream fis = new FileInputStream(getAbsoluteFilePath(args));
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

    public static String getAbsoluteFilePath(String[] args) throws FileNotFoundException {

//        The file to be opened for reading,
//        if run through cli, pass the file path as argument and uncomment the below line
//        FileInputStream fis = new FileInputStream(args[0]);

        // uncomment the below two lines if file is being passed as an argument while running through cli
        String testInputFilePath = "sample_input/input1.txt";
        String absolutePath = new File(testInputFilePath).getAbsolutePath();

        return absolutePath;
    }
}
