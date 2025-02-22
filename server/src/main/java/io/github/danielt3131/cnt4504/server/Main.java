package io.github.danielt3131.cnt4504.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int port;
        try {
                System.out.println("Enter in a port for the server to listen to");
                Scanner scanner = new Scanner(System.in);
                port = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Womp womp try again");
            throw e;
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
           int i = 0;
            while (true) {
                ServerThread serverInstance = new ServerThread(serverSocket.accept());
                Thread serverThread = new Thread(serverInstance);
                serverThread.start();
                System.out.println(i);
                i++;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}