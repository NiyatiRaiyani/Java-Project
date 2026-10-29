import java.io.Serializable;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {


    private static final long serialVersionUID = 1L;
    @Id
    private final String accountNumber;

    private final String ownerName;

    @Positive
    protected long balance;

    private boolean active;

    public Account(String ownerName, long balance) {
        this("AC0000", ownerName, balance);
    }

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
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

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero.");
        }

        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero.");
        }

        if (!canWithdraw(amount)) {
            long shortfall = amount - balance;

            throw new InsufficientFundsException(
                    "Insufficient funds!", shortfall);
        }

        balance -= amount;
        return true;
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public double monthlyInterest() {
        return balance * interestRate() / 100 / 12;
    }

    @Override
    public String toString() {
        return "Account{"
                + "accountNumber='" + accountNumber + '\''
                + ", ownerName='" + ownerName + '\''
                + ", balance=" + balance
                + ", active=" + active
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

public void transfer(Account to, long amount) throws BankException {
    try {
        this.withdraw(amount);
        to.deposit(amount);
        System.out.println("Transfer successful!");
    } catch (BankException e) {
        throw e;
    } finally {
        System.out.println("Transfer attempt completed.");
    }
}
@Override
public int compareTo(Account other) {
    return this.accountNumber.compareTo(other.accountNumber);
}

}
