/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author martin TCP client class for Workshop Booking System
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
        run();
    }

    // run method for connection to server
    private static void run() {
        //declaring and init variables
        Socket link = null;

        try {
            //assigning linkn to ip & port
            link = new Socket(host, PORT);
            //creating and init in/out put variables
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            //handle user input
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //variable for client message & server response
                String message = null;
                String response = null;
                //prompt user for input and accept
                System.out.println("Enter a command 'book/cancel/list/import' | 'STOP' to close connection...");

                message = userEntry.readLine().trim();

                //send server response
                out.println(message);
                //if to stop connection if message is stop
                if (message.equalsIgnoreCase("stop")) {
                    break;
                }                
                
                //response from server
                response = in.readLine();
                System.out.println("SERVER RECEIVED: " + response);

            }

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
