import java.util.Scanner;

public class Main {

    // Function to find the waiting time for all processes
    static void findWaitingTime(int[] processes, int n, int[] bt, int[] wt) {
        // Waiting time for the first process is 0
        wt[0] = 0;

        // Calculating waiting time
        for (int i = 1; i < n; i++) {
            wt[i] = bt[i - 1] + wt[i - 1];
        }
    }

    // Function to calculate turn around time
    static void findTurnAroundTime(int[] processes, int n, int[] bt, int[] wt, int[] tat) {
        // Calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }

    // Function to calculate average time
    static void findAvgTime(int[] processes, int n, int[] bt) {
        int[] wt = new int[n];
        int[] tat = new int[n];
        int total_wt = 0, total_tat = 0;

        // Function to find waiting time of all processes
        findWaitingTime(processes, n, bt, wt);

        // Function to find turn around time for all processes
        findTurnAroundTime(processes, n, bt, wt, tat);

        // Display processes along with all details
        System.out.println("\nProcesses  Burst time  Waiting time  Turn around time");

        // Calculate total waiting time and total turn around time
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + processes[i] + "\t\t" + bt[i] + "\t    " + wt[i] + "\t\t   " + tat[i]);
        }

        System.out.println("\nAverage waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    // Driver code
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Create arrays for processes and burst times
        int[] processes = new int[n];
        int[] burst_time = new int[n];

        // Input process IDs and burst times
        for (int i = 0; i < n; i++) {
            processes[i] = i + 1; // Process IDs start from 1
            System.out.print("Enter the burst time for process " + processes[i] + ": ");
            burst_time[i] = scanner.nextInt();
        }

        // Calculate and display average time
        findAvgTime(processes, n, burst_time);

        scanner.close();
    }
}


