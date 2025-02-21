package Notissimo.noteSaving;

import Notissimo.UtilityValues;

import java.util.Scanner;

public class NotesBuilderTest {

    static NotesBuilder notesBuilder;

    public static void main(String[] args) {


        String command, note;
        int month, day, year;
        notesBuilder = new NotesBuilder();

        // Add an item
        Scanner in = new Scanner(System.in);

        do {

            // Read Data
            //printList(notesList);
            notesBuilder.printNotes();

            System.out.print("Please enter an item: ");

            System.out.print("'" + UtilityValues.ADD_COMMAND +  "' or '" + UtilityValues.REMOVE_COMMAND + "' ('" + UtilityValues.END_COMMAND + "' to end program): ");
            command = in.nextLine();
            if (command.equals(UtilityValues.ADD_COMMAND)) {

                System.out.print("Enter note: ");
                note = in.nextLine();

                do {
                    System.out.print("Enter month # 1-12: ");
                    String monthStr = in.nextLine();
                    month = Integer.parseInt(monthStr);
                } while (!(month >= 1 && month <= 12));

                System.out.print("Enter day: ");
                day = in.nextInt();
                System.out.print("Enter year: ");
                year = in.nextInt();

                notesBuilder.addNote(note, month, day, year);

            } else if (command.equals(UtilityValues.REMOVE_COMMAND)) {

                System.out.print("Enter Index: ");
                int index = in.nextInt();
                notesBuilder.removeNote(index);

            } else if (command.equals(UtilityValues.END_COMMAND)) {

                System.out.println("Ending Program");
                break;

            } else if (command.equals(UtilityValues.RESET_COMMAND)) {
                notesBuilder.clear();
            } else if (command.equals(UtilityValues.EDIT_COMMAND)) {
                System.out.print("Enter note: ");
                note = in.nextLine();

                do {
                    System.out.print("Enter month # 1-12: ");
                    String monthStr = in.nextLine();
                    month = Integer.parseInt(monthStr);
                } while (!(month >= 1 && month <= 12));

                System.out.print("Enter day: ");
                day = in.nextInt();
                System.out.print("Enter year: ");
                year = in.nextInt();

                System.out.println("enter index");
                int index = in.nextInt();

                notesBuilder.editNote(index, note, month, day, year);
            } else {
                System.out.println("Invalid Command");
            }

            // Save data
            notesBuilder.write();
        } while (!command.equals(UtilityValues.END_COMMAND));

        in.close();
    }

}
