
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static void generate(Path logsFolder, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;

        List<String> details = new ArrayList<>();

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(logsFolder)) {

            for (Path file : stream) {

                if (!Files.isRegularFile(file)
                        || !file.toString().endsWith(".log")
                        || Files.size(file) == 0) {
                    continue;
                }

                BasicFileAttributes attrs = Files.readAttributes(
                        file, BasicFileAttributes.class);

                details.add("File: " + file.getFileName()
                        + ", Size: " + attrs.size()
                        + ", Last Modified: " + attrs.lastModifiedTime());

                try (BufferedReader reader =
                             Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length == 3) {
                            try {
                                long amount = Long.parseLong(parts[2]);

                                if (parts[0].equals("DEPOSIT")) {
                                    totalDeposits += amount;
                                } else if (parts[0].equals("WITHDRAW")) {
                                    totalWithdrawals += amount;
                                }
                            } catch (NumberFormatException e) {
                                // Skip invalid amounts.
                            }
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;

        StringBuilder report = new StringBuilder();
        report.append("End-of-Day Report\n");
        report.append("Total Deposits: ").append(totalDeposits).append('\n');
        report.append("Total Withdrawals: ").append(totalWithdrawals).append('\n');
        report.append("Net Change: ").append(netChange).append('\n');
        report.append("\nFile Details:\n");

        for (String detail : details) {
            report.append(detail).append('\n');
        }

        Files.writeString(reportFile, report.toString());

        System.out.println(report);
    }
}
