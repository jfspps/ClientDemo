package com.jfspps.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TCP_Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)){
            // set the client timeout (server is currently 5000 ms)
            socket.setSoTimeout(10000);

            // setup a stream response from the server
            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;

            do {
                System.out.println("Enter a string to be echoed (type exit to exit;\nsubsequent input will be printed shortly): ");
                echoString = scanner.nextLine();

                // send the input to the server
                stringToEcho.println(echoString);
                if (!echoString.equals("exit")){
                    // get the server's response
                    response = echoes.readLine();
                    System.out.println(response);

                    // get the server's echo (this is blocking for this client only)
                    response = echoes.readLine();
                    System.out.println(response);
                }
            } while (!echoString.equals("exit"));
        } catch (SocketException e){
            System.out.println("Socket timeout error: " + e.getMessage());
        } catch (IOException exception) {
            System.out.println("Client error thrown: " + exception.getMessage());
        }
    }
}
