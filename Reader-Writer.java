import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    // Shared resource
    static int sharedData = 0;

    // Lock for readers and writers
    static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    static Lock readLock = rwLock.readLock();
    static Lock writeLock = rwLock.writeLock();

    // Atomic boolean to stop threads
    static AtomicBoolean running = new AtomicBoolean(true);

    // Reader function
    public static void reader(int id) {
        while (running.get()) {
            // Entry section for readers
            readLock.lock(); // Readers acquire read lock
            try {
                // Reading section
                System.out.println("Reader " + id + ": Reading sharedData = " + sharedData);
                Thread.sleep(1000); // Simulate reading
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Exit section for readers
                readLock.unlock(); // Release the read lock
            }

            try {
                Thread.sleep(500); // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Writer function
    public static void writer(int id) {
        while (running.get()) {
            writeLock.lock(); // Writers acquire write lock
            try {
                // Writing section
                sharedData++;
                System.out.println("Writer " + id + ": Writing sharedData = " + sharedData);
                Thread.sleep(1000); // Simulate writing
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock(); // Release the write lock
            }

            try {
                Thread.sleep(500); // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Number of readers and writers
        int numReaders = 3, numWriters = 2;
        
        // Create reader and writer threads
        Thread[] readers = new Thread[numReaders];
        Thread[] writers = new Thread[numWriters];

        // Create reader threads
        for (int i = 0; i < numReaders; i++) {
            final int id = i + 1;
            readers[i] = new Thread(() -> reader(id));
            readers[i].start();
        }

        // Create writer threads
        for (int i = 0; i < numWriters; i++) {
            final int id = i + 1;
            writers[i] = new Thread(() -> writer(id));
            writers[i].start();
        }

        // Run for a specific time (e.g., 10 seconds) then stop
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running.set(false); // Stop all threads

        // Join threads
        try {
            for (int i = 0; i < numReaders; i++) {
                readers[i].join();
            }
            for (int i = 0; i < numWriters; i++) {
                writers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished.");
    }
}
