import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;

class Process {
    int pid;         // Process ID
    int arrivalTime; // Arrival Time
    int burstTime;   // Burst Time
    int remainingTime; // Remaining Burst Time
    int waitingTime;  // Waiting Time
    int turnaroundTime; // Turnaround Time

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        // Input arrival and burst times
        System.out.println("Enter Arrival Time and Burst Time for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " - Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Process " + (i + 1) + " - Burst Time: ");
            int bt = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt);
        }

        // Input quantum time
        System.out.print("Enter the quantum time: ");
        int quantum = sc.nextInt();

        // Sort processes by arrival time
        Arrays.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        // Simulation variables
        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0; // Current simulation time
        int completed = 0;   // Number of completed processes
        float totalWT = 0;   // Total waiting time
        float totalTAT = 0;  // Total turnaround time

        // Add first process to the ready queue
        readyQueue.add(processes[0]);
        int index = 1; // Index for new processes arriving later

        // Process scheduling loop
        while (completed < n) {
            if (readyQueue.isEmpty() && index < n) {
                // Advance time to the next process arrival if the queue is empty
                currentTime = processes[index].arrivalTime;
                readyQueue.add(processes[index++]);
            }

            // Get the next process from the queue
            Process currentProcess = readyQueue.poll();

            // Check if the process has remaining burst time
            if (currentProcess.remainingTime > 0) {
                // Execute the process for the quantum or until completion
                int executionTime = Math.min(currentProcess.remainingTime, quantum);
                currentProcess.remainingTime -= executionTime;
                currentTime += executionTime;

                // Add newly arrived processes to the ready queue
                while (index < n && processes[index].arrivalTime <= currentTime) {
                    readyQueue.add(processes[index++]);
                }

                // If the process is not completed, re-add it to the queue
                if (currentProcess.remainingTime > 0) {
                    readyQueue.add(currentProcess);
                } else {
                    // Calculate waiting and turnaround times for completed processes
                    currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWT += currentProcess.waitingTime;
                    totalTAT += currentProcess.turnaroundTime;
                    completed++;
                }
            }
        }

        // Output results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            System.out.printf("%d\t\t%d\t\t%d\t\t%d\t\t%d%n", process.pid, process.arrivalTime, process.burstTime, process.waitingTime, process.turnaroundTime);
        }

        // Output average waiting and turnaround times
        System.out.printf("%nAverage Waiting Time: %.2f%n", totalWT / n);
        System.out.printf("Average Turnaround Time: %.2f%n", totalTAT / n);

        sc.close();
    }
}
       
