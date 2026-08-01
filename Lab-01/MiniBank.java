import java.util.Scanner;

record BankInfo(String name, String branch) {}

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

        BankInfo bank = new BankInfo("BARODA Bank", "GONDAL");

        // Creating 3 Accounts
        Account[] accounts = new Account[3];

        accounts[0] = new Account("Niyati", 5000);
        accounts[1] = new Account("Priya");
        accounts[2] = new Account("Krina", 10000);

        // Transactions
        accounts[0].deposit(2000);
        accounts[1].deposit(3000);

        accounts[0].withdraw(1000);
        accounts[2].withdraw(5000);

        // Display Accounts
        System.out.println("=================================");
        System.out.println("        MINI BANK");
        System.out.println("=================================");
        System.out.println("Bank Name : " + bank.name());
        System.out.println("Branch    : " + bank.branch());

        System.out.println("\n----- Account Details -----");

        for (Account acc : accounts) {
            System.out.println("Account Number : " + acc.getAccountNumber());
            System.out.println("Owner Name     : " + acc.getOwnerName());
            System.out.println("Balance        : " + acc.getBalance());
            System.out.println();
        }

        while (true) {

            System.out.println("========== MENU ==========");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter Your Choice : ");
            int choice = sc.nextInt();

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid Choice! Try Again.");
                continue;
            }

            switch (option) {

                case OPEN_ACCOUNT ->
                    System.out.println("Open Account - To be implemented in later lab.");

                case DEPOSIT ->
                    System.out.println("Deposit - To be implemented in later lab.");

                case WITHDRAW ->
                    System.out.println("Withdraw - To be implemented in later lab.");

                case TRANSFER ->
                    System.out.println("Transfer - To be implemented in later lab.");

                case EXIT -> {
                    System.out.println("Thank You for Using MiniBank.");
                    System.out.println("Good Bye!");
                    sc.close();
                    return;
                }
            }
        }
    }
}