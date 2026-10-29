package edu.minibank.service;
import edu.minibank.model.Account;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class AccountStore {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final List<String> history = Collections.synchronizedList(new ArrayList<>());
    public void add(Account account) { accounts.put(account.getAccountNumber(), account); }
    public Account get(String id) { return accounts.get(id); }
    public Collection<Account> getAll() { return List.copyOf(accounts.values()); }
    public List<Account> sortedByBalance() {
        ArrayList<Account> result = new ArrayList<>(accounts.values());
        result.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return result;
    }
    public void addHistory(String line) { history.add(line); }
    public List<String> history() { synchronized(history) { return List.copyOf(history); } }
    public int size() { return accounts.size(); }
    public void clear() { accounts.clear(); history.clear(); }
}
