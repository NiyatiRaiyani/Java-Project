import java.util.Objects;

public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    // Generate Account Number
    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    // Constructor with opening balance
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Constructor with zero balance
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Deposit money
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Getters
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

    // toString()
    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
               ", Owner Name: " + ownerName +
               ", Balance: " + balance;
    }

    // equals()
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Account))
            return false;

        Account account = (Account) o;

        return accountNumber.equals(account.accountNumber);
    }

    // hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
    @Override
    public Customer clone() {
    try {
        return (Customer) super.clone();
    } catch (CloneNotSupportedException e) {
        return null;
    }
}
}