package edu.minibank;

import edu.minibank.exception.BankException;
import edu.minibank.model.Account;
import edu.minibank.service.BankService;
import edu.minibank.util.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {
    @TempDir Path temp;
    private BankService service() {
        return new BankService(temp.resolve("accounts.dat"), temp.resolve("transactions.log"));
    }
    @Test void depositIncreasesBalance() throws Exception {
        BankService s = service();
        Account a = s.openAccount("Riya", "riya@example.com", "9876543210", "CURRENT", 1000);
        s.deposit(a.getAccountNumber(), 500);
        assertEquals(1500, a.getBalance());
    }
    @Test void invalidMobileIsRejected() {
        assertFalse(Validator.isValidMobile("12345"));
    }
    @Test void transferMovesMoneyWithoutChangingTotal() throws Exception {
        BankService s = service();
        Account a = s.openAccount("Riya", "riya@example.com", "9876543210", "CURRENT", 5000);
        Account b = s.openAccount("Asha", "asha@example.com", "9876543211", "CURRENT", 1000);
        s.transfer(a.getAccountNumber(), b.getAccountNumber(), 700);
        assertEquals(4300, a.getBalance());
        assertEquals(1700, b.getBalance());
    }
    @Test void invalidWithdrawalDoesNotChangeBalance() throws Exception {
        BankService s = service();
        Account a = s.openAccount("Riya", "riya@example.com", "9876543210", "CURRENT", 1000);
        assertThrows(BankException.class, () -> s.withdraw(a.getAccountNumber(), -10));
        assertEquals(1000, a.getBalance());
    }
}
