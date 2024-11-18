import java.util.Scanner;

public class Main {

    static final int MAX = 10;

    // Function to check if a system is in a safe state
    public static boolean isSafeState(int[] processes, int[] avail, int[][] max, int[][] allot, int[][] need, int n, int m) {
        int[] work = new int[MAX];
        int[] finish = new int[MAX];
        int[] safeSeq = new int[MAX];
        for (int i = 0; i < m; i++) {
            work[i] = avail[i];
        }

        for (int i = 0; i < n; i++) {
            finish[i] = 0;
        }

        int count = 0;
        while (count < n) {
            boolean found = false;
            for (int p = 0; p < n; p++) {
                if (finish[p] == 0) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (need[p][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == m) {
                        for (int k = 0; k < m; k++) {
                            work[k] += allot[p][k];
                        }
                        safeSeq[count++] = p;
                        finish[p] = 1;
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("The system is not in a safe state.");
                return false;
            }
        }

        System.out.println("The system is in a safe state.");
        System.out.print("Safe sequence is: ");
        for (int i = 0; i < n; i++) {
            System.out.print(safeSeq[i] + " ");
        }
        System.out.println();

        return true;
    }

    // Function to calculate the need matrix
    public static void calculateNeed(int[][] need, int[][] max, int[][] allot, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allot[i][j];
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.print("Enter the number of resources: ");
        int m = scanner.nextInt();

        int[] processes = new int[MAX];
        int[] avail = new int[MAX];
        int[][] max = new int[MAX][MAX];
        int[][] allot = new int[MAX][MAX];
        int[][] need = new int[MAX][MAX];

        // Reading allocation matrix
        System.out.println("Enter the allocation matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allot[i][j] = scanner.nextInt();
            }
        }

        // Reading maximum matrix
        System.out.println("Enter the maximum matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Reading available resources
        System.out.println("Enter the available resources:");
        for (int i = 0; i < m; i++) {
            avail[i] = scanner.nextInt();
        }

        // Calculate the need matrix
        calculateNeed(need, max, allot, n, m);

        // Check if the system is in a safe state
        isSafeState(processes, avail, max, allot, need, n, m);

        scanner.close();
    }
}
