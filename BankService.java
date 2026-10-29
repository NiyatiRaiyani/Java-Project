import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import exception.BankException;
import exception.AccountNotFoundException;

public class BankService {

    private Repository<Account> repository = new Repository<>();
    public BankService() {
    Path file = Path.of("data", "accounts.dat");

    if (Files.exists(file)) {
        try {
            Account[] savedAccounts = StatePersister.load(file);

            for (Account account : savedAccounts) {
                repository.save(account);
            }

            System.out.println("Saved accounts loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load saved accounts.");
        }
    }
}

    public Account openAccount(Account account) {
        return repository.save(account);
    }

    public Account findAccount(String accountNumber)
            throws AccountNotFoundException {

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException("Account not found!");
        }

        return account;
    }

    public void deposit(String accountNumber, long amount)
            throws BankException {

        Account account = findAccount(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, long amount)
            throws BankException {

        Account account = findAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(String fromId, String toId, long amount)
            throws BankException {

        Account from = findAccount(fromId);
        Account to = findAccount(toId);

        from.transfer(to, amount);
    }

    public String statement(String accountNumber)
            throws AccountNotFoundException {

        return findAccount(accountNumber).toString();
    }

    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }
    public void saveAccounts() {
    try {
        Path folder = Path.of("data");
        Files.createDirectories(folder);

        Account[] accounts = repository.findAll()
                .toArray(new Account[0]);

        StatePersister.save(accounts, folder.resolve("accounts.dat"));

        System.out.println("Accounts saved successfully.");
    } catch (IOException e) {
        System.out.println("Could not save accounts: " + e.getMessage());
    }
}
}
