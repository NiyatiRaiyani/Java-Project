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

    public static void main(String[] args) throws CloneNotSupportedException {

        try (Scanner sc = new Scanner(System.in)) {

            BankInfo bank = new BankInfo(
                    "Baroda",
                    "College Chowk Branch"
            );

            // Practical 2: Creating Customer
            Customer customer1 = new Customer(
                    "Niyati Raiyani",
                    "niyati9856@gmail.com",
                    "9876543210"
            );

            // Practical 3: Creating Address
            Customer.Address address = new Customer.Address(
                    "College Road",
                    "Gondal",
                    "388001"
            );

            customer1.setAddress(address);

            // ==========================================
            // Practical 5: Creating Different Account Types
            // ==========================================

            SavingsAccount account1 =
                    new SavingsAccount(
                            "Niyati Raiyani",
                            5000,
                            2000
                    );

            CurrentAccount account2 =
                    new CurrentAccount(
                            "Dhanvi Patel",
                            0,
                            3000
                    );

            FixedDepositAccount account3 =
                    new FixedDepositAccount(
                            "Krina Sorathiya",
                            10000
                    );

            // Practical 2: Transactions
            account1.deposit(2000);
            account2.deposit(3000);

            boolean withdrawn = account1.withdraw(1000);

            System.out.println("==================================");
            System.out.println("             MINIBANK");
            System.out.println("==================================");
            System.out.println("Bank Name : " + bank.name());
            System.out.println("Branch    : " + bank.branch());

            // Customer Details
            System.out.println("\nCustomer Details");
            System.out.println("----------------------------------");
            System.out.println("Customer ID : "
                    + customer1.getCustomerId());
            System.out.println("Name        : "
                    + customer1.getName());
            System.out.println("Email       : "
                    + customer1.getEmail());
            System.out.println("Mobile      : "
                    + customer1.getMobile());

            // Practical 3: Address Details
            System.out.println("\nAddress Details");
            System.out.println("----------------------------------");
            System.out.println("Address : "
                    + customer1.getAddress().getLine());
            System.out.println("City    : "
                    + customer1.getAddress().getCity());
            System.out.println("Pincode : "
                    + customer1.getAddress().getPincode());

            // Account Details
            System.out.println("\nAccount Details");
            System.out.println("----------------------------------");

            // Practical 3: toString()
            System.out.println(account1.toString());
            System.out.println(account2.toString());
            System.out.println(account3.toString());

            System.out.println("\nWithdrawal Status : "
                    + (withdrawn ? "Successful" : "Failed"));

            // Practical 3: equals()
            System.out.println("\nEquals Comparison");
            System.out.println("----------------------------------");
            System.out.println("Account 1 equals Account 2 : "
                    + account1.equals(account2));

            // Practical 3: instanceof
            System.out.println("\nInstanceof Check");
            System.out.println("----------------------------------");
            System.out.println("account1 is Account : "
                    + (account1 instanceof Account));

            // Practical 3: clone()
            Customer clonedCustomer = customer1.clone();

            System.out.println("\nClone Details");
            System.out.println("----------------------------------");
            System.out.println("Original Customer : "
                    + customer1.getName());
            System.out.println("Cloned Customer   : "
                    + clonedCustomer.getName());

            // ==========================================
            // Practical 4: Validator Testing
            // ==========================================

            System.out.println("\nValidator Testing");
            System.out.println("----------------------------------");

            System.out.println("Valid Mobile   : "
                    + Validator.isValidMobile("9876543210"));

            System.out.println("Invalid Mobile : "
                    + Validator.isValidMobile("12345"));

            System.out.println("Valid Email    : "
                    + Validator.isValidEmail(
                            "niyati9856@gmail.com"
                    ));

            System.out.println("Invalid Email  : "
                    + Validator.isValidEmail(
                            "niyati@"
                    ));

            System.out.println("Valid PAN      : "
                    + Validator.isValidPan("ABCDE1234F"));

            System.out.println("Invalid PAN    : "
                    + Validator.isValidPan("ABC123"));

            System.out.println("Valid IFSC     : "
                    + Validator.isValidIfsc("BARB0COLLEG"));

            System.out.println("Invalid IFSC   : "
                    + Validator.isValidIfsc("BAR123"));

            // ==========================================
            // Practical 4: Command Parser
            // ==========================================

            System.out.println("\nCommand Parser Testing");
            System.out.println("----------------------------------");

            String input = "DEPOSIT AC001 500";

            Command command = CommandParser.parse(input);

            System.out.println("Input Command    : " + input);
            System.out.println("Transaction Type : "
                    + command.type());
            System.out.println("Account Number   : "
                    + command.accountNumber());
            System.out.println("Amount           : "
                    + command.amount());

            // ==========================================
            // Practical 4: Statement Formatter
            // ==========================================

            System.out.println("\nAccount Statement");
            System.out.println("----------------------------------");

            System.out.println(
                    StatementFormatter.buildStatement(account1)
            );

            // ==========================================
            // Practical 5: Polymorphism
            // ==========================================

            Account[] accounts = {
                account1,
                account2,
                account3
            };

            System.out.println("\nPolymorphism - Interest Rates");
            System.out.println("----------------------------------");

            for (Account account : accounts) {

                System.out.println(
                        account.getClass().getSimpleName()
                                + " : "
                                + account.interestRate()
                                + "%"
                );
            }

            // ==========================================
            // Practical 5: Pattern instanceof
            // ==========================================

            System.out.println("\nPattern instanceof Check");
            System.out.println("----------------------------------");

            for (Account account : accounts) {

                if (account instanceof SavingsAccount savings) {

                    System.out.println(
                            "Savings Account detected."
                    );

                    System.out.println(
                            "Minimum Balance : "
                                    + savings.getMinBalance()
                    );
                }
            }

            // ==========================================
            // Practical 5: Withdrawal Rules
            // ==========================================

            System.out.println("\nAccount Withdrawal Rules");
            System.out.println("----------------------------------");

            System.out.println(
                    "Savings Account Withdrawal : "
                            + account1.withdraw(3000)
            );

            System.out.println(
                    "Current Account Withdrawal : "
                            + account2.withdraw(5000)
            );

            System.out.println(
                    "Fixed Deposit Withdrawal   : "
                            + account3.withdraw(1000)
            );

            // ==========================================
            // Existing Practical 1 Menu
            // ==========================================

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

                    case 1 ->
                        option = MenuOption.OPEN_ACCOUNT;

                    case 2 ->
                        option = MenuOption.DEPOSIT;

                    case 3 ->
                        option = MenuOption.WITHDRAW;

                    case 4 ->
                        option = MenuOption.TRANSFER;

                    case 5 ->
                        option = MenuOption.EXIT;

                    default -> {
                        System.out.println(
                                "Invalid choice. Please try again."
                        );
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
        }

        System.out.println("Goodbye!");
    }
}