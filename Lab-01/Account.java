import java.util.Objects;

public abstract class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%03d", accountCounter);
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
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) {
            balance -= amount;
            return true;
        }

        return false;
    }

    // Practical 5: Abstract Methods
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

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

    // Practical 3: toString()
    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: " + balance;
    }

    // Practical 3: equals()
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Account)) {
            return false;
        }

        Account other = (Account) o;

        return accountNumber.equals(other.accountNumber);
    }

    // Practical 3: hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}