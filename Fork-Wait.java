import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Take input for array size and elements
        System.out.print("Enter the number of elements in the array: ");
        int n = scanner.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter the elements of the array: ");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Create a child thread to simulate a child process
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Child process started.");
                // Child process sorts the array
                Arrays.sort(arr);
                System.out.println("Child process sorted array: " + Arrays.toString(arr));

                // Simulate child becoming a zombie by finishing execution
                try {
                    Thread.sleep(1000); // Delay to simulate zombie state
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Child process finished.");
            }
        });

        // Start the child process
        childThread.start();

        // Simulate the parent process doing some work (e.g., sorting)
        System.out.println("Parent process started.");
        bubbleSort(arr);
        System.out.println("Parent process sorted array: " + Arrays.toString(arr));

        // Wait for the child process to finish
        childThread.join();  // Simulates wait() in Unix

        System.out.println("Parent process finished.");
    }

    // Bubble sort algorithm
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
