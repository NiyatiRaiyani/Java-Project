
public class CommandParser {

    public static Command parse(String line) {

        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "Command must have 3 parts: TYPE ACCOUNT_NUMBER AMOUNT"
            );
        }

        TransactionType type =
                TransactionType.valueOf(parts[0].toUpperCase());

        String accountNumber = parts[1];

        if (!Validator.isValidAmount(parts[2])) {
            throw new IllegalArgumentException("Invalid amount.");
        }

        long amount = Long.parseLong(parts[2]);

        return new Command(type, accountNumber, amount);
    }
}
