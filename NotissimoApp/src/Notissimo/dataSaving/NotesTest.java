package Notissimo.dataSaving;

import Notissimo.UtilityValues;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class NotesTest {

    public static ArrayList<String> notesList = readNotes();
    public static ArrayList<String> monthList = readMonths();
    public static ArrayList<String> dayList = readDays();
    public static ArrayList<String> yearList = readYears();

    public static void main(String[] args) {

        // Add an item
        Scanner in = new Scanner(System.in);

        // for reference
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        String input;

        do {

            // Read Data
            //printList(notesList);
            printNotes();

            System.out.print("Please enter an item: ");

            System.out.print("'" + UtilityValues.ADD_COMMAND +  "' or '" + UtilityValues.REMOVE_COMMAND + "' ('" + UtilityValues.END_COMMAND + "' to end program): ");
            input = in.nextLine();
            if (input.equals(UtilityValues.ADD_COMMAND)) {

                System.out.print("Enter note: ");
                notesList.add(in.nextLine());

                int month;

                do {
                    System.out.print("Enter month # 1-12: ");
                    String monthStr = in.nextLine();
                    month = Integer.parseInt(monthStr);
                } while (!(month >= 1 && month <= 12));

                monthList.add(months[month - 1]);
                System.out.print("Enter day: ");
                dayList.add(in.nextLine());
                System.out.print("Enter year: ");
                yearList.add(in.nextLine());

            } else if (input.equals(UtilityValues.REMOVE_COMMAND)) {

                System.out.print("Enter Index: ");
                int index = in.nextInt();
                notesList.remove(index);
                monthList.remove(index);
                dayList.remove(index);
                yearList.remove(index);

            } else if (input.equals(UtilityValues.END_COMMAND)) {
                System.out.println("Ending Program");
            } else if (input.equals("reset")) {
                notesList.clear();
                monthList.clear();
                dayList.clear();
                yearList.clear();
            } else {
                System.out.println("Invalid Command");
            }

            // Save data
            writeNotes(notesList);
            writeMonths(monthList);
            writeDays(dayList);
            writeYears(yearList);
        } while (!input.equals(UtilityValues.END_COMMAND));

        in.close();
    }

    private static ArrayList<String> readNotes() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Create the Scanner with the data file
            Scanner fileIn = new Scanner(new File("notes.txt"));
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

    private static ArrayList<String> readMonths() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Create the Scanner with the data file
            Scanner fileIn = new Scanner(new File("months.txt"));
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

    private static ArrayList<String> readDays() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Create the Scanner with the data file
            Scanner fileIn = new Scanner(new File("days.txt"));
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

    private static ArrayList<String> readYears() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            // Create the Scanner with the data file
            Scanner fileIn = new Scanner(new File("years.txt"));
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

    private static void writeNotes(ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter("notes.txt");
            for (String item : list) {
                fileOut.println(item);
            }
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("Unable to write");
        }
    }

    private static void writeMonths(ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter("months.txt");
            for (String item : list) {
                fileOut.println(item);
            }
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("Unable to write");
        }
    }

    private static void writeDays(ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter("days.txt");
            for (String item : list) {
                fileOut.println(item);
            }
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("Unable to write");
        }
    }

    private static void writeYears(ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter("years.txt");
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
        System.out.println("Current Notes: ");
        if (l.size() > 0) {
            for (String item : l) {
                System.out.println(item);
            }
        }
        System.out.println();
    }

    public static void printNotes() {
        System.out.println("Current Notes: ");
        if (!notesList.isEmpty()) {
            try {
                for (int i = 0; i < notesList.size(); i++) {
                    System.out.println(notesList.get(i) + " - " + monthList.get(i) + " " + dayList.get(i) + ", " + yearList.get(i));
                }
            } catch (Exception e) {
                System.out.println("Unable to write");
            }
        }
        System.out.println();
    }

}