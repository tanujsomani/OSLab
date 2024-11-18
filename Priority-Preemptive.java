import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival time
    int burstTime; // Burst time
    int remainingTime; // Remaining burst time
    int priority; // Priority of the process (lower number -> higher priority)
    int completionTime; // Completion time
    int waitingTime; // Waiting time
    int turnAroundTime; // Turn around time

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
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

        // Variables to track time and process execution
        int currentTime = 0;
        int completed = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        while (completed < n) {
            // Find the process with the highest priority (lowest priority number) that has arrived
            int highestPriority = Integer.MAX_VALUE;
            int currentProcess = -1;

            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0 && processes[i].priority < highestPriority) {
                    highestPriority = processes[i].priority;
                    currentProcess = i;
                }
            }

            if (currentProcess == -1) {
                // No process is ready to execute, advance time
                currentTime++;
                continue;
            }

            // Execute the selected process for 1 time unit
            processes[currentProcess].remainingTime--;
            currentTime++;

            // If the process is completed
            if (processes[currentProcess].remainingTime == 0) {
                completed++;
                processes[currentProcess].completionTime = currentTime;
                processes[currentProcess].turnAroundTime = processes[currentProcess].completionTime - processes[currentProcess].arrivalTime;
                processes[currentProcess].waitingTime = processes[currentProcess].turnAroundTime - processes[currentProcess].burstTime;

                totalWaitingTime += processes[currentProcess].waitingTime;
                totalTurnAroundTime += processes[currentProcess].turnAroundTime;
            }
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
