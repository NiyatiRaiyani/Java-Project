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

        BankInfo bank = new BankInfo("Baroda", "College Chowk Branch");

        // Practical 2: Creating Customers
        Customer customer1 = new Customer(
                "Niyati Raiyani",
                "niyati9856@gmail.com",
                "9876543210"
        );

        Customer customer2 = new Customer(
                "Dhanvi Patel",
                "dhanvi1252@gmail.com",
                "9876543211"
        );

        // Practical 2: Creating Accounts
        Account account1 = new Account("Niyati Raiyani", 5000);
        Account account2 = new Account("Priya Patel");
        Account account3 = new Account("Krina Sorathiya", 10000);

        // Practical 2: Transactions
        account1.deposit(2000);
        account2.deposit(3000);

        boolean withdrawn = account1.withdraw(1000);

        System.out.println("==================================");
        System.out.println("             MINIBANK");
        System.out.println("==================================");
        System.out.println("Bank Name : " + bank.name());
        System.out.println("Branch    : " + bank.branch());

        System.out.println("\nCustomer Details");
        System.out.println("----------------------------------");
        System.out.println("Customer ID : " + customer1.getCustomerId());
        System.out.println("Name        : " + customer1.getName());
        System.out.println("Email       : " + customer1.getEmail());
        System.out.println("Mobile      : " + customer1.getMobile());

        System.out.println("\nAccount Details");
        System.out.println("----------------------------------");

        System.out.println("Account No. : " + account1.getAccountNumber());
        System.out.println("Owner       : " + account1.getOwnerName());
        System.out.println("Balance     : ₹" + account1.getBalance());
        System.out.println("Active      : " + account1.isActive());

        System.out.println();

        System.out.println("Account No. : " + account2.getAccountNumber());
        System.out.println("Owner       : " + account2.getOwnerName());
        System.out.println("Balance     : ₹" + account2.getBalance());
        System.out.println("Active      : " + account2.isActive());

        System.out.println();

        System.out.println("Account No. : " + account3.getAccountNumber());
        System.out.println("Owner       : " + account3.getOwnerName());
        System.out.println("Balance     : ₹" + account3.getBalance());
        System.out.println("Active      : " + account3.isActive());

        System.out.println("\nWithdrawal Status : "
                + (withdrawn ? "Successful" : "Failed"));

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