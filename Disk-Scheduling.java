import java.util.*;

public class Main {

    // Function to calculate the total seek time for SSTF (Shortest Seek Time First)
    public static int sstf(List<Integer> requests, int head) {
        int seekTime = 0;
        boolean[] visited = new boolean[requests.size()];

        for (int i = 0; i < requests.size(); i++) {
            int minDistance = Integer.MAX_VALUE;
            int index = -1;

            // Find the nearest request
            for (int j = 0; j < requests.size(); j++) {
                if (!visited[j] && Math.abs(head - requests.get(j)) < minDistance) {
                    minDistance = Math.abs(head - requests.get(j));
                    index = j;
                }
            }

            visited[index] = true;
            seekTime += minDistance;
            head = requests.get(index); // Move head to the new position
        }

        return seekTime;
    }

    // Function to calculate the total seek time for SCAN (Elevator Algorithm)
    public static int scan(List<Integer> requests, int head, int diskSize) {
        int seekTime = 0;
        requests.add(0); // Add the spindle (starting point)
        requests.add(diskSize - 1); // Add the end of the disk

        Collections.sort(requests);

        int index = 0;
        while (index < requests.size() && requests.get(index) < head) {
            index++; // Find the starting point in sorted requests
        }

        // Moving right first (away from spindle)
        for (int i = index; i < requests.size(); i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        // Moving back left to the spindle
        for (int i = index - 1; i >= 0; i--) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        return seekTime;
    }

    // Function to calculate the total seek time for C-LOOK (Circular Look Algorithm)
    public static int cLook(List<Integer> requests, int head) {
        int seekTime = 0;
        Collections.sort(requests);

        int index = 0;
        while (index < requests.size() && requests.get(index) < head) {
            index++; // Find the starting point in sorted requests
        }

        // Moving right first (away from spindle)
        for (int i = index; i < requests.size(); i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        // Jump to the first request (circular move) and move right again
        for (int i = 0; i < index; i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        return seekTime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input
        System.out.print("Enter the number of disk requests: ");
        int n = scanner.nextInt();

        List<Integer> requests = new ArrayList<>();
        System.out.print("Enter the disk requests (positions on the disk): ");
        for (int i = 0; i < n; i++) {
            requests.add(scanner.nextInt());
        }

        System.out.print("Enter the initial head position: ");
        int head = scanner.nextInt();

        System.out.print("Enter the disk size: ");
        int diskSize = scanner.nextInt();

        // Calculate and display total seek time for each algorithm
        System.out.println("\nSSTF Total Seek Time: " + sstf(requests, head));
        System.out.println("SCAN Total Seek Time: " + scan(requests, head, diskSize));
        System.out.println("C-LOOK Total Seek Time: " + cLook(requests, head));

        scanner.close();
    }
}
