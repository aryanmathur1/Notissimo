package Notissimo.noteSaving;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Object for a NotesBuilder to save notes along with dates and priorities (true/false)
 * methods:
 * internal read and write methods
 * add note
 * remove note
 * edit note
 * write()
 * clear
 * print notes
 */
public class NotesBuilder {

    private ArrayList<String> notesList;
    private ArrayList<String> monthList;
    private ArrayList<String> dayList;
    private ArrayList<String> yearList;
    private ArrayList<Boolean> priorityList;  // Changed to Boolean

    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Default constructor to instantiate the array lists
     */
    public NotesBuilder() {
        notesList = readNotes();
        monthList = readMonths();
        dayList = readDays();
        yearList = readYears();
        priorityList = readPriorities();  // Add priority reading
    }

    // Getter and Setter methods for priority list
    public boolean getPriority(int i) {
        return priorityList.get(i);
    }

    public void setPriority(int i, boolean priority) {
        priorityList.set(i, priority);
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
        return monthList.get(i);
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
     * returns the note at index i in a formatted way
     * @param i - index
     * @return - formatted note
     */
    public String getFormattedNote(int i) {
        return (notesList.get(i) + " - " + monthList.get(i) + " " + dayList.get(i) + ", " + yearList.get(i) + " - Priority: " + (priorityList.get(i) ? "High" : "Low"));
    }

    // Reading Methods

    private ArrayList<String> readNotes() {
        return readFromFile("notes.txt");
    }

    private ArrayList<String> readMonths() {
        return readFromFile("months.txt");
    }

    private ArrayList<String> readDays() {
        return readFromFile("days.txt");
    }

    private ArrayList<String> readYears() {
        return readFromFile("years.txt");
    }

    private ArrayList<Boolean> readPriorities() {
        ArrayList<Boolean> list = new ArrayList<>();
        try {
            Scanner fileIn = new Scanner(new File("priority.txt"));
            while (fileIn.hasNext()) {
                list.add(Boolean.parseBoolean(fileIn.nextLine()));  // Read as boolean
            }
            fileIn.close();
        } catch (IOException e) {
            System.out.println("No List Exists for priority.txt");
        }
        return list;
    }

    private ArrayList<String> readFromFile(String filename) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner fileIn = new Scanner(new File(filename));
            while (fileIn.hasNext()) {
                list.add(fileIn.nextLine());
            }
            fileIn.close();
        } catch (IOException e) {
            System.out.println("No List Exists for " + filename);
        }
        return list;
    }

    // Write Methods

    private void writeNotes(ArrayList<String> list) {
        writeToFile("notes.txt", list);
    }

    private void writeMonths(ArrayList<String> list) {
        writeToFile("months.txt", list);
    }

    private void writeDays(ArrayList<String> list) {
        writeToFile("days.txt", list);
    }

    private void writeYears(ArrayList<String> list) {
        writeToFile("years.txt", list);
    }

    private void writePriorities(ArrayList<Boolean> list) {
        try {
            PrintWriter fileOut = new PrintWriter("priority.txt");
            for (Boolean priority : list) {
                fileOut.println(priority);  // Write as boolean
            }
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Unable to write to priority.txt");
        }
    }

    private void writeToFile(String filename, ArrayList<String> list) {
        try {
            PrintWriter fileOut = new PrintWriter(filename);
            for (String item : list) {
                fileOut.println(item);
            }
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Unable to write to " + filename);
        }
    }

    // Method to write all lists to files
    public void write() {
        writeNotes(notesList);
        writeMonths(monthList);
        writeDays(dayList);
        writeYears(yearList);
        writePriorities(priorityList);  // Add priority writing
    }

    // Add, Edit, Remove Notes Methods

    public void addNote(String note, int month, int day, int year, boolean priority) {
        notesList.add(note);
        monthList.add(String.valueOf(Math.min(month, 12)));
        dayList.add(String.valueOf(day));
        yearList.add(String.valueOf(year));
        priorityList.add(priority);  // Add priority
    }

    public boolean editNote(int index, String note, int month, int day, int year, boolean priority) {
        if (notesList.size() > index) {
            notesList.set(index, note);
            monthList.set(index, months[Math.min(month, 12) - 1]);
            dayList.set(index, String.valueOf(day));
            yearList.set(index, String.valueOf(year));
            priorityList.set(index, priority);  // Edit priority
            return true;
        } else {
            return false;
        }
    }

    public boolean removeNote(int index) {
        if (index != -1) {
            notesList.remove(index);
            monthList.remove(index);
            dayList.remove(index);
            yearList.remove(index);
            priorityList.remove(index);  // Remove priority
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        notesList.clear();
        monthList.clear();
        dayList.clear();
        yearList.clear();
        priorityList.clear();  // Clear priority list
    }

    public void printNotes() {
        System.out.println("Current Notes: ");
        if (!notesList.isEmpty()) {
            for (int i = 0; i < notesList.size(); i++) {
                System.out.println(notesList.get(i) + " - " + monthList.get(i) + " " + dayList.get(i) + ", " + yearList.get(i) + " - Priority: " + (priorityList.get(i) ? "High" : "Low"));
            }
        }
        System.out.println();
    }

    public void read() {
        notesList = readNotes();
        monthList = readMonths();
        dayList = readDays();
        yearList = readYears();
        priorityList = readPriorities();  // Add priority reading
    }

}
