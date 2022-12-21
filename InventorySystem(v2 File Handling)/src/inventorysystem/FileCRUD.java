/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

/**
 *
 * @author Melva
 */

import java.io.*;

public class FileCRUD {
    
    public String fileName = "items.txt";
    final int SIZE = 200;
   // #define fileName "items.txt", if its in C
   // #define SIZE 200, if its in C

    public String[][] displayItems() {
        /*Selects all records from the file (items are separated using \t)*/
        String[][] itemsList = new String[SIZE][6];
        String temp;
        try {
            BufferedReader itemFile = new BufferedReader(new FileReader(fileName));
            int x;
            for(x=0; (temp = itemFile.readLine()) != null; x++){
                itemsList[x] = temp.split("\t");
            }
            itemFile.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return itemsList;
    }

    public String[][] searchItems(String text) {
        text = text.toLowerCase();
        /*Selects records whose contents match a given search query (name, brand, category column - implementing SQL LIKE mechanics)*/
        String[][] itemsList = new String[SIZE][6];
        String temp;
        String[] tempItem;
        boolean found;
        try {
            BufferedReader itemFile = new BufferedReader(new FileReader(fileName));
            int x;
            for(x=0; (temp = itemFile.readLine()) != null;){
                tempItem = temp.split("\t");
                
                //check matches
                found = false;
                found = tempItem[0].toLowerCase().contains(text) || //item
                        tempItem[1].toLowerCase().contains(text) || //brand
                        tempItem[2].toLowerCase().contains(text) || //category
                        tempItem[5].toLowerCase().contains(text); //store location
                if(found == true){
                    itemsList[x] = tempItem;
                    x++;
                }
            }
            itemFile.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return itemsList;
    }

    public String[][] displayCritical() {
        /*Selects all records from the file (items are separated using \t)*/
        String[][] itemsList = new String[SIZE][6];
        String temp;
        String[] tempItem;
        try {
            BufferedReader itemFile = new BufferedReader(new FileReader(fileName));
            int x;
            for(x=0; (temp = itemFile.readLine()) != null;){
                tempItem = temp.split("\t");
                if(Integer.parseInt(tempItem[3]) < Integer.parseInt(tempItem[4])){ //quantity available less than quantity to maintain
                    itemsList[x] = tempItem;
                    x++;
                }
            }
            itemFile.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return itemsList;
    }

    public void insertItem(String[] newItem) {
        /*Inserts a new item record to the file (duplicates allowed)*/
        try {
            PrintWriter newFile = new PrintWriter(new FileWriter("items.txt",true));
            String temp = newItem[0]+"\t"+newItem[1]+"\t"+newItem[2]+"\t"+newItem[3]+"\t"+newItem[4]+"\t"+newItem[5];
            newFile.println(temp);
            newFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateItem(int id, String[] newItem) {
        /*Updates an existing item record to the file*/
        String[] itemsList = new String[SIZE];
        String temp;
        boolean found=false;
        try {
            BufferedReader itemFile = new BufferedReader(new FileReader(fileName));
            int x, y;
            for(x=0; (temp = itemFile.readLine()) != null; x++){
                if(found==false && x+1==id){ //item to update is found
                    found=true;
                    itemsList[x] = newItem[0]+"\t"+newItem[1]+"\t"+newItem[2]+"\t"+newItem[3]+"\t"+newItem[4]+"\t"+newItem[5];
                } else {
                    itemsList[x] = temp;
                }
            }
            itemFile.close();
            
            //overwrite the file
            PrintWriter newFile = new PrintWriter(new FileWriter("items.txt"));
            for (y = 0; itemsList[y] != null; y++) {
                newFile.println(itemsList[y]);
            }    

            newFile.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void deleteItem(int id) {
        /*Removes an existing item record from the file*/
        String[] itemsList = new String[SIZE];
        String temp;
        boolean found=false;
        try {
            BufferedReader itemFile = new BufferedReader(new FileReader(fileName));
            int x, y;
            for(x=0; (temp = itemFile.readLine()) != null;){
                if(found==false && x+1==id){ //item to delete is found
                    found=true;
                } else {
                    itemsList[x] = temp;
                    x++;
                }
            }
            itemFile.close();
            
            //overwrite the file
            PrintWriter newFile = new PrintWriter(new FileWriter("items.txt"));
            for (y = 0; itemsList[y] != null; y++) {
                newFile.println(itemsList[y]);
            }    

            newFile.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
