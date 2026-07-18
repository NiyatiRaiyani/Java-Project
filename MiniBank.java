import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

// Customer Class
class Customer {

    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    private static long customerCounter = 101;

    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }
}

// Account Class
class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount < 0) {
            System.out.println("Invalid Deposit Amount");
            return;
        }
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount < 0) {
            System.out.println("Invalid Withdraw Amount");
            return false;
        }

        if (balance >= amount) {
            balance -= amount;
            return true;
        }

        return false;
    }

    public static boolean transfer(Account from, Account to, long amount) {
        if (from.withdraw(amount)) {
            to.deposit(amount);
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}

// Main Class
public class MiniBank {

    public static void main(String[] args) {

        try (Scanner _ = new Scanner(System.in)) {
            BankInfo bank = new BankInfo("BARODA Bank", "GONDAL");
            
            Customer c1 = new Customer("Niyati", "niyati@gmail.com", "9876543210");
            Customer c2 = new Customer("Raj", "raj@gmail.com", "9988776655");
            Customer c3 = new Customer("Priya", "priya@gmail.com", "9123456789");
            
            Account[] accounts = new Account[3];
            
            accounts[0] = new Account(c1.getName(), 5000);
            accounts[1] = new Account(c2.getName());
            accounts[2] = new Account(c3.getName(), 10000);
            
            accounts[0].deposit(1000);
            accounts[1].deposit(3000);
            
            accounts[0].withdraw(2000);
            accounts[2].withdraw(1500);
            
            Account.transfer(accounts[1], accounts[2], 500);
            
            System.out.println("=================================");
            System.out.println("          MINI BANK");
            System.out.println("=================================");
            System.out.println("Bank Name : " + bank.name());
            System.out.println("Branch    : " + bank.branch());
            
            System.out.println("\nAccount Details:");
            
            for (Account acc : accounts) {
                System.out.println("--------------------------------");
                System.out.println("Account No : " + acc.getAccountNumber());
                System.out.println("Owner      : " + acc.getOwnerName());
                System.out.println("Balance    : ₹" + acc.getBalance());
                System.out.println("Active     : " + acc.isActive());
            }
        }
    }
}