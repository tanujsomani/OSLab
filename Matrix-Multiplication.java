import java.util.Scanner;

class Main {
    static int[][] matA;
    static int[][] matB;
    static int[][] matC;
    static int MATRIX_SIZE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get matrix size from user
        System.out.print("Enter the size of the matrices (N x N): ");
        MATRIX_SIZE = scanner.nextInt();

        // Initialize matrices based on user input size
        matA = new int[MATRIX_SIZE][MATRIX_SIZE];
        matB = new int[MATRIX_SIZE][MATRIX_SIZE];
        matC = new int[MATRIX_SIZE][MATRIX_SIZE];

        // Input elements for matrix A
        System.out.println("Enter the elements of matrix A:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print("A[" + i + "][" + j + "]: ");
                matA[i][j] = scanner.nextInt();
            }
        }

        // Input elements for matrix B
        System.out.println("Enter the elements of matrix B:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print("B[" + i + "][" + j + "]: ");
                matB[i][j] = scanner.nextInt();
            }
        }

        // Create and start threads for each element in the resultant matrix
        Thread[] threads = new Thread[MATRIX_SIZE * MATRIX_SIZE];
        int threadCount = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                threads[threadCount] = new Thread(new MultiplyTask(i, j));
                threads[threadCount].start();
                threadCount++;
            }
        }

        // Wait for all threads to complete
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display the resultant matrix
        System.out.println("Resultant Matrix:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }

    // Task to perform multiplication for one element in the resultant matrix
    static class MultiplyTask implements Runnable {
        int row, col;

        MultiplyTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            matC[row][col] = 0;
            for (int k = 0; k < MATRIX_SIZE; k++) {
                matC[row][col] += matA[row][k] * matB[k][col];
            }
        }
    }
}
