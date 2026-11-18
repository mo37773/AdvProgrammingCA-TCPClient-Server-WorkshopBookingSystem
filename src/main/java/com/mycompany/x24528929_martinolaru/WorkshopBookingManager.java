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
    private static ConcurrentHashMap<String, ArrayList<WorkshopBooking>> bookings = new ConcurrentHashMap<>();

    private String addBooking(String name, String workshop, String date, String time) {
        return "";
    }

    private String cancelBooking(String name, String workshop, String date, String time) {
        return "";

    }

    private String listBookings(String name) {
        return "";
    }

}
