
public class BankResource implements AutoCloseable {

    public void use() {
        System.out.println("Bank resource is in use.");
    }

    @Override
    public void close() {
        System.out.println("Bank resource closed.");
    }
}
