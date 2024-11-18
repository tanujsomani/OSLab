import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException; // Import the InputMismatchException class

//scheduling algorithm
//Each process getting a maximum of quant time to run.
//If a process's burst time is <= to the quantum, it completes execution.
//If it exceeds the quantum, the remaining burst time is reduced,
//and the process is placed back in the queue for the next round.//ideal for time-sharing systems.

class Process {
    int AT, BT, FT, WT, TAT, pos;
    int[] ST = new int[20];

    public Process() {
        Arrays.fill(ST, -1);
    }
}

public class RoundRobin_2 {
    static int quant;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0;

        // Taking Input
        System.out.print("Enter the number of processes: ");
        while (true) {
            try {
                n = sc.nextInt();
                if (n > 0) break;
                else System.out.print("Please enter a positive integer: ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter an integer: ");
                sc.next(); // clear the invalid input
            }
        }
        Process[] p = new Process[n];

        System.out.print("Enter the quantum: ");
        while (true) {
            try {
                quant = sc.nextInt();
                if (quant > 0) break;
                else System.out.print("Please enter a positive integer: ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter an integer: ");
                sc.next(); // clear the invalid input
            }
        }

        System.out.println("Enter the process numbers: ");
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            while (true) {
                try {
                    p[i].pos = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        System.out.println("Enter the Arrival time of processes: ");
        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    p[i].AT = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        System.out.println("Enter the Burst time of processes: ");
        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    p[i].BT = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        // Declaring variables
        int c = n;
        float time = 0, mini = Float.MAX_VALUE;
        float[] b = new float[n];
        float[] a = new float[n];
        int[][] s = new int[n][20];

        // Initializing burst and arrival time arrays
        int index = -1;
        for (int i = 0; i < n; i++) {
            b[i] = p[i].BT;
            a[i] = p[i].AT;
            Arrays.fill(s[i], -1);
        }

        int tot_wt = 0, tot_tat = 0;
        boolean flag = false;

        while (c != 0) {
            mini = Float.MAX_VALUE;
            flag = false;

            for (int i = 0; i < n; i++) {
                float tempTime = time + 0.1f; // renamed variable
                if (a[i] <= tempTime && mini > a[i] && b[i] > 0) {
                    index = i;
                    mini = a[i];
                    flag = true;
                }
            }

            // if at =1 then loop gets out hence set flag to false
            if (!flag) {
                time++;
                continue;
            }

            // calculating start time
            int j = 0;
            while (s[index][j] != -1) {
                j++;
            }

            if (s[index][j] == -1) {
                s[index][j] = (int) time;
                p[index].ST[j] = (int) time;
            }

            if (b[index] <= quant) {
                time += b[index];
                b[index] = 0;
            } else {
                time += quant;
                b[index] -= quant;
            }

            if (b[index] > 0) {
                a[index] = time + 0.1f;
            }

            // calculating arrival, burst, final times
            if (b[index] == 0) {
                c--;
                p[index].FT = (int) time;
                p[index].WT = p[index].FT - p[index].AT - p[index].BT;
                tot_wt += p[index].WT;
                p[index].TAT = p[index].BT + p[index].WT;
                tot_tat += p[index].TAT;
            }
        } // end of while loop

        // Printing output
        System.out.println("Process number  Arrival time  Burst time  Start time          Final time  Wait Time  TurnAround Time");
        System.out.println("---------------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%14d%13d%11d", p[i].pos, p[i].AT, p[i].BT);
            int j = 0;
            int v = 0;
            System.out.print("  ");
            while (s[i][j] != -1) {
                System.out.printf("%4d ", p[i].ST[j]);
                j++;
                v += 3;
            }
            while (v < 40) {
                System.out.print(" ");
                v++;
            }
            System.out.printf("%11d%11d%17d%n", p[i].FT, p[i].WT, p[i].TAT);
        }

        // Calculating average wait time and turnaround time
        double avg_wt = tot_wt / (double) n;
        double avg_tat = tot_tat / (double) n;

        // Printing average wait time and turnaround time
        System.out.println("The average wait time is: " + avg_wt);
        System.out.println("The average TurnAround time is: " + avg_tat);

        sc.close();
    }
}
