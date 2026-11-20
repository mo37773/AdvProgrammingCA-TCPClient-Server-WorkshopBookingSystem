/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

/**
 *
 * @author martin
 * Booking object 
 */
public class WorkshopBooking {
    private int id;
    private String memberName;
    private String workshopName;
    private String date;
    private String time;
    
    //loaded constructor
    public WorkshopBooking(String memberName, String workshopName, String date, String time){
        this.memberName = memberName;
        this.workshopName = workshopName;
        this.date = date;
        this.time = time;
    }
    //no args constructor
    public WorkshopBooking(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //method for listing workshops to implement in booking manager
    public String listWorkshops() {
        return workshopName + " on " + date + " at " + time;
    }


    
    
    
}
