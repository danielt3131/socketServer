package io.github.danielt3131.cnt4504.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private int optionSelector;
    private String host;
    private int port;
    private long elapsedTime;


    public Client(String host, int port, int optionSelector) {
        this.optionSelector = optionSelector;
        this.host = host;
        this.port = port;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            elapsedTime = System.currentTimeMillis();
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(optionSelector);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            elapsedTime = System.currentTimeMillis() - elapsedTime;

        } catch (IOException e) {
            System.err.println("Retrying connection");
            run();
        }
    }
}
