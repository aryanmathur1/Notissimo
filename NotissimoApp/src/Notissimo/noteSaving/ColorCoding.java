package Notissimo.noteSaving;

/**
 * FOR ADHI
 */
public class ColorCoding {

    static NotesBuilder notesBuilder = new NotesBuilder();

    public static void main(String[] args) {

        // prints each line individually
        for (int i = 0; i < notesBuilder.length(); i++) {
            System.out.println("whatever color thingy " + notesBuilder.getFormattedNote(i));
            if (Integer.parseInt(notesBuilder.getYear(i)) >= 2025) {
                System.out.println("This note is in the future/present!");
            } else {
                System.out.println("This note is in the past!");
            }
        }


    }

}
