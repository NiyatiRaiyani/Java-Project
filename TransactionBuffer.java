
import java.util.LinkedList;
import java.util.Queue;

public class TransactionBuffer {

    @SuppressWarnings("FieldMayBeFinal")
    private Queue<Integer> transactions = new LinkedList<>();

    public synchronized void addTransaction(int amount)
            throws InterruptedException {

        transactions.add(amount);
        System.out.println("Produced: " + amount);

        notify();
    }

    public synchronized int getTransaction()
            throws InterruptedException {

        while (transactions.isEmpty()) {
            wait();
        }

        int amount = transactions.remove();
        System.out.println("Processed: " + amount);

        return amount;
    }
}
