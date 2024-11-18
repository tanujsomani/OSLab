import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid; // Process ID
    int burstTime; // Burst time of the process
    int waitingTime; // Waiting time of the process
    int turnAroundTime; // Turn around time of the process
    
    public Process(int pid, int burstTime) {
        this.pid = pid;
        this.burstTime = burstTime;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        // Sort processes by burst time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.burstTime));

        // Calculate waiting time and turn around time
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        processes[0].waitingTime = 0;
        processes[0].turnAroundTime = processes[0].burstTime;
        totalTurnAroundTime += processes[0].turnAroundTime;

        for (int i = 1; i < n; i++) {
            processes[i].waitingTime = processes[i - 1].turnAroundTime;
            processes[i].turnAroundTime = processes[i].waitingTime + processes[i].burstTime;

            totalWaitingTime += processes[i].waitingTime;
            totalTurnAroundTime += processes[i].turnAroundTime;
        }

        // Output results
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println(process.pid + "\t\t" + process.burstTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }

        System.out.println("\nAverage waiting time: " + (float) totalWaitingTime / n);
        System.out.println("Average turnaround time: " + (float) totalTurnAroundTime / n);

        scanner.close();
    }
}
