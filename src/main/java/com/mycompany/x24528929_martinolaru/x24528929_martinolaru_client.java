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
import java.util.List;

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
            //let the user know the formats to input, i put it outside the loop so it is shown only once to keep the output console cleaner

            System.out.println("---'book/cancel' format : 'book/cancel ; member  name ; workshop name ; date ; time' ");
            System.out.println("---'list' format : 'list ; member  name ; - ; - ; -' ");
            System.out.println("---'import' format : 'import ;<public-URL-to-bookings.txt>  ");
            //handle user input
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //variable for client message & server response
                String message = null;
                String response = null;
                //prompt user for input and accept
                //print to console to let user know the commands
                System.out.println("Enter a command 'book/cancel/list/import' | 'STOP' to close connection...");
                message = userEntry.readLine().trim();
                //if to handle clients import command
                if (message.toLowerCase().startsWith("import")) {
                    try {
                        //split the input into 2 fields for import
                        String[] importCommand = message.split(";", 2);
                        //check if input is less than 2 fields, throw exception
                        if (importCommand.length < 2) {
                            throw new InvalidCommandException("Wrong import format! Must be 2 fields like 'import;url'");
                        }
                        //split the input into 2 variables, trim white space
                        String importAction = importCommand[0].trim();
                        String importURL = importCommand[1].trim();
                        //extra check incase input is imported and passes first check
                        if (!importAction.equalsIgnoreCase("import")) {
                            throw new InvalidCommandException("Wrong import format! Must be 'import;url'...");
                        }
                        //check to make sure the url is formatted correctly
                        if (importURL.isEmpty() || !importURL.toLowerCase().startsWith("http://") && !importURL.toLowerCase().startsWith("https://")) {
                            throw new InvalidCommandException("URL must start with 'http://' OR 'https://'...");
                        }
                        //create a httpconnectionandparse obj and pass the url
                        HttpConnectionAndParse conn = new HttpConnectionAndParse(importURL);
                        //we get the list from our method
                        List<String> commandList = conn.connectAndParse();
                        //variables for imported and skipped
                        int imported = 0;
                        int skippedCount = conn.getSkippedCount();
                        //loop to go through each command in the list, already been validated in the connectionandparse class so we can add, book was also added to the input line from that class
                        for (String command : commandList) {
                            //send to server
                            out.println(command);
                            //get reply
                            response = in.readLine();
                            System.out.println(response);
                            //this is where we count our imported and skipped, if it starts with errror it increments skipped, if not, increments imported
                            if (response != null && !response.startsWith("ERROR")) {
                                imported++;
                            } else {
                                skippedCount++;
                            }

                        }
                        //print the count of imported and skipped
                        System.out.println("Imported: " + imported + "; Skipped: " + skippedCount);

                    } catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    }
                    //if not import command, treat as normal
                } else {
                    //send server msg
                    out.println(message);
                    //if to stop connection if message is stop
                    if (message.equalsIgnoreCase("stop")) {
                        response = in.readLine();
                        System.out.println(response);

                        break;
                    }

                    //response from server
                    response = in.readLine();
                    System.out.println(response);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //check if link is null before closing to prevent null pointer exception
            if (link != null) {

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

}
