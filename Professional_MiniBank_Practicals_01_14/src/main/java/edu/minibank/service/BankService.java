package edu.minibank.service;

import edu.minibank.exception.*;
import edu.minibank.model.*;
import edu.minibank.util.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class BankService {
    private final AccountStore store = new AccountStore();
    private final Repository<Account> repository = new Repository<>();
    private final Path dataFile;
    private final Path logFile;
    private final Path reportFile;
    private final Map<String, Customer> customers = new HashMap<>();

    public BankService(Path dataFile, Path logFile) {
        this.dataFile = dataFile; this.logFile = logFile;
        this.reportFile = logFile.resolveSibling("end-of-day-report.txt");
    }

    public Account openAccount(String owner, String email, String mobile, String type, long opening) throws BankException {
        if (owner == null || owner.isBlank()) throw new IllegalArgumentException("Owner name is required.");
        if (!Validator.isValidEmail(email)) throw new IllegalArgumentException("Invalid email address.");
        if (!Validator.isValidMobile(mobile)) throw new IllegalArgumentException("Invalid mobile number.");
        Customer customer = new Customer(owner, email, mobile);
        Account account = switch (type.toUpperCase(Locale.ROOT)) {
            case "SAVINGS" -> new SavingsAccount(owner, opening, customer.getCustomerId());
            case "CURRENT" -> new CurrentAccount(owner, opening, customer.getCustomerId());
            case "FIXED", "FIXED_DEPOSIT" -> new FixedDepositAccount(owner, opening, customer.getCustomerId());
            default -> throw new IllegalArgumentException("Account type must be SAVINGS, CURRENT, or FIXED.");
        };
        customers.put(customer.getCustomerId(), customer);
        store.add(account); repository.save(account);
        log("OPEN " + account.getAccountNumber() + " " + opening);
        return account;
    }

    public void deposit(String id, long amount) throws BankException {
        Account a = require(id); a.deposit(amount); log("DEPOSIT " + id + " " + amount);
    }
    public void withdraw(String id, long amount) throws BankException {
        Account a = require(id); a.withdraw(amount); log("WITHDRAW " + id + " " + amount);
    }
    public void transfer(String fromId, String toId, long amount) throws BankException {
        if (fromId.equals(toId)) throw new IllegalArgumentException("Source and destination must differ.");
        Account from = require(fromId), to = require(toId);
        // Validate destination and source before changing either balance.
        if (amount <= 0) throw new InvalidAmountException("Transfer amount must be positive.");
        if (!from.canWithdraw(amount)) throw new InsufficientFundsException("Transfer rejected by source account rules.");
        from.withdraw(amount);
        try { to.deposit(amount); }
        catch (BankException | RuntimeException e) {
            try { from.deposit(amount); } catch (BankException rollback) { e.addSuppressed(rollback); }
            throw e;
        } finally { /* audit/cleanup boundary for Practical 8 */ }
        log("TRANSFER " + fromId + " -> " + toId + " " + amount);
    }
    public String statement(String id) throws AccountNotFoundException { return StatementFormatter.buildStatement(require(id)); }
    public List<Account> listAccounts() { return store.sortedByBalance(); }
    public Account find(String id) { return store.get(id); }
    private Account require(String id) throws AccountNotFoundException {
        Account account = repository.findById(id);
        if (account == null) throw new AccountNotFoundException(id);
        return account;
    }
    private void log(String line) {
        store.addHistory(line);
        try { TransactionLog.append(logFile, line); }
        catch (IOException e) { System.out.println("Warning: transaction log could not be written: " + e.getMessage()); }
    }
    public void save() throws IOException { StatePersister.save(store.getAll(), dataFile); }
    public void load() throws IOException, ClassNotFoundException {
        store.clear();
        for (Account account : StatePersister.load(dataFile)) { store.add(account); repository.save(account); }
    }
    public String generateReport() throws IOException { return ReportGenerator.generate(logFile, reportFile); }
    public void runBatchDemo() throws InterruptedException {
        try (TransactionProcessor processor = new TransactionProcessor()) {
            for (Account account : store.getAll()) {
                processor.submit(() -> {
                    try { account.deposit(1); }
                    catch (BankException ignored) { /* task reports failure by leaving balance unchanged */ }
                });
            }
        }
    }
    public int accountCount() { return store.size(); }
}
