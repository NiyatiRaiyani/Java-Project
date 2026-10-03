package service;

public class CommandParser {

    public static Command parse(String line) {

        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Command must contain type, account number and amount.");
        }

        TransactionType type = TransactionType.valueOf(
                parts[0].toUpperCase());

        long amount = Long.parseLong(parts[2]);

        return new Command(type, parts[1], amount);
    }
}
