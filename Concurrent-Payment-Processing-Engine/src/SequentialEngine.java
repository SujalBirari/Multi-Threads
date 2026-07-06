import java.util.ArrayList;
import java.util.List;

public class SequentialEngine {
    public static volatile boolean emergencyHalt = false;

    public static void main(String[] args) {
        System.out.println("Starting Sequential Payment Processing...");

        // 1. Setup Accounts
        Account alice = new Account("Alice", 10000.0);
//        Account alice = new Account("Alice", 0.0); for Thread Synchronization test
        Account bob = new Account("Bob", 10000.0);

        System.out.println("Initial Total Balance: " + (alice.getBalance() + bob.getBalance()));

        // 2. Generate Transactions (Let's simulate 1,000 transfers of $1 from Alice to Bob)
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            transactions.add(new Transaction(alice, bob, 1.0));
        }

        // 3. Process Transactions Sequentially
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        for (Transaction tx : transactions) {
//            tx.execute(); // Blocks the main thread for 10ms every loop!
            Thread t = new Thread(tx);
            t.start();
            threads.add(t);
        }

        // Volatile Test - success (keep Alice's balance back to 1000 for this to work)
//        Thread emergencyHaltThread = new Thread(() -> {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Emergency!!! Halt All Threads!!!");
//        });
//        emergencyHaltThread.start();

        for (Thread t : threads) {
            if (SequentialEngine.emergencyHalt) return;
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        // 4. Print Results
        System.out.println("Final Alice Balance: " + alice.getBalance());
        System.out.println("Final Bob Balance: " + bob.getBalance());
        System.out.println("Final Total Balance: " + (alice.getBalance() + bob.getBalance()));
        System.out.println("Total Time Taken: " + (endTime - startTime) + " ms");

        // Thread Synchronization Test
//        for (int i = 0; i < 10; i++) {
//            transactions.add(new Transaction(alice, bob, 10.0));
//        }
//
//        List<Thread> threads = new ArrayList<>();
//        for (Transaction tx : transactions) {
//            Thread t = new Thread(tx);
//            t.start();
//            threads.add(t);
//        }
//
//        Thread threadToDepositAlice = new Thread(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Transaction transaction = new Transaction(bob, alice, 100.0);
//            Thread t = new Thread(transaction);
//            t.start();
////            threads.add(t); causing a java.util.ConcurrentModificationException because threads is an ArrayList and not thread-safe
//        });
//        threadToDepositAlice.start();
//
//        for (Thread t : threads) {
//            if (SequentialEngine.emergencyHalt) return;
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}