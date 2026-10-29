import exception.BankException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MiniBank {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();

        int choice;

        do {
            System.out.println("\n===== MINIBANK =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Account Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Generate Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {

                    case 1 -> {
                        sc.nextLine();
                        System.out.print("Account Number: ");
                        String id = sc.nextLine();

                        System.out.print("Owner Name: ");
                        String name = sc.nextLine();

                        System.out.print("Initial Balance: ");
                        long balance = sc.nextLong();

                        System.out.print("Minimum Balance: ");
                        long minimum = sc.nextLong();

                        service.openAccount(
                                new SavingsAccount(id, name, balance, minimum)
                        );

                        System.out.println("Account opened successfully!");
                    }

                    case 2 -> {
                        System.out.print("Account Number: ");
                        String id = sc.next();
                        System.out.print("Amount to deposit: ");
                        long amount = sc.nextLong();
                        service.deposit(id, amount);
                        System.out.println("Deposit successful!");
                    }

                    case 3 -> {
                        System.out.print("Account Number: ");
                        String id = sc.next();
                        System.out.print("Amount to withdraw: ");
                        long amount = sc.nextLong();
                        service.withdraw(id, amount);
                        System.out.println("Withdrawal successful!");
                    }

                    case 4 -> {
                        System.out.print("From Account: ");
                        String from = sc.next();
                        System.out.print("To Account: ");
                        String to = sc.next();
                        System.out.print("Amount: ");
                        long amount = sc.nextLong();
                        service.transfer(from, to, amount);
                        System.out.println("Transfer completed!");
                    }

                    case 5 -> {
                        System.out.print("Account Number: ");
                        String id = sc.next();
                        System.out.println(service.statement(id));
                    }

                    case 6 -> {
                        for (Account account : service.listAccounts()) {
                            System.out.println(account);
                        }
                    }
                        case 7 -> {
                            Path logsFolder = Path.of("data", "logs");
                            Path reportFile = Path.of("data", "report.txt");
                            
                            Files.createDirectories(logsFolder);
                            
                            ReportGenerator.generate(logsFolder, reportFile);
                            System.out.println("Report generated successfully.");
                    }

                    case 0 -> {
                        service.saveAccounts();
                        System.out.println("Thank you for using MiniBank!");
                    }

                    default -> System.out.println("Invalid choice!");
                }

            } catch (BankException | RuntimeException | IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);

        sc.close();
    }
}
