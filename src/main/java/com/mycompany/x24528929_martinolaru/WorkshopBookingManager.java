/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author martin Booking manager class where i create in memory
 * storage(concurrent hashmap), add,cancel, list methods
 */
public class WorkshopBookingManager {

    //using a hashmap of type <string, list<WorkshopBooking> to use a member name as a key and then have a list of booking objects as values
    private final ConcurrentHashMap<String, ArrayList<WorkshopBooking>> bookings = new ConcurrentHashMap<>();

    /*
    add booking method
    we will take the booking obj, check if it already exits 
    if not we will add it to the list for that member
     */
    public String addBooking(WorkshopBooking b) throws InvalidCommandException {
        //synch block to make it thread safe
        synchronized (bookings) {
            //creating a list to check members existing bookings, we get the list off the key, turning them into lower case so it doesnt mess the mapping up
            ArrayList<WorkshopBooking> existingBookings = bookings.get(b.getMemberName().toLowerCase());

            //check if there is any bookings if not, add list to put bookings in
            if (existingBookings == null) {
                //if no bookings attached to member we create a new empty list to put in
                existingBookings = new ArrayList<>();
                bookings.put(b.getMemberName().toLowerCase(), existingBookings);
            }

            //check for existing bookings in bookings list from map for that member
            for (WorkshopBooking existing : existingBookings) {
                //check existing bookings to make sure workshopname, date, time dont already exists
                if (existing.getWorkshopName().equalsIgnoreCase(b.getWorkshopName()) && existing.getDate().equalsIgnoreCase(b.getDate()) && existing.getTime().equalsIgnoreCase(b.getTime())) {
                    throw new InvalidCommandException("ERROR: " + b.getMemberName() + " already has the booking: " + b.getWorkshopName() + " on " + b.getDate() + " at " + b.getTime() + "!");
                }
            }
            //adds new booking to the list
            existingBookings.add(b);
            //if else to correctly format booking / bookings. if more than 1, it will return the string with "BOOKINGS", if 1 it will return "BOOKING"
            if (existingBookings.size() > 1) {
                //loop to add all the workshops for a certain member to be returned as a string if more than 1
                String listOfBookings = b.getMemberName() + " currently has " + existingBookings.size() + " bookings: ";
                for (WorkshopBooking listResult : existingBookings) {
                    listOfBookings += listResult.listWorkshops() + "; ";
                }
                //using sub string  to remove the trailing "; "                
                return listOfBookings.substring(0, listOfBookings.length() - 2);
            } else {
                return b.getMemberName() + " currently has " + existingBookings.size() + " booking: " + b.listWorkshops();
            }

        }
    }

    public String cancelBooking(String name, String workshop, String date, String time) {
        return "";

    }

    //list all bookings, returns clear msg if no bookings, or returns list of bookings for that member
    public String listBookings(String name) {
        //synch block to make it thread safe
        synchronized (bookings) {
            //creating an array list from the values (array list of workshop bookings ) from the assosciated key
            ArrayList<WorkshopBooking> existingBookings = bookings.get(name.toLowerCase());

            //if/else to return clear if no bookings for that member or all the listings
            if (existingBookings == null || existingBookings.isEmpty()) {
                return "";
            } else {
                //nested if/else to correctly format booking/s
                if (existingBookings.size() > 1) {
                    String listOfBookings = name + " has " + existingBookings.size() + " bookings: ";
                    for (WorkshopBooking listResult : existingBookings) {
                        listOfBookings += listResult.listWorkshops() + "; ";
                    }
                    //using sub string  to remove the trailing "; "
                    return listOfBookings.substring(0, listOfBookings.length() - 2);
                } else {
                    String listOfBookings = name + " has " + existingBookings.size() + " booking: ";
                    for (WorkshopBooking listResult : existingBookings) {
                        listOfBookings += listResult.listWorkshops() + "; ";
                    }
                    //using sub string  to remove the trailing "; "
                    return listOfBookings.substring(0, listOfBookings.length() - 2);
                }

            }
        }

    }

}
