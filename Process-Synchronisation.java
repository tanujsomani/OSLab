import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static final Object mutex = new Object(); // Mutex for critical section
    private static Queue<Integer> buffer = new LinkedList<>(); // Shared buffer
    private static Semaphore emptySlots; // Semaphore for empty slots
    private static Semaphore fullSlots; // Semaphore for full slots

    public static void main(String[] args) {
        int bufferSize, numProducers, numConsumers, numItems;

        // User input for buffer size, number of producers, consumers, and items
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Enter buffer size: ");
        bufferSize = sc.nextInt();
        System.out.print("Enter the number of producers: ");
        numProducers = sc.nextInt();
        System.out.print("Enter the number of consumers: ");
        numConsumers = sc.nextInt();
        System.out.print("Enter the total number of items to produce and consume: ");
        numItems = sc.nextInt();

        // Initialize semaphores
        emptySlots = new Semaphore(bufferSize); // Initially, all buffer slots are empty
        fullSlots = new Semaphore(0); // Initially, no buffer slots are full

        // Calculate the number of items each producer and consumer will handle
        int itemsPerProducer = numItems / numProducers;
        int itemsPerConsumer = numItems / numConsumers;

        // Create and start producer and consumer threads
        Thread[] producers = new Thread[numProducers];
        Thread[] consumers = new Thread[numConsumers];

        for (int i = 0; i < numProducers; i++) {
            producers[i] = new Thread(new Producer(i + 1, itemsPerProducer));
            producers[i].start();
        }

        for (int i = 0; i < numConsumers; i++) {
            consumers[i] = new Thread(new Consumer(i + 1, itemsPerConsumer));
            consumers[i].start();
        }

        // Wait for all threads to finish
        try {
            for (int i = 0; i < numProducers; i++) {
                producers[i].join();
            }
            for (int i = 0; i < numConsumers; i++) {
                consumers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Producer thread class
    static class Producer implements Runnable {
        int id;
        int numItems;

        Producer(int id, int numItems) {
            this.id = id;
            this.numItems = numItems;
        }

        @Override
        public void run() {
            for (int i = 0; i < numItems; i++) {
                try {
                    Thread.sleep(500); // Simulate production time
                    int item = i + 1;

                    emptySlots.waitForSignal(); // Wait for an empty slot

                    synchronized (mutex) {
                        buffer.add(item); // Produce an item and add it to the buffer
                        System.out.println("Producer " + id + " produced item " + item);
                    }

                    fullSlots.signal(); // Signal that a full slot is available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Consumer thread class
    static class Consumer implements Runnable {
        int id;
        int numItems;

        Consumer(int id, int numItems) {
            this.id = id;
            this.numItems = numItems;
        }

        @Override
        public void run() {
            for (int i = 0; i < numItems; i++) {
                try {
                    Thread.sleep(1000); // Simulate consumption time

                    fullSlots.waitForSignal(); // Wait for a full slot

                    synchronized (mutex) {
                        int item = buffer.poll(); // Consume an item from the buffer
                        System.out.println("Consumer " + id + " consumed item " + item);
                    }

                    emptySlots.signal(); // Signal that an empty slot is available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Semaphore class to mimic the behavior of semaphores in C++
    static class Semaphore {
        private int signals = 0;

        Semaphore(int initial) {
            this.signals = initial;
        }

        synchronized void waitForSignal() throws InterruptedException {
            while (signals == 0) {
                wait();
            }
            signals--;
        }

        synchronized void signal() {
            signals++;
            notify();
        }
    }
}
