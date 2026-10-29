package edu.minibank.util;
import edu.minibank.model.Account;
import java.io.*;
import java.nio.file.*;
import java.util.Collection;
public final class StatePersister {
    private StatePersister() {}
    public static void save(Collection<Account> accounts, Path file) throws IOException {
        Files.createDirectories(file.getParent());
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(accounts.toArray(Account[]::new));
        }
    }
    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) return new Account[0];
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
            return (Account[]) in.readObject();
        }
    }
}
