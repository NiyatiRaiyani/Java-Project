
package edu.minibank.util;

import edu.minibank.model.Account;

public final class StatementFormatter {
    private StatementFormatter() {}

    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder("MINIBANK ACCOUNT STATEMENT\n");
        sb.append("Account: ").append(account.getAccountNumber()).append('\n');
        sb.append("Owner: ").append(account.getOwnerName()).append('\n');
        sb.append("Type: ").append(account.getClass().getSimpleName()).append('\n');
        sb.append("Interest rate: ").append(account.interestRate()).append("%\n");
        sb.append("Balance: Rs. ").append(account.getBalance()).append('\n');
        sb.append("Active: ").append(account.isActive()).append('\n');
        return sb.toString();
    }
}
