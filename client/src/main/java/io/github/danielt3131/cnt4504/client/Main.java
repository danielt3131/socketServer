package io.github.danielt3131.cnt4504.client;

import java.io.*;
import java.time.Instant;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        if (args.length == 2 && args[1].equals("-b")) {
            String[] line = args[0].split(":");
            for (int k = 0; k < 2; k++) {
                for (int i = 1; i <= 6; i++) {
                    for (int j = 0; j <= 25; j += 5) {
                        if (j == 0) {
                            processRequests(i, 1, line[0], Integer.parseInt(line[1]) + k, "result-" + k + ".txt");
                        } else {
                            processRequests(i, j, line[0], Integer.parseInt(line[1]) + k, "result-" + k + ".txt");
                        }
                    }
                }
            }
        } else {
            System.out.println("Enter in the server ip and port");
            Scanner console = new Scanner(System.in);
            String[] line = console.nextLine().split(":");
            System.out.println("Press 1 for get the Date and Time");
            System.out.println("Press 2 for get the system uptime");
            System.out.println("Press 3 for get the system memory usage");
            System.out.println("Press 4 to run netstat");
            System.out.println("Press 5 to get a list of active users");
            System.out.println("Press 6 to get a list of all running processes");


            int option = Integer.parseInt(console.nextLine());

            System.out.println("Enter in how many requests you like to make (threads)");
            int numThreads = Integer.parseInt(console.nextLine());
            processRequests(option, numThreads, line[0], Integer.parseInt(line[1]), "result.txt");
        }
    }

    public static void processRequests(int option, int numThreads, String address, int port, String filename)  throws IOException, InterruptedException {
        Client[] client = new Client[numThreads];
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            client[i] = new Client(address, port, option);
            threads[i] = new Thread(client[i]);
            threads[i].start();
        }
        long runtime = 0;

        // Write results to a buffer in memory then flush to a file
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
        writer.println(String.format("Mode %d with %d threads", option, numThreads));
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
            runtime += client[i].getElapsedTime();
            writer.println(String.format("Thread %d: runtime %dms", i, client[i].getElapsedTime()));
        }
        // Writes the elapsed time to file
        writer.println("Total Turn around time" + runtime + "ms");
        writer.println("Average Total Turn around time" + runtime/numThreads + "ms");
        writer.println();
        writer.close();
    }
}
