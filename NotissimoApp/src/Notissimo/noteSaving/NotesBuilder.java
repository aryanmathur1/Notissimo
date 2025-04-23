package Notissimo.noteSaving;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Object for a NotesBuilder to save notes along with titles, dates, and priorities (true/false)
 *
 * Methods:
 * - internal read and write methods
 * - add note
 * - remove note
 * - edit note
 * - write()
 * - clear()
 * - print notes
 */
public class NotesBuilder {

    private ArrayList<String> titlesList;  // List of note titles
    private ArrayList<String> notesList;   // List of note contents
    private ArrayList<String> monthList;   // List of months
    private ArrayList<String> dayList;     // List of days
    private ArrayList<String> yearList;    // List of years
    private ArrayList<Boolean> priorityList;  // List of priorities (true = high)

    private final String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    /**
     * Default constructor to instantiate the array lists from file
     */
    public NotesBuilder() {
        titlesList = readTitles();       // Read titles from titles.txt
        notesList = readNotes();         // Read notes from notes.txt
        monthList = readMonths();        // Read months from months.txt
        dayList = readDays();            // Read days from days.txt
        yearList = readYears();          // Read years from years.txt
        priorityList = readPriorities(); // Read priorities from priority.txt
    }

    // Getter and Setter methods for each component

    public String getTitle(int i) {
        return titlesList.get(i);
    }

    public void setTitle(int i, String title) {
        titlesList.set(i, title);
    }

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
     * returns the formatted note at index i
     * @param i - index
     * @return - formatted note
     */
    public String getFormattedNote(int i) {
        return (titlesList.get(i) + ": " + notesList.get(i) + " - " +
                monthList.get(i) + " " + dayList.get(i) + ", " + yearList.get(i) +
                " - Priority: " + (priorityList.get(i) ? "High" : "Low"));
    }

    // Internal read methods

    private ArrayList<String> readTitles() {
        return readFromFile("titles.txt");
    }

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

    // Internal write methods

    private void writeTitles(ArrayList<String> list) {
        writeToFile("titles.txt", list);
    }

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

    /**
     * Write all lists to their respective files
     */
    public void write() {
        writeTitles(titlesList);
        writeNotes(notesList);
        writeMonths(monthList);
        writeDays(dayList);
        writeYears(yearList);
        writePriorities(priorityList);
    }

    /**
     * Add a note to all lists
     * @param title - note title
     * @param note - note content
     * @param month - month number (1-12)
     * @param day - day number
     * @param year - year number
     * @param priority - true if high priority
     */
    public void addNote(String title, String note, int month, int day, int year, boolean priority) {
        titlesList.add(title);
        notesList.add(note);
        monthList.add(String.valueOf(Math.min(month, 12)));
        dayList.add(String.valueOf(day));
        yearList.add(String.valueOf(year));
        priorityList.add(priority);
    }

    /**
     * Edit a note at a specific index
     * @param index - index of the note
     * @param title - new title
     * @param note - new content
     * @param month - new month
     * @param day - new day
     * @param year - new year
     * @param priority - new priority
     * @return true if successful
     */
    public boolean editNote(int index, String title, String note, int month, int day, int year, boolean priority) {
        if (notesList.size() > index) {
            titlesList.set(index, title);
            notesList.set(index, note);
            monthList.set(index, months[Math.min(month, 12) - 1]);
            dayList.set(index, String.valueOf(day));
            yearList.set(index, String.valueOf(year));
            priorityList.set(index, priority);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a note at a specific index
     * @param index - index of note to remove
     * @return true if successful
     */
    public boolean removeNote(int index) {
        if (index != -1) {
            titlesList.remove(index);
            notesList.remove(index);
            monthList.remove(index);
            dayList.remove(index);
            yearList.remove(index);
            priorityList.remove(index);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clear all notes
     */
    public void clear() {
        titlesList.clear();
        notesList.clear();
        monthList.clear();
        dayList.clear();
        yearList.clear();
        priorityList.clear();
    }

    /**
     * Print all notes to the console in a formatted way
     */
    public void printNotes() {
        System.out.println("Current Notes: ");
        if (!notesList.isEmpty()) {
            for (int i = 0; i < notesList.size(); i++) {
                System.out.println(titlesList.get(i) + ": " + notesList.get(i) + " - " +
                        monthList.get(i) + " " + dayList.get(i) + ", " +
                        yearList.get(i) + " - Priority: " +
                        (priorityList.get(i) ? "High" : "Low"));
            }
        }
        System.out.println();
    }

    /**
     * Read all lists from files
     */
    public void read() {
        titlesList = readTitles();
        notesList = readNotes();
        monthList = readMonths();
        dayList = readDays();
        yearList = readYears();
        priorityList = readPriorities();
    }

}
