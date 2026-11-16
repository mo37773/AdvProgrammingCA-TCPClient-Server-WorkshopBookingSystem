/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author martin
 *         TCP client class for Workshop Booking System
 */
public class x24528929_martinolaru_client {
    // declaring variables
    private static InetAddress host;
    private static final int PORT = 1234;

    // main method to get Host Ip
    public static void main(String[] args) {
        // try catch to get ip, if not successful closes app
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
    }

    // run method for connection to server
    private static void run() {
        Socket link = null;
        try {
            link = new Socket(host, PORT);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing connection...");
                link.close();

            } catch (IOException e) {
                System.out.println("Unable to close or disconnect connection!!!");
                System.exit(1);
            }
        }
    }

}
