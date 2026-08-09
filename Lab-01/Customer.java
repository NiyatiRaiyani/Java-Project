public class Customer {

    private String name;
    private String email;
    private String mobile;

    private final String customerId;

    private static long customerCounter = 100;

    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
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

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}