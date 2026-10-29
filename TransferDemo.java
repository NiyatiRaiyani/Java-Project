
public class TransferDemo {

    static void transfer(String id1, Object account1,
                         String id2, Object account2) {

        Object firstLock;
        Object secondLock;

        if (id1.compareTo(id2) < 0) {
            firstLock = account1;
            secondLock = account2;
        } else {
            firstLock = account2;
            secondLock = account1;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                System.out.println(
                        Thread.currentThread().getName()
                        + " transferred money safely.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Object accountA = new Object();
        Object accountB = new Object();

        Thread t1 = new Thread(
                () -> transfer("AC0001", accountA,
                               "AC0002", accountB),
                "Thread-1");

        Thread t2 = new Thread(
                () -> transfer("AC0002", accountB,
                               "AC0001", accountA),
                "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Transfers completed without deadlock.");
    }
}
