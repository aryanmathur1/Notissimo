package Notissimo.views.taskManager;

import Notissimo.noteSaving.NotesBuilder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * CREATE NOTES VIEW
 * Text fields for note, month, day, and year
 * when create button clicked, window closed and new note is created
 */
public class NewTaskView extends JFrame implements ActionListener {

    private static NewTaskView newTaskView;
    public NotesBuilder notesBuilder;

    // JFrame
    static JFrame frame;
    static JPanel panel;

    // JButton
    static JButton createButton, cancelButton;

    // Label to display text
    static JLabel label;

    static JTextField noteField, monthField, dayField, yearField;

    static JPanel buttonCard, textCard1, textCard2, textCard3, textCard4;

    // Main driver method
    public static void main(String[] args) {
        NewTaskView.newTaskView = new NewTaskView();
        newTaskView.run();
    }

    public void run() {
        init();

        draw();
    }

    public void init() {
        notesBuilder = new NotesBuilder();
    }

    public void draw() {
        // Creating a new frame to store
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        frame = new JFrame("Notissimo");

        // set background of panel
        panel.setBackground(Color.white);

        JLabel title = new JLabel("Create Note");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        drawTextFields();
        drawButtons();

        frame.add(panel);

        // Setting the size of frame
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Choose desired close operation
        frame.setVisible(true);
    }

    public void drawTextFields() {

        textCard1 = new JPanel();
        noteField = new JTextField(10);
        textCard1.add(new JLabel("Note:"));
        textCard1.add(noteField);
        panel.add(textCard1);
        textCard1.setBackground(Color.white);

        monthField = new JTextField(10);
        textCard2 = new JPanel();
        monthField = new JTextField(10);
        textCard2.add(new JLabel("Month:"));
        textCard2.add(monthField);
        panel.add(textCard2);
        textCard2.setBackground(Color.white);

        dayField = new JTextField(10);
        textCard3 = new JPanel();
        dayField = new JTextField(10);
        textCard3.add(new JLabel("Day:"));
        textCard3.add(dayField);
        panel.add(textCard3);
        textCard3.setBackground(Color.white);

        yearField = new JTextField(10);
        textCard4 = new JPanel();
        yearField = new JTextField(10);
        textCard4.add(new JLabel("Year:"));
        textCard4.add(yearField);
        panel.add(textCard4);
        textCard4.setBackground(Color.white);

    }

    public void drawButtons() {
        // Adding panel to frame
        buttonCard = new JPanel();

        createButton = new JButton("create");
        createButton.addActionListener(this);

        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(this);

        buttonCard.add(createButton, BorderLayout.SOUTH);
        buttonCard.add(cancelButton, BorderLayout.SOUTH);
        buttonCard.setBackground(Color.white);

        panel.add(buttonCard);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == createButton) {

            if (!noteField.getText().isEmpty() && !monthField.getText().isEmpty() && !dayField.getText().isEmpty() && !yearField.getText().isEmpty()) {
                // if everything is not empty, then add note and close window
                notesBuilder.addNote(noteField.getText(), Integer.parseInt(monthField.getText()), Integer.parseInt(dayField.getText()), Integer.parseInt(yearField.getText()), false);
                notesBuilder.write();
                notesBuilder.printNotes();
                frame.dispose();
            }

        }
        if (e.getSource() == cancelButton) {
            frame.dispose();
        }

    }
}
