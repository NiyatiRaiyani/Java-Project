import java.util.Scanner;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "CHARUSAT Branch");

        System.out.println("==================================");
        System.out.println("             MINIBANK");
        System.out.println("==================================");
        System.out.println("Bank Name : " + bank.name());
        System.out.println("Branch    : " + bank.branch());

        boolean running = true;

        while (running) {

            System.out.println("\n----------- MENU -----------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.println("----------------------------");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            MenuOption option;

            switch (choice) {
                case 1 -> option = MenuOption.OPEN_ACCOUNT;
                case 2 -> option = MenuOption.DEPOSIT;
                case 3 -> option = MenuOption.WITHDRAW;
                case 4 -> option = MenuOption.TRANSFER;
                case 5 -> option = MenuOption.EXIT;
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            String message = switch (option) {
                case OPEN_ACCOUNT ->
                    "Open Account - To be implemented in a later lab.";
                case DEPOSIT ->
                    "Deposit - To be implemented in a later lab.";
                case WITHDRAW ->
                    "Withdraw - To be implemented in a later lab.";
                case TRANSFER ->
                    "Transfer - To be implemented in a later lab.";
                case EXIT -> {
                    running = false;
                    yield "Thank you for using MiniBank.";
                }
            };

            System.out.println(message);
        }

        sc.close();

        System.out.println("Goodbye!");
    }
}