import java.util.Objects;

record BankInfo(String name, String branch) {}

// Customer Class
class Customer implements Cloneable {

    private String name, email, mobile;
    private final String customerId;
    private Address address;

    private static int id = 101;

    public Customer(Address address, String customerId) {
        this.address = address;
        this.customerId = customerId;
    }

    public Customer(String customerId, String email, String mobile, String name) {
        this.customerId = customerId;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
    }

    public Customer(String customerId, String email) {
        this.customerId = customerId;
        this.email = email;
    }

    public Customer(Address address, String customerId, String name) {
        this.address = address;
        this.customerId = customerId;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    // Static Nested Class
    public static class Address {
        private String line, city, pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = "CUST" + id++;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public Customer clone() throws CloneNotSupportedException {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

// Account Class
class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active = true;

    private static int count = 1;

    public Account(String ownerName, long balance) {
        this.accountNumber = String.format("AC%04d", count++);
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amt) {
        if (amt > 0)
            balance += amt;
    }

    public void withdraw(long amt) {
        if (amt > 0 && balance >= amt)
            balance -= amt;
    }

    public static void transfer(Account a1, Account a2, long amt) {
        if (a1.balance >= amt) {
            a1.withdraw(amt);
            a2.deposit(amt);
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName +
                " | Balance: " + balance +
                " | Active: " + active;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Account))
            return false;
        Account a = (Account) obj;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public String getStatement() {
        return """
        -----------------------
        Account : %s
        Owner   : %s
        Balance : %d
        Active  : %b
        -----------------------
        """.formatted(accountNumber, ownerName, balance, active);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

// Main Class
public class MiniBank {

    public static void main(String[] args) throws CloneNotSupportedException {

        BankInfo bank = new BankInfo("BARODA Bank", "GONDAL");

        Customer c1 = new Customer("Niyati", "niyati@gmail.com", "9876543210",
                new Customer.Address("Rajkot Road", "Gondal", "360311"));

        Customer c2 = new Customer("Vishva", "vishva@gmail.com", "9988776655",
                new Customer.Address("Yagnik Road", "Rajkot", "360001"));

        Account a1 = new Account(c1.getName(), 5000);
        Account a2 = new Account(c2.getName(), 3000);

        a1.deposit(1000);
        a2.withdraw(500);
        Account.transfer(a1, a2, 1000);

        System.out.println("===== MINI BANK =====");
        System.out.println(bank.name() + " - " + bank.branch());

        System.out.println("\nAccounts:");
        System.out.println(a1);
        System.out.println(a2);

        System.out.println("\nStatement:");
        System.out.println(a1.getStatement());

        System.out.println("a1.equals(a1) : " + a1.equals(a1));

        Object obj = a1;
        System.out.println("instanceof Account : " + (obj instanceof Account));

        Customer copy = c1.clone();
        System.out.println("\nOriginal : " + c1.getName() + " - " + c1.getAddress());
        System.out.println("Clone    : " + copy.getName() + " - " + copy.getAddress());
    }
}