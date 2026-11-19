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
    //importing workshopbookingmanager as final so it can only be instantiated once
    private final WorkshopBookingManager bookingManager = new WorkshopBookingManager();
    int bookingCountId = 1;

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
            //declaring message and reply variable            
            String message;
            //whhile loop to keep asking for input
            while (true) {
                //get message from client
                message = in.readLine();
                //if to check if msg is null to prevent nullpointerexception
                if (message == null) {
                    break;
                }
                //if to brak out of loop if stop is read
                if (message.equalsIgnoreCase("stop")) {
                    System.out.println("TERMINATE");
                    break;
                }
                //try-catch to handle input validation, throws invalid command excpetion if error is caught
                try {
                    //split the message by the semi colon, limit of 5 strings
                    String[] command = message.split(";", 5);
                    //if statement to make sure user inputs 5 fields
                    if (command.length < 5) {
                        throw new InvalidCommandException("Input command must be 5 fields, each seperated by ';'! | EXAMPLE: 'ACTION;MEMBER;WORKSHOP;DATE;TIME'...");
                    }
                    //creating and init variable off entries from the message split
                    String action = command[0].trim().toLowerCase();
                    String memberName = command[1].trim();
                    String workshop = command[2].trim();
                    String date = command[3].trim();
                    String time = command[4].trim();

                    //declaring reply variable
                    String reply;

                    //switch conditional to handle actions
                    switch (action) {
                        //book action that validates using validateBookingFields and then calls book method from workshopbooking manager
                        case "book":
                            //try/catch to catch duplicate booking?****
                            //increment every time a booking is added so we can keep track of how they came in chronologically
                            bookingCountId++;
                                                 
                            //we validate the fields first before creating the workshop booking obj
                            validateBookingFields(memberName, workshop, date, time);
                            
                            WorkshopBooking b = new WorkshopBooking(bookingCountId,memberName,workshop,date,time);
                            
                            //after booking is succesful it returns the string to confirm, if booking already exists should throw invalid command exception if booking already exists
                            reply = bookingManager.addBooking(b);
                            

                            break;
                        //cancel action that validates using validateBookingFields and then calls cancel method from workshopbooking manager
                        case "cancel":
                            validateBookingFields(memberName, workshop, date, time);
                            reply = "Cancel success";

                            break;
                        //list action that validates and then calls cancel method from workshopbooking manager                            
                        case "list":
                            if (!workshop.equals("-") || !date.equals("-") || !time.equals("-")) {
                                throw new InvalidCommandException("List command should be formatted as 'MEMBER;-;-;-");
                            }
                            //list all the workshops a certain member has booked
                            bookingManager.listBookings(memberName);
                            reply = "List success";

                            break;
                        default:
                            throw new InvalidCommandException("Action command does not exist... Accepted commands: book/cancel/list/import ");

                    }
                    //returns reply to client
                    out.println(reply);
                } catch (InvalidCommandException e) {
                    out.println(e.getMessage());
                }

                //this is just for the server console to show the message sent by client
                System.out.println("Client: " + name + ", Message Received: " + message);

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

    //method for validating booking fields that gets called when a user input his command, throws invalidcommandexceptiom
    /*
    WILL ADD MORE ADV INPUT VALIDATION HERE TO STATE WHAT VARIABLES ARE MISSING
    */
    private void validateBookingFields(String memberName, String workshop, String date, String time) throws InvalidCommandException {
        //if all fields are empty, throws corresponding error msg
        if (memberName.isEmpty() && workshop.isEmpty() && date.isEmpty() && time.isEmpty()) {
            throw new InvalidCommandException("All fields are missing!! Command must be ACTION;MEMBER;WORKSHOP;DATE;TIME");
        } //if name field is empty or "-", throws corresponding error msg
        else if (memberName.isEmpty() || memberName.equals("-")) {
            throw new InvalidCommandException("Member name cannot be empty or '-'!");
            //if workshop field is empty or "-", throws corresponding error msg
        } else if (workshop.isEmpty() || workshop.equals("-")) {
            throw new InvalidCommandException("Workshop name cannot be empty or '-' for this action!");
            //if date field is empty or "-", throws corresponding error msg
        } else if (date.isEmpty() || date.equals("-")) {
            throw new InvalidCommandException("Date cannot be empty or '-' for this action!");
            //if time field is empty or "-", throws corresponding error msg
        } else if (time.isEmpty() || time.equals("-")) {
            throw new InvalidCommandException("Time cannot be empty or '-' for this action!");
        }
    }
}
