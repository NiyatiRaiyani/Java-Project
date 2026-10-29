package edu.minibank.util;
public record Command(TransactionType type, String accountNumber, long amount) {}
