public class BankSession implements AutoCloseable {

    public void open() {
        System.out.println("Bank session opened.");
    }

    @Override
    public void close() {
        System.out.println("Bank session closed.");
    }
}
