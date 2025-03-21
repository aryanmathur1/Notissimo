package Notissimo.views.gpaCalculator;

import Notissimo.views.CustomElements.FancyButton;
import Notissimo.views.CustomElements.HintTextField;
import Notissimo.views.NotissimoApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GPACalculator extends JFrame {

    // Declare components
    private JPanel coursePanel;
    private FancyButton addCourseButton, calculateButton;
    private JLabel resultLabel;
    private ArrayList<JTextField> gradeFields;
    private ArrayList<JTextField> creditFields;

    public GPACalculator() {

        JPanel mainPanel = new JPanel();

        // set up the frame
        setTitle("GPA Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // initialize the lists to store input fields
        gradeFields = new ArrayList<>();
        creditFields = new ArrayList<>();

        // create the panel for courses - this will have all the text fields
        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));

        // add first course fields initially
        addCourseFields();

        // label
        resultLabel = new JLabel("Your GPA will be displayed here");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setBorder(new EmptyBorder(10, 20, 10, 20)); // padding
        resultLabel.setForeground(new Color(86, 0, 255, 255));

        FancyButton homeButton = new FancyButton("Home");
        homeButton.addActionListener(_ -> {
            dispose();
            new NotissimoApp().draw();
        });

        // Create add course button
        addCourseButton = new FancyButton("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourseFields();  // add new course fields when button is clicked
            }
        });

        // create the calculate button
        calculateButton = new FancyButton("Calculate GPA");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGPA(); // calculate when clicked
            }
        });

        // CREATE LAYOUT

        // this is the user input buttons on the bottom to add course or calculate
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCourseButton);
        buttonPanel.add(calculateButton);

        // create container panel

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(coursePanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(resultLabel);
        titlePanel.add(homeButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // add the panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    private void addCourseFields() {
        // create panels for new course input
        JPanel courseInputPanel = new JPanel();
        courseInputPanel.setLayout(new FlowLayout());

        // create grade and credit input fields
        JTextField gradeField = new JTextField(10);
        gradeField.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField creditField = new JTextField(5);
        creditField.setFont(new Font("Arial", Font.PLAIN, 12));

        // add them to the course input panel
        JLabel gradeLabel = new JLabel("Grade %");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        courseInputPanel.add(gradeLabel);
        courseInputPanel.add(gradeField);
        JLabel creditsLabel  = new JLabel("Credits");
        creditsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        courseInputPanel.add(creditsLabel);
        courseInputPanel.add(creditField);

        // add the input fields to the list
        gradeFields.add(gradeField);
        creditFields.add(creditField);

        // add the course panel to the main panel
        coursePanel.add(courseInputPanel);
        revalidate();  // ee-layout the frame to show new fields
        repaint();
    }

    private void calculateGPA() {
        try { // try to make sure everything is valid so the whole thing does not crash
            double totalWeightedPoints = 0;
            int totalCredits = 0;

            // loop through each course and calculate the weighted GPA
            for (int i = 0; i < gradeFields.size(); i++) {
                String gradeText = gradeFields.get(i).getText();
                String creditText = creditFields.get(i).getText();

                double grade = Double.parseDouble(gradeText);
                int credits = Integer.parseInt(creditText);

                // convert percentage to GPA (4.0 scale) using the GPA method
                double gpaGrade = convertPercentageToGPA(grade);

                // add multiple based on credits
                totalWeightedPoints += gpaGrade * credits;
                totalCredits += credits;
            }

            // calculate GPA by taking average
            double gpa = totalWeightedPoints / totalCredits;

            // display the result
            resultLabel.setText("Your GPA is: " + String.format("%.2f", gpa));

        } catch (NumberFormatException ex) {
            // otherwise error message for user input error
            JOptionPane.showMessageDialog(this, "Please enter valid grade and credit values for all courses.");
        }
    }

    /**
     * METHOD TO TURN PERCENTAGE INTO GPA UNWEIGHTED BASED ON LOUDOUN COUNTY
     * @param doublePercentage - grade percentage as a double
     * @return
     */
    private double convertPercentageToGPA(double doublePercentage) {

        // rounding percentage
        long percentage = Math.round(doublePercentage);

        if (percentage >= 98) {
            return 4.3;
        } else if (percentage >= 93) {
            return 4.0;
        } else if (percentage >= 90) {
            return 3.7;
        } else if (percentage >= 87) {
            return 3.3;
        } else if (percentage >= 83) {
            return 3.0;
        } else if (percentage >= 80) {
            return 2.7;
        } else if (percentage >= 77) {
            return 2.3;
        } else if (percentage >= 73) {
            return 2.0;
        }  else if (percentage >= 70) {
            return 1.7;
        } else if (percentage >= 67) {
            return 1.3;
        } else if (percentage >= 63) {
            return 1.0;
        } else if (percentage >= 60) {
            return 0.7;
        } else {
            return 0.0;
        }
    }

    /**
     * MAIN METHOD - just running a new window/instance of the GPA calculator
     */
    public static void main(String[] args) {
        new GPACalculator();
    }
}
