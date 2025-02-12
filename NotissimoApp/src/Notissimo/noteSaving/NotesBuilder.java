package Notissimo.noteSaving;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Object for a NotesBuilder to save notes along with dates
 */
public class NotesBuilder {

    private ArrayList<String> notesList;
    private ArrayList<String> monthList;
    private ArrayList<String> dayList;
    private ArrayList<String> yearList;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Default constructor to instantiate the array lists
     */
    public NotesBuilder() {
        notesList = readNotes();
        monthList = readMonths();
        dayList = readDays();
        yearList = readYears();
    }

    /**
     * returns how many notes
     * @return - length of note list size
     */
    public int length() {
        return notesList.size();
    }

    /**
     * returns the note at index i
     * @param i - index
     * @return - note
     */
    public String getNote(int i) {
        return notesList.get(i);
    }

    /**
     * returns the month at index i
     * @param i - index
     * @return - month
     */
    public String getMonth(int i) {
        return months[i];
    }

    /**
     * returns the day at index i
     * @param i - index
     * @return - day
     */
    public String getDay(int i) {
        return dayList.get(i);
    }

    /**
     * returns the year at index i
     * @param i - index
     * @return - year
     */
    public String getYear(int i) {
        return yearList.get(i);
    }

    /**
     * reading the notes to put in the notesList array
     * @return - notes array
     */
    private ArrayList<String> readNotes() {
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

    /**
     * reading the months to put in the monthsList array
     * @return - months array
     */
    private ArrayList<String> readMonths() {
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

    /**
     * reading the days to put in the daysList array
     * @return - days array
     */
    private ArrayList<String> readDays() {
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

    /**
     * reading the years to put in the yearsList array
     * @return - years array
     */
    private ArrayList<String> readYears() {
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

    /**
     * write the notesList onto the txt file
     * @param list - list to be written
     */
    private void writeNotes(ArrayList<String> list) {
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

    /**
     * write the monthsList onto the txt file
     * @param list - list to be written
     */
    private void writeMonths(ArrayList<String> list) {
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

    /**
     * write the daysList onto the txt file
     * @param list - list to be written
     */
    private void writeDays(ArrayList<String> list) {
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

    /**
     * write the yearsList onto the txt file
     * @param list - list to be written
     */
    private void writeYears(ArrayList<String> list) {
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

    /**
     * prints any list onto system output
     * @param l - list to be printed
     */
    private void printList(ArrayList<String> l) {
        System.out.println("Current Notes: ");
        if (!l.isEmpty()) {
            for (String item : l) {
                System.out.println(item);
            }
        }
        System.out.println();
    }

    /**
     * adds a note to the lists
     * to write on txt file, please use write()
     * @param note - note
     * @param month - month
     * @param day - day
     * @param year - year
     */
    public void addNote(String note, int month, int day, int year) {
        // first add to list
        notesList.add(note);
        // check if month is in range 1-12
        month = Math.min(month, 12);
        month = Math.max(month, 1);
        monthList.add(months[month - 1]);
        dayList.add(String.valueOf(day));
        yearList.add(String.valueOf(year));
    }

    /**
     * removes a note at a certain index
     * @param index - index at which note should be removed
     */
    public void removeNote(int index) {
        notesList.remove(index);
        monthList.remove(index);
        dayList.remove(index);
        yearList.remove(index);
    }

    /**
     * MUST USE IN ORDER TO WRITE ONTO TXT FILE AND SAVE NOTES
     */
    public void write() {
        // then add to txt file
        writeNotes(notesList);
        writeMonths(monthList);
        writeDays(dayList);
        writeYears(yearList);
    }

    /**
     * CLEARS ALL NOTES
     */
    public void clear() {
        notesList.clear();
        monthList.clear();
        dayList.clear();
        yearList.clear();
    }

    /**
     * prints the notes onto system out in format
     */
    public void printNotes() {
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