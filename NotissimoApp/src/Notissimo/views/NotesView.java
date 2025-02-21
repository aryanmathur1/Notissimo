package Notissimo.views;

import Notissimo.noteSaving.NotesBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MAIN JFRAME TO TEST NOTES LISTS FROM TEXT FILES
 * ALSO A TEST FOR WHEN YOU CLICK A BUTTON NEW TASK VIEW SHOWS
 */
public class NotesView extends JPanel implements ActionListener {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;

    private static NotesBuilder notesBuilder;

    // JFrame
    static JFrame frame;
    static JPanel panel;
    static JFrame addFrame;
    static JPanel addPanel;

    static NewTaskView newTaskView;

    // JButton
    static JButton addButton, removeButton;

    public void draw() {
        panel = new JPanel();

        frame = new JFrame("Notissimo");

        // setbackground of panel
        panel.setBackground(Color.white);

        addButtons();

        // Adding panel to frame

        frame.setContentPane(new NotesView());
        frame.add(panel);

        // Setting the size of frame
        frame.setSize(300, 500);

        frame.setVisible(true);

    }

    public void addButtons() {
        addButton = new JButton("Add");
        addButton.addActionListener(this);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);

        panel.add(addButton, BorderLayout.SOUTH);
        panel.add(removeButton, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        int yNote = 100;
        g2d.setColor(Color.black);

        // need to animate this; doesn't update when I create new note until I rerun program
        for (int i = 0; i < notesBuilder.length(); i++) {
            g2d.drawString(notesBuilder.getFormattedNote(i), 10, yNote);
            yNote += 30;
        }

        notesBuilder.read();

        repaint();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            System.out.println("add");
            newTaskView.run();
        }
        if (e.getSource() == removeButton) {
            System.out.println("remove");
        }

    }

    public static void main(String[] args) {

        notesBuilder = new NotesBuilder();

        newTaskView = new NewTaskView();
        newTaskView.init();

        NotesView notesView = new NotesView();
        notesView.draw();

    }

}
