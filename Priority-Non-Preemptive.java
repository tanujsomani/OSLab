import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival time
    int burstTime; // Burst time
    int priority; // Priority of the process (lower number -> higher priority)
    int completionTime; // Completion time
    int waitingTime; // Waiting time
    int turnAroundTime; // Turn around time

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the arrival time, burst time, and priority for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime, priority);
        }

        // Sort by arrival time first, then by priority (lower number -> higher priority)
        Arrays.sort(processes, Comparator.comparingInt((Process p) -> p.arrivalTime)
                .thenComparingInt(p -> p.priority));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for (Process process : processes) {
            // If the CPU is idle, advance current time to the process's arrival time
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Calculate completion time, waiting time, and turnaround time
            process.completionTime = currentTime + process.burstTime;
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;

            // Update current time
            currentTime = process.completionTime;

            totalWaitingTime += process.waitingTime;
            totalTurnAroundTime += process.turnAroundTime;
        }

        // Output results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tPriority\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +
                    process.priority + "\t\t" + process.completionTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }

        System.out.println("\nAverage waiting time: " + (float) totalWaitingTime / n);
        System.out.println("Average turnaround time: " + (float) totalTurnAroundTime / n);

        scanner.close();
    }
}
