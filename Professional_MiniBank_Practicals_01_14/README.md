# Professional MiniBank — OOP CEUE203 Practicals 1–14

Console-based educational banking application built with Java 17+ and Maven. It is designed around the uploaded practical list and demonstrates the semester's concepts in one integrated project.

## Features
- Open savings, current, and fixed-deposit accounts
- Deposit, withdraw, transfer, account list, statement, and end-of-day report
- Input validation and typed bank exceptions
- Encapsulation, records, enums, inheritance, abstract classes, interfaces, lambdas
- Custom runtime annotations and reflection-based `@Id` repository
- Collections, comparators, concurrent account store, thread pool
- Serializable accounts, file save/load, append-only transaction log and report
- JUnit 5 tests and runnable JAR packaging

> Educational simulation only. It is not connected to real banking systems and must not be used to store real customer data or credentials.

## Requirements
- JDK 17 or newer
- Maven 3.8+ (for tests/build)

## Run in VS Code / IntelliJ
Open this folder as a Maven project. Run `edu.minibank.MiniBank`.

## Build, test, run
```bash
mvn clean test package
java -jar target/professional-minibank-1.0.0.jar
```

## Menu
1. Open account
2. Deposit
3. Withdraw
4. Transfer
5. Account statement
6. List accounts
7. End-of-day report
8. Run safe batch demo
0. Save and exit

Sample test data can be created by opening accounts through the menu. Persistent state is written under `data/`.

## Practical mapping
| Practical | Integrated project concepts |
|---|---|
| 1 | Menu shell, `BankInfo` record, `MenuOption`, switch |
| 2 | `Customer`, `Account`, encapsulation, generated IDs |
| 3 | `toString`, `equals`, `hashCode`, nested `Address`, cloning |
| 4 | `Validator`, regex, `Command`, `CommandParser`, `StatementFormatter` |
| 5 | Abstract `Account`, savings/current/fixed-deposit polymorphism |
| 6 | `Transactable`, `InterestBearing`, `WithdrawRule`, packages, runnable JAR |
| 7 | `@Id`, `@Positive`, `@MaxLength`, reflection validator |
| 8 | Checked exception hierarchy, try/catch/finally, safe transfer |
| 9 | `Runnable`, synchronized account operations, race-condition prevention |
| 10 | `ExecutorService`, transaction batch processing |
| 11 | Serialization, NIO, append-only log, report generator |
| 12 | `HashMap`, `ArrayList`, `Comparator`, `Comparable`, concurrent map |
| 13 | Generic `Repository<T>`, reflection-based ID lookup, bounded generic method |
| 14 | `BankService`, integrated menu, JUnit 5, final JAR and README |

## Notes
- Money is stored as whole rupees (`long`) to match the practical list.
- This project is for learning OOP concepts, not production banking/security.
- Generated files in `data/` are ignored by Git.
