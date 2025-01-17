package Notissimo.dataSaving;

import Notissimo.UtilityValues;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class NotesTest {

    public static ArrayList<String> myList = readData();

    public static void main(String[] args) {

        // Add an item
        Scanner in = new Scanner(System.in);

        String input;

        do {

            // Read Data
            printList(myList);

            System.out.print("Please enter an item: ");

            System.out.print("'" + UtilityValues.ADD_COMMAND +  "' or '" + UtilityValues.REMOVE_COMMAND + "' ('" + UtilityValues.END_COMMAND + "' to end program): ");
            input = in.nextLine();
            if (input.equals(UtilityValues.ADD_COMMAND)) {

                System.out.print("Enter note: ");
                myList.add(in.nextLine());

            } else if (input.equals(UtilityValues.REMOVE_COMMAND)) {
                System.out.print("Enter Index: ");
                myList.remove(in.nextInt());

            } else if (input.equals(UtilityValues.END_COMMAND)) {
                System.out.println("Ending Program");
            } else {
                System.out.println("Invalid Command");
            }

            // Save data
            writeData(myList);
        } while (!input.equals("cancel"));

        in.close();
    }

    private static ArrayList<String> readData(){
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Create the Scanner with the data file
            Scanner fileIn = new Scanner(new File("data.txt"));
            while (fileIn.hasNext()) {
                // Add to the ArrayList
                list.add(fileIn.nextLine());
            }
            fileIn.close();
        }
        catch (IOException e) {
            System.out.println("No List Exists.");
        }
        return list;
    }

    private static void writeData(ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter("data.txt");
            for (String item : list) {
                fileOut.println(item);
            }
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("Unable to write");
        }
    }

    private static void printList(ArrayList<String> l) {
        if (l.size() > 0) {
            System.out.println("Current Notes: ");
            for (String item : l) {
                System.out.println(item);
            }
            System.out.println();
        }

    }
}