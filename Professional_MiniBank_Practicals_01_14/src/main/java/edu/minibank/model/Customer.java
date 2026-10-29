package edu.minibank.model;

import java.io.Serializable;

public class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static long customerCounter = 100;
    private final String customerId;
    private final String name;
    private final String email;
    private final String mobile;
    private Address address;

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, new Address("", "", ""));
    }
    public Customer(String name, String email, String mobile, Address address) {
        this.customerId = "CUST" + (++customerCounter);
        this.name = name; this.email = email; this.mobile = mobile; this.address = address;
    }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }
    @Override public Customer clone() {
        return new Customer(name, email, mobile, address == null ? null : new Address(address.line(), address.city(), address.pincode()));
    }
    public record Address(String line, String city, String pincode) implements Serializable {}
}
