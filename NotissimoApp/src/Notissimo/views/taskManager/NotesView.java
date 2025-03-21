package Notissimo.views.taskManager;

import Notissimo.noteSaving.NotesBuilder;
import Notissimo.views.CustomElements.FancyButton;

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
    static FancyButton addButton, removeButton;
    static JButton fancyButton;

    public void draw() {
        panel = new JPanel();

        frame = new JFrame("Notissimo");

        // setbackground of panel
        panel.setBackground(Color.white);

        // Adding panel to frame

        frame.setContentPane(new NotesView());
        frame.add(panel);

        addButtons();

        // Setting the size of frame
        frame.setSize(300, 500);

        frame.setVisible(true);

    }

    public void addButtons() {
        addButton = new FancyButton("add");
        addButton.setColorOver(new Color(127, 188, 147));
        addButton.setColorClick(new Color(153, 191, 163));
        addButton.setBorderColor(new Color(43, 122, 68));
        addButton.addActionListener(this);

        removeButton = new FancyButton("remove");
        removeButton.setColorOver(new Color(237, 133, 159));
        removeButton.setColorClick(new Color(225, 172, 186));
        removeButton.setBorderColor(new Color(221, 0, 39));
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

        // NOTES WRITTED LINE BY LINE HERE
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
