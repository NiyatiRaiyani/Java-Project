package edu.minibank;

import edu.minibank.model.*;
import edu.minibank.service.BankService;
import edu.minibank.util.*;
import edu.minibank.exception.BankException;

import java.nio.file.Path;
import java.util.Scanner;

public class MiniBank {
    private static final BankInfo BANK = new BankInfo("Professional MiniBank", "Rajkot Main Branch");
    private static final Path DATA_FILE = Path.of("data", "accounts.dat");
    private static final Path LOG_FILE = Path.of("data", "transactions.log");

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println(BANK.name() + " | " + BANK.branch());
        System.out.println("Educational console banking application");
        System.out.println("======================================");
        BankService service = new BankService(DATA_FILE, LOG_FILE);
        try { service.load(); } catch (Exception e) { System.out.println("Starting with empty store: " + e.getMessage()); }

        try (Scanner in = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose: ");
                String choice = in.nextLine().trim();
                try {
                    MenuOption option = switch (choice) {
                        case "1" -> MenuOption.OPEN_ACCOUNT;
                        case "2" -> MenuOption.DEPOSIT;
                        case "3" -> MenuOption.WITHDRAW;
                        case "4" -> MenuOption.TRANSFER;
                        case "5" -> MenuOption.STATEMENT;
                        case "6" -> MenuOption.LIST_ACCOUNTS;
                        case "7" -> MenuOption.REPORT;
                        case "8" -> MenuOption.BATCH_DEMO;
                        case "0" -> MenuOption.EXIT;
                        default -> null;
                    };
                    if (option == null) { System.out.println("Invalid menu number. Try again."); continue; }
                    switch (option) {
                        case OPEN_ACCOUNT -> {
                            System.out.print("Owner name: "); String owner = in.nextLine().trim();
                            System.out.print("Email: "); String email = in.nextLine().trim();
                            System.out.print("Mobile (10 digits): "); String mobile = in.nextLine().trim();
                            System.out.print("Account type (SAVINGS/CURRENT/FIXED): ");
                            String type = in.nextLine().trim().toUpperCase();
                            System.out.print("Opening balance (whole rupees): "); long opening = Long.parseLong(in.nextLine().trim());
                            Account account = service.openAccount(owner, email, mobile, type, opening);
                            System.out.println("Account opened: " + account.getAccountNumber());
                        }
                        case DEPOSIT -> {
                            System.out.print("Account number: "); String id = in.nextLine().trim();
                            System.out.print("Amount: "); long amount = Long.parseLong(in.nextLine().trim());
                            service.deposit(id, amount); System.out.println("Deposit successful.");
                        }
                        case WITHDRAW -> {
                            System.out.print("Account number: "); String id = in.nextLine().trim();
                            System.out.print("Amount: "); long amount = Long.parseLong(in.nextLine().trim());
                            service.withdraw(id, amount); System.out.println("Withdrawal successful.");
                        }
                        case TRANSFER -> {
                            System.out.print("From account: "); String from = in.nextLine().trim();
                            System.out.print("To account: "); String to = in.nextLine().trim();
                            System.out.print("Amount: "); long amount = Long.parseLong(in.nextLine().trim());
                            service.transfer(from, to, amount); System.out.println("Transfer successful.");
                        }
                        case STATEMENT -> {
                            System.out.print("Account number: "); String id = in.nextLine().trim();
                            System.out.println(service.statement(id));
                        }
                        case LIST_ACCOUNTS -> service.listAccounts().forEach(System.out::println);
                        case REPORT -> System.out.println(service.generateReport());
                        case BATCH_DEMO -> { service.runBatchDemo(); System.out.println("Safe batch demo complete."); }
                        case EXIT -> { service.save(); running = false; System.out.println("Data saved. Thank you for using MiniBank."); }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid whole number.");
                } catch (BankException | IllegalArgumentException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                } finally {
                    // Practical 8: finally runs after each menu action.
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Open account  2. Deposit  3. Withdraw  4. Transfer");
        System.out.println("5. Statement     6. List accounts  7. Report  8. Batch demo  0. Save & exit");
    }
}
