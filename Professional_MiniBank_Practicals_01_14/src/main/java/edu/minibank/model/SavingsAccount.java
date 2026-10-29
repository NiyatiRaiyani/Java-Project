package edu.minibank.model;
import edu.minibank.exception.InvalidAmountException;
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long minBalance;
    public SavingsAccount(String owner, long opening, String customerId) throws InvalidAmountException { super(owner, opening, customerId); this.minBalance = 500; }
    @Override public double interestRate() { return 4.0; }
    @Override public boolean canWithdraw(long amount) { return getBalance() - amount >= minBalance; }
}
