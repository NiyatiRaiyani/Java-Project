package edu.minibank.util;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
public final class ReportGenerator {
    private ReportGenerator() {}
    public static String generate(Path logFile, Path reportFile) throws IOException {
        long deposits = 0, withdrawals = 0, transfers = 0;
        if (Files.exists(logFile)) {
            try (Stream<String> lines = Files.lines(logFile)) {
                for (String line : (Iterable<String>) lines::iterator) {
                    if (line.contains("DEPOSIT ")) deposits++;
                    if (line.contains("WITHDRAW ")) withdrawals++;
                    if (line.contains("TRANSFER ")) transfers++;
                }
            }
        }
        String report = "MINIBANK END-OF-DAY REPORT\\nDeposit operations: " + deposits +
                "\\nWithdrawal operations: " + withdrawals + "\\nTransfer operations: " + transfers + "\\n";
        Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile, report, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        if (Files.exists(logFile)) {
            BasicFileAttributes a = Files.readAttributes(logFile, BasicFileAttributes.class);
            report += "Log bytes: " + a.size() + "\\nLast modified: " + a.lastModifiedTime() + "\\n";
        }
        return report;
    }
}
