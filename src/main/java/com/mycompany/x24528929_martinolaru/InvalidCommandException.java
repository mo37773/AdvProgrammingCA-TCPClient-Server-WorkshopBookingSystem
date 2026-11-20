/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

/**
 *
 * @author martin
 * 
 */
public class InvalidCommandException extends Exception{
    
    //invalid command exception class to throw custom error for failed validation checks
    public InvalidCommandException(String message){
        super(message);
    }
    
}
