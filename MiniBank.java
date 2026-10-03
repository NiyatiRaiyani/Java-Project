import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.annotation.AnnotationValidator;
import service.Command;
import service.CommandParser;
import service.StatementFormatter;
import service.WithdrawRule;
import static util.Validator.isValidMobile;

public class MiniBank {

    public static void main(String[] args) throws CloneNotSupportedException {

        System.out.println("=================================");
        System.out.println("          MINI BANK");
        System.out.println("=================================");
        System.out.println("Bank Name : BARODA Bank");
        System.out.println("Branch    : GONDAL");

        // Practical 2 and 5: create different account types
        Account[] accounts = {
            new SavingsAccount("Niyati", 5000, 1000),
            new CurrentAccount("Priya", 8000, 3000),
            new FixedDepositAccount("Krina", 10000)
        };

        // Practical 2: deposit and withdrawal
        try {
            accounts[0].deposit(2000);
            accounts[0].withdraw(1000);
        } catch (BankException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        // Practical 3: toString(), equals(), instanceof
        System.out.println("\n----- Account Details -----");
        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\nEquals Check");
        System.out.println("accounts[0].equals(accounts[1]) = "
                + accounts[0].equals(accounts[1]));

        Object object = accounts[0];
        System.out.println("object instanceof Account = "
                + (object instanceof Account));

        // Practical 3: Customer, Address and clone
        Customer.Address address =
                new Customer.Address("College Road", "Gondal", "360001");

        Customer customer =
                new Customer("Niyati", "niyati@gmail.com", "9876543210", address);

        Customer copy = customer.clone();

        System.out.println("\n----- Customer Details -----");
        System.out.println("Customer ID : " + copy.getCustomerId());
        System.out.println("Name        : " + copy.getName());
        System.out.println("City        : " + copy.getAddress().getCity());

        // Practical 4: validation
        System.out.println("\n----- Validation -----");
        System.out.println("Valid Mobile : " + isValidMobile("9876543210"));
        System.out.println("Invalid Mobile : " + isValidMobile("12345"));

        // Practical 4: command parsing
        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("\n----- Parsed Command -----");
        System.out.println("Type          : " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount        : " + command.amount());

        // Practical 4: statement
        System.out.println("\n----- Statement -----");
        System.out.println(StatementFormatter.buildStatement(accounts[0]));

        // Practical 5: runtime polymorphism
        System.out.println("----- Interest Rates -----");
        for (Account account : accounts) {
            System.out.println(account.getOwnerName() + " : "
                    + account.interestRate() + "%");

            if (account instanceof SavingsAccount savings) {
                System.out.println("Savings minimum balance: "
                        + savings.getMinBalance());
            }
        }

        // Practical 6: default interface method
        System.out.println("\n----- Yearly Interest -----");
        for (Account account : accounts) {
            System.out.println(account.getOwnerName() + " : ₹"
                    + account.yearlyInterest());
        }

        // Practical 6: anonymous class
        WithdrawRule rule1 = (Account account, long amount) -> account.getBalance() >= amount;

        // Practical 6: lambda
        WithdrawRule rule2 =
                (account, amount) -> account.canWithdraw(amount);

        System.out.println("\nWithdraw Rule");
        System.out.println("Anonymous Class : " + rule1.allow(accounts[0], 1000));
        System.out.println("Lambda          : " + rule2.allow(accounts[0], 1000));

        // Practical 7: annotation validation
        Account invalidAccount = new SavingsAccount("Test", -100, 0);

        System.out.println("\n----- Annotation Validation -----");
        String[] errors = AnnotationValidator.validate(invalidAccount);

        for (String error : errors) {
            System.out.println(error);
        }

        // Practical 8: custom exceptions
        System.out.println("\n----- Exception Handling -----");

        try {
            accounts[0].withdraw(50000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by "
                    + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        } finally {
            System.out.println("Withdrawal attempt completed.");
        }

        // Practical 8: transfer
        try {
            accounts[0].transfer(accounts[1], 500);
            System.out.println("Transfer completed successfully.");
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } finally {
            System.out.println("Transfer operation completed.");
        }

        // Practical 8: try-with-resources
        try (BankSession session = new BankSession()) {
            session.open();
        } catch (Exception e) {
            System.out.println("Resource error: " + e.getMessage());
        }
    }
}
