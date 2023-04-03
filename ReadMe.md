# The Ledger Co

This repo contains the solution for the challenge named "The Ledger Co"

## Design Pattens Used

## 1. [Command Pattern](https://refactoring.guru/design-patterns/command)
Command is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. This transformation lets you pass requests as a method arguments, delay or queue a request’s execution, and support undoable operations.
There are currently 3 types of commands : LOAN, PAYMENT, BALANCE

 `Commands.java` is and Enum that stores all the command ids and size of command

`BaseLedgerCommand` is the abstract class containing abstract method `execute()` which is how the commands are executed.
All commands have a `ia-a` property and inherit this base class.

`CommandProcessor` acts as a command orchestrator and executes those command

`ICommandExecutorService` uses a generic interface to create a contract between the command and the service which executes it.

## 2. [Builder Pattern](https://refactoring.guru/design-patterns/builder)

Builder is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code.

The code uses `Lombok` to use annotation to create builder and generate getters and setters
Classes using Builder Pattern are:
1. Loan.java
2. Payment.java
3. BaseLedgerCommand.java


### Build Pre-requisites
* Java 1.8
* Maven
* Lombok plugin for Intellij and enable Annotation Processor

### How to test the code
`MainTest.java` run overall test cases by reading the commands defined in the `input.txt` test file.

 ### How to execute the unit tests

 `mvn clean test` will execute the unit test cases.
