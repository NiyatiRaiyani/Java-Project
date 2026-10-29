package edu.minibank.exception;
public class InsufficientFundsException extends BankException {
    private static final long serialVersionUID = 1L;
    private final long shortfall;
    public InsufficientFundsException(String message) { super(message); this.shortfall = 0; }
    public InsufficientFundsException(long shortfall) { super("Insufficient funds. Shortfall: " + shortfall); this.shortfall = shortfall; }
    public long getShortfall() { return shortfall; }
}
