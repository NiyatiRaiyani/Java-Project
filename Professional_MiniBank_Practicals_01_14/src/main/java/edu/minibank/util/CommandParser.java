package edu.minibank.util;
public final class CommandParser {
    private CommandParser() {}
    public static Command parse(String line) {
        String[] parts = line == null ? new String[0] : line.trim().split("\\\\s+");
        if (parts.length != 3) throw new IllegalArgumentException("Command format: TYPE ACCOUNT_NUMBER AMOUNT");
        return new Command(TransactionType.valueOf(parts[0].toUpperCase()), parts[1], Long.parseLong(parts[2]));
    }
}
