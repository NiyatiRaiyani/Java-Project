public class SavingsAccount extends Account implements Premium {
    private static final long serialVersionUID = 1L;

    private long minBalance;

    public SavingsAccount(String accountNumber, String ownerName,
                          long balance, long minBalance) {
        super(accountNumber, ownerName, balance);
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && balance - amount >= minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }
}
