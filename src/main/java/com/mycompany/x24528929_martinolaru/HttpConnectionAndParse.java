/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.x24528929_martinolaru;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 * class for HttpConnection and parsing fields
 */
public class HttpConnectionAndParse {
    //declaring variables, will use a list to return the valid commands to client
    private final String importedUrl;
    private final List<String> listOfCommands = new ArrayList<>();
    private int skippedCount;

    public HttpConnectionAndParse(String importedUrl) {
        this.importedUrl = importedUrl;
    }

    public List<String> connectAndParse() throws InvalidCommandException {
        //this is to clear the list and skip count everytime i make a new connection
        listOfCommands.clear();
        skippedCount = 0;
        BufferedReader in = null;
        
        //try catch to connection to the imported url, catches malformed or i/o exceptions
        try {
            URL url = new URL(importedUrl);
            //open connection
            URLConnection c = url.openConnection();
            //get the input from the url
            in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            //first line variable to check for the name
            String firstLine = in.readLine();
            //if the first line is null or does not start with 'member=' throw invalidcommand exception
            if (firstLine == null || !firstLine.trim().toLowerCase().startsWith("member=")) {
                throw new InvalidCommandException("ERROR: First line must be 'member=<name>'");
            }
            //here we get the name after member= using the substring method
                String name = firstLine.trim().substring(7).trim();
                //if there is no name we throw an exception
                if(name.isEmpty() || name.equalsIgnoreCase("-")){
                    throw new InvalidCommandException("ERROR: First line include a name after 'member='..");
                }
                //linr variable for our while loop to parse through each line
                String line;
                while ((line = in.readLine()) != null) {
                    //if the line is blank we continue to the next one
                    if(line.isBlank()){
                        continue;
                    }
                    // for each line we split the input into an array using .split(), the -1 gets rid of any trailing semi colons
                    String[] importedCommands = line.split(";", -1);
                    //check the input array to make sure it is only 3 fields long
                    if (importedCommands.length == 3) {
                        //create variabkes for each field trimming any whitespace
                        String workshop = importedCommands[0].trim();
                        String date = importedCommands[1].trim();
                        String time = importedCommands[2].trim();
                        //check that the variables are not empty or have a hypen and then add to list
                        if(!workshop.isEmpty() && !workshop.equals("-") && !date.isEmpty() && !date.equals("-") && !time.isEmpty() && !time.equals("-")){
                            listOfCommands.add("book;" + name + ";" + workshop + ";" + date + ";" + time);                
                        }
                        //if a field is empty, it prints error msg and then increments the skipcount
                        else{
                            System.out.println("ERROR: skipped line because it's missing a field! --- " + line);
                            skippedCount++;
                        }

                    }
                    //if the input line is not 3 fields exactly it skips and gives this error msg
                    else{
                        System.out.println("ERROR: skipped line because it did not meet the format of 'workshop;date;time' --- " + line);
                        skippedCount++;
                    }

                }
            

        } catch (MalformedURLException e) {
            throw new InvalidCommandException("ERROR: Malformed URL!!!");
        } catch (IOException e1) {
            throw new InvalidCommandException("ERROR: Invalid I/O!!!");
        }
        finally{
            //makes sure the link is not null before closing to prevent nullpointerexception
            if(in != null){
                try{
                    in.close();
                }
                catch(IOException e){
                    System.out.println("Unable to close URL stream!");
                }
            }
        }//return the list
        return listOfCommands;
    }
    //method for getting skipcount
    public int getSkippedCount() {
        return skippedCount;
    }
}
