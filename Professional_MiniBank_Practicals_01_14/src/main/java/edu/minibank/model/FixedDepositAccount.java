package edu.minibank.model;
import edu.minibank.exception.InvalidAmountException;
public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;
    public FixedDepositAccount(String owner, long opening, String customerId) throws InvalidAmountException { super(owner, opening, customerId); }
    @Override public double interestRate() { return 7.0; }
    @Override public boolean canWithdraw(long amount) { return false; }
}
