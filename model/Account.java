package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import service.InterestBearing;
import service.Transactable;

public abstract class Account implements Transactable, InterestBearing {

    @Id
    private final String accountNumber;

    private String ownerName;

    @Positive
    private long balance;

    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    protected Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String accountNumber, boolean active, long balance, String ownerName) {
        this.accountNumber = accountNumber;
        this.active = active;
        this.balance = balance;
        this.ownerName = ownerName;
    }

    public Account(String accountNumber, boolean active) {
        this.accountNumber = accountNumber;
        this.active = active;
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
    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }

        if (!canWithdraw(amount)) {
            long shortfall = amount - balance;
            if (shortfall < 0) {
                shortfall = amount;
            }
            throw new InsufficientFundsException(
                    "Withdrawal is not allowed for this account.",
                    shortfall);
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new BankException("Target account cannot be null.");
        }

        try {
            withdraw(amount);
            to.deposit(amount);
        } catch (BankException e) {
            throw e;
        } finally {
            System.out.println("Transfer attempt finished.");
        }
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Account)) {
            return false;
        }

        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
