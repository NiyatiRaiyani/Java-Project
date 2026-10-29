package edu.minibank.util;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
public final class TransactionLog {
    private TransactionLog() {}
    public static void append(Path file, String line) throws IOException {
        Files.createDirectories(file.getParent());
        Files.writeString(file, Instant.now() + " | " + line + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
