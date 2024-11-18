import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival time
    int burstTime; // Burst time
    int remainingTime; // Remaining burst time
    int completionTime; // Completion time
    int waitingTime; // Waiting time
    int turnAroundTime; // Turn around time

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // Initially, remaining time equals burst time
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the arrival time and burst time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Variables to track time and process execution
        int currentTime = 0;
        int completed = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        while (completed < n) {
            // Find the process with the shortest remaining time that has arrived
            int minTime = Integer.MAX_VALUE;
            int shortestProcess = -1;

            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0 && processes[i].remainingTime < minTime) {
                    minTime = processes[i].remainingTime;
                    shortestProcess = i;
                }
            }

            if (shortestProcess == -1) {
                // No process is ready to execute, advance time
                currentTime++;
                continue;
            }

            // Execute the selected process for 1 time unit
            processes[shortestProcess].remainingTime--;
            currentTime++;

            // If the process is completed
            if (processes[shortestProcess].remainingTime == 0) {
                completed++;
                processes[shortestProcess].completionTime = currentTime;
                processes[shortestProcess].turnAroundTime = processes[shortestProcess].completionTime - processes[shortestProcess].arrivalTime;
                processes[shortestProcess].waitingTime = processes[shortestProcess].turnAroundTime - processes[shortestProcess].burstTime;

                totalWaitingTime += processes[shortestProcess].waitingTime;
                totalTurnAroundTime += processes[shortestProcess].turnAroundTime;
            }
        }

        // Output results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.println(process.pid + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +
                    process.completionTime + "\t\t" + process.waitingTime + "\t\t" + process.turnAroundTime);
        }

        System.out.println("\nAverage waiting time: " + (float) totalWaitingTime / n);
        System.out.println("Average turnaround time: " + (float) totalTurnAroundTime / n);

        scanner.close();
    }
}
