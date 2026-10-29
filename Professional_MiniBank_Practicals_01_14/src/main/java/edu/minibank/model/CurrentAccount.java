package edu.minibank.model;
import edu.minibank.exception.InvalidAmountException;
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private final long overdraftLimit;
    public CurrentAccount(String owner, long opening, String customerId) throws InvalidAmountException { super(owner, opening, customerId); this.overdraftLimit = 5000; }
    @Override public double interestRate() { return 0.0; }
    @Override public boolean canWithdraw(long amount) { return getBalance() - amount >= -overdraftLimit; }
}
