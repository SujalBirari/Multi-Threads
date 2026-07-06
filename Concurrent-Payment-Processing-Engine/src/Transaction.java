public class Transaction implements Runnable{
    private final Account fromAccount;
    private final Account toAccount;
    private final double amount;

    public Transaction(Account fromAccount, Account toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (SequentialEngine.emergencyHalt) return;

        // 1. Simulate network/database delay (10 milliseconds)
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. Perform the transfer
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}