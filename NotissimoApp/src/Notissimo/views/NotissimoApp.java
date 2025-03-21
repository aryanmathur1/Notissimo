package Notissimo.views;

import Notissimo.views.CustomElements.FancyButton;
import Notissimo.views.gpaCalculator.GPACalculator;
import Notissimo.views.taskManager.TaskManagerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotissimoApp extends JPanel {

    static JFrame frame;
    static JPanel panel;

    static FancyButton openTaskManagement, openGPACalculator;

    public void draw() {
        panel = new JPanel();

        frame = new JFrame("Notissimo");

        // Adding panel to frame

        frame.setContentPane(new NotissimoApp());
        frame.add(panel);

        addButtons();

        // Setting the size of frame
        frame.setSize(500, 100);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void addButtons() {

        openTaskManagement = new FancyButton("Task Management");
        openTaskManagement.setRadius(0);
        openTaskManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskManagerView();  // add new course fields when button is clicked
            }
        });

        openGPACalculator = new FancyButton("GPA Calculator");
        openGPACalculator.setRadius(0);
        openGPACalculator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GPACalculator();  // add new course fields when button is clicked
            }
        });

        panel.add(openTaskManagement);
        panel.add(openGPACalculator);

    }

    public static void main(String[] args) {

        NotissimoApp notissimoApp = new NotissimoApp();
        notissimoApp.draw();

    }

}
