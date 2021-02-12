package com.jfspps.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UDP_Main {

    public static void main(String[] args){
        try {

            InetAddress address = InetAddress.getLocalHost();
            // UDP; no port number here
            DatagramSocket datagramSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String echoString;

            do {
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                byte[] buffer = echoString.getBytes();

                // this packet contains the data, the address of the client and the port number
                // (cf. Socket which stores the address and port; this is potentially unsafe??)
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5000);
                datagramSocket.send(packet);

                // get ready to receive the datagram response from the server (this is for demo purposes, see Server code)
                byte[] buffer2 = new byte[50];
                packet = new DatagramPacket(buffer2, buffer2.length);
                // receive() is blocking
                datagramSocket.receive(packet);
                System.out.println("Datagram text response from server: " + new String(buffer2));

            } while (!echoString.equals("exit"));

        } catch (SocketTimeoutException timeoutException){
            System.out.println("Socket timed out: " + timeoutException.getMessage());
        } catch (IOException e){
            System.out.println("Client IO error: " + e.getMessage());
        }
    }
}
