package edu.minibank.model;

import edu.minibank.exception.InsufficientFundsException;
import edu.minibank.exception.InvalidAmountException;
import edu.minibank.util.annotation.Id;
import edu.minibank.util.annotation.Positive;

import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;
    private static long accountCounter = 1000;
    @Id private final String accountNumber;
    private final String ownerName;
    @Positive private long balance;
    private boolean active = true;
    private final String customerId;
    protected Account(String ownerName, long openingBalance, String customerId) throws InvalidAmountException {
        if (openingBalance < 0) throw new InvalidAmountException("Opening balance cannot be negative.");
        this.accountNumber = "AC" + String.format("%04d", ++accountCounter);
        this.ownerName = Objects.requireNonNull(ownerName);
        this.balance = openingBalance;
        this.customerId = customerId;
    }
    @Override public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive.");
        balance = Math.addExact(balance, amount);
    }
    @Override public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive.");
        if (!active) throw new InvalidAmountException("Account is inactive.");
        if (!canWithdraw(amount)) throw new InsufficientFundsException("Insufficient funds or withdrawal rule rejected this amount.");
        balance = Math.subtractExact(balance, amount);
        return true;
    }
    public abstract boolean canWithdraw(long amount);
    @Override public abstract double interestRate();
    @Override public synchronized long getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public String getCustomerId() { return customerId; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    @Override public int compareTo(Account other) { return accountNumber.compareTo(other.accountNumber); }
    @Override public boolean equals(Object obj) { return obj instanceof Account other && accountNumber.equals(other.accountNumber); }
    @Override public int hashCode() { return accountNumber.hashCode(); }
    @Override public String toString() { return accountNumber + " | " + ownerName + " | balance=" + balance + " | " + getClass().getSimpleName(); }
}
