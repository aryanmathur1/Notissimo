package Notissimo.views.volunteerLogger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class VolunteerHour {

    private ArrayList<String> names;
    private ArrayList<Double> hours;

    public VolunteerHour() {
        names = readNames();
        hours = readHours();
    }

    public void addEntry(String name, double hours) {
        this.names.add(name);
        this.hours.add(hours);
    }

    public void insertEntry(int index, String name, double hour) {
        this.names.set(index, name);
        this.hours.add(index, hour);
    }

    public void deleteEntry(int index) {
        this.names.remove(index);
        this.hours.remove(index);
    }

    public void write() {
        writeNames();
        writeHours();
        writeTotalHours();
    }

    public String getName(int i) {
        return names.get(i);
    }

    public double getHour(int i) {
        return hours.get(i);
    }

    public double getTotalHours() {
        double total = 0;

        for (double hour : hours) {
            total += hour;
        }

        return total;
    }

    public void clear() {
        names.clear();
        hours.clear();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Double> getHours() {
        return hours;
    }

    private ArrayList<String> readNames() {
        return readFromFile("activitynames.txt");
    }

    private ArrayList<Double> readHours() {
        return convertStringToDoubleList(readFromFile("individualhours.txt"));
    }

    private void writeNames() {
        writeToFile("activitynames.txt", names);
    }

    private void writeHours() {
        writeToFile("individualhours.txt", convertDoubleToStringList(hours));
    }

    public void writeTotalHours() {

        ArrayList<String> totalHours = new ArrayList<>();
        totalHours.add(getTotalHours() + "");

        writeToFile("totalhours.txt", totalHours);
    }

    public static ArrayList<Double> convertStringToDoubleList(ArrayList<String> stringList) {
        ArrayList<Double> doubleList = new ArrayList<>();

        // Iterate through the String ArrayList and convert each String to a Double
        for (String str : stringList) {
            try {
                double num = Double.parseDouble(str);
                doubleList.add(num);
            } catch (NumberFormatException e) {
                // Handle the case where the String cannot be converted to a double
                System.err.println("Error: Could not convert \"" + str + "\" to a double. Skipping.");
                // You might choose to log the error, skip the problematic element, or throw an exception.
            }
        }

        return doubleList;
    }

    public static ArrayList<String> convertDoubleToStringList(ArrayList<Double> doubleList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Double num : doubleList) {
            stringList.add(String.valueOf(num));
        }
        return stringList;
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

}
