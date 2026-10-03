package service;

import model.Account;

public interface InterestBearing {

    double interestRate();

    default double yearlyInterest() {
        return getAccountBalance() * interestRate() / 100.0;
    }

    private double getAccountBalance() {
        return ((Account) this).getBalance();
    }
}
