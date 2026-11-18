/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author martin Runnable thread class for handling multiple threads and inputs
 * concurrently
 */
public class ClientConnectionServer implements Runnable {

    //declaring variables and init
    String name = null;
    Socket link = null;

    //loaded constructor 
    public ClientConnectionServer(String name, Socket link) {
        this.name = name;
        this.link = link;
    }

    //run method, overriding the one from the runnable interface 
    @Override
    public void run() {

        //try/catch to handle inputs and output
        try {
            //declaring and init buffreader & pw for input/output
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            //declaring message variable            
            String message;
            //whhile loop to keep asking for input
            while (true) {
                //get message from client
                message = in.readLine();
                //if to brak out of loop if stop is read
                if (message.equalsIgnoreCase("stop")) {
                    System.out.println("TERMINATING....");
                    break;
                }
                System.out.println("Client: " + name + ", Message Received: " + message);
                //echo message back to client to test comms
                out.println("Echo Message: " + message);
            }

        } //catch any In/output excpetions
        catch (IOException e) {
            e.printStackTrace();
            //finally to close connection after try catch
        } finally {
            //try catch that closes the link connection after stop, unless any error happens
            try {
                System.out.println("Closing connection with " + name + "...");
                link.close();
            } catch (IOException e) {
                System.out.println("Unable to disconnect!!!");
                System.exit(1);

            }
        }

    }
}
