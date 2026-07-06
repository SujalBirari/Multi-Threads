public class Account {
    private final String id;
    private double balance;

    public Account(String id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void withdraw(double amount) throws InterruptedException {
        while (balance < amount) {
            wait();
        }
        balance -= amount;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        notifyAll();
    }
}