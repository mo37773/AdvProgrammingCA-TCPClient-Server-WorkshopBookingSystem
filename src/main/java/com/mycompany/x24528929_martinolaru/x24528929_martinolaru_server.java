/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author martin
 * TCP server class for Workshop booking system
 */
public class x24528929_martinolaru_server {
    //declaring variables
    private static final int PORT = 1234;
    private static  ServerSocket servSocket;
    private static int clientCount = 0;

    public static void main(String[] args){
        //try/catch to open server socket and listen for connections
        System.out.println("Opening port...\n");
        try{
            servSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT + "\n");
            }
            catch(IOException e){
                System.out.println("Unable to attach to port: " + PORT);
                System.exit(1);
        }
        //do/while loop to run the server
        do{
            run();
        }while(true);
        
    }
    //run method to handle client connections
    private static void run(){
        Socket link = null;
        //try to accept client connection and increment client count
        try{
            //accept client connection
            link = servSocket.accept();
            //increment client count
            clientCount++;
            //create new client runnable 
            ClientConnectionServer clientResource = new ClientConnectionServer("Client: " + clientCount, link);
            //create new thread using clientResource
            Thread t = new Thread(clientResource);
            //start thread
            t.start();


        }
        //catch IOException and close connection
        catch(IOException e){
            e.printStackTrace();
            try{
                System.out.println("\n* Closing connection... *");
                link.close();
            }
            catch(IOException ex){
                System.out.println("Unable to disconnect!");
                ex.printStackTrace();
            }
        }

    }
}
