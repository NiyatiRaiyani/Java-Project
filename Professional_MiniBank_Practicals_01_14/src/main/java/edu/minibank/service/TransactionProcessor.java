package edu.minibank.service;
import java.util.concurrent.*;
public class TransactionProcessor implements AutoCloseable {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);
    public void submit(Runnable task) { pool.execute(task); }
    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) pool.shutdownNow();
    }
    @Override public void close() {
        try { stop(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); pool.shutdownNow(); }
    }
}
