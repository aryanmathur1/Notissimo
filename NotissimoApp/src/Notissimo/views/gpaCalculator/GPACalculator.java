package Notissimo.views.gpaCalculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GPACalculator {

    private JPanel gpaCalculatorPanel;
    private JButton addCourseButton, calculateButton;
    private JLabel resultLabel;
    private ArrayList<JTextField> gradeFields;
    private ArrayList<JTextField> creditFields;
    private ArrayList<JComboBox<String>> courseTypeComboBoxes;

    private final Color purple = new Color(86, 0, 255);
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 14);

    private JPanel coursePanel;

    public GPACalculator() {
        gradeFields = new ArrayList<>();
        creditFields = new ArrayList<>();
        courseTypeComboBoxes = new ArrayList<>();

        gpaCalculatorPanel = new JPanel(new BorderLayout());
        gpaCalculatorPanel.setBackground(Color.WHITE);

        setupCoursePanel();
        addCourseFields();

        resultLabel = new JLabel("Your GPA will be displayed here");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(purple);
        resultLabel.setBorder(new EmptyBorder(20, 20, 10, 20));

        addCourseButton = createPurpleButton("Add Course");
        addCourseButton.addActionListener(e -> addCourseFields());

        calculateButton = createPurpleButton("Calculate GPA");
        calculateButton.addActionListener(e -> calculateGPA());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(calculateButton);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(resultLabel);

        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        gpaCalculatorPanel.add(titlePanel, BorderLayout.NORTH);
        gpaCalculatorPanel.add(scrollPane, BorderLayout.CENTER);
        gpaCalculatorPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupCoursePanel() {
        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBackground(Color.WHITE);
        coursePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Add header row
        JPanel headerRow = new JPanel(new GridLayout(1, 3, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel gradeHeader = new JLabel("Grade %");
        JLabel creditHeader = new JLabel("Credits");
        JLabel typeHeader = new JLabel("Type");

        gradeHeader.setFont(defaultFont);
        creditHeader.setFont(defaultFont);
        typeHeader.setFont(defaultFont);

        headerRow.add(gradeHeader);
        headerRow.add(creditHeader);
        headerRow.add(typeHeader);

        coursePanel.add(headerRow);
    }

    private void addCourseFields() {
        JPanel row = new JPanel(new GridLayout(1, 3, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // Fixed row height

        JTextField gradeField = createRoundedTextField(6);
        JTextField creditField = createRoundedTextField(4);

        String[] types = {"Academic", "Honors", "AP"};
        JComboBox<String> typeDropdown = new JComboBox<>(types);
        typeDropdown.setFont(defaultFont);
        typeDropdown.setBackground(Color.WHITE);
        typeDropdown.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        typeDropdown.setPreferredSize(new Dimension(100, 30));

        row.add(gradeField);
        row.add(creditField);
        row.add(typeDropdown);

        gradeFields.add(gradeField);
        creditFields.add(creditField);
        courseTypeComboBoxes.add(typeDropdown);

        coursePanel.add(row);
        coursePanel.revalidate();
        coursePanel.repaint();
    }

    private void calculateGPA() {
        try {
            double totalWeightedPoints = 0;
            int totalCredits = 0;

            for (int i = 0; i < gradeFields.size(); i++) {
                double grade = Double.parseDouble(gradeFields.get(i).getText());
                int credits = Integer.parseInt(creditFields.get(i).getText());
                String type = (String) courseTypeComboBoxes.get(i).getSelectedItem();

                double gpa = convertPercentageToGPA(grade);
                gpa += getCourseBump(type);

                totalWeightedPoints += gpa * credits;
                totalCredits += credits;
            }

            double finalGPA = totalWeightedPoints / totalCredits;
            resultLabel.setText("Your GPA is: " + String.format("%.2f", finalGPA));

            // save in txt file for home screen
            String gpaLast = String.format("%.2f", finalGPA);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers in all fields.");
        }
    }

    private double convertPercentageToGPA(double percentage) {
        long rounded = Math.round(percentage);
        if (rounded >= 98) return 4.3;
        else if (rounded >= 93) return 4.0;
        else if (rounded >= 90) return 3.7;
        else if (rounded >= 87) return 3.3;
        else if (rounded >= 83) return 3.0;
        else if (rounded >= 80) return 2.7;
        else if (rounded >= 77) return 2.3;
        else if (rounded >= 73) return 2.0;
        else if (rounded >= 70) return 1.7;
        else if (rounded >= 67) return 1.3;
        else if (rounded >= 63) return 1.0;
        else if (rounded >= 60) return 0.7;
        else return 0.0;
    }

    private double getCourseBump(String type) {
        return switch (type) {
            case "Honors" -> 0.5;
            case "AP" -> 1.0;
            default -> 0.0;
        };
    }

    private JButton createPurpleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(purple);
        button.setForeground(Color.WHITE);
        button.setFont(defaultFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(purple));
        button.setPreferredSize(new Dimension(140, 40));
        button.setUI(new RoundedButtonUI());
        return button;
    }

    private JTextField createRoundedTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(defaultFont);
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        field.setPreferredSize(new Dimension(60, 30));
        return field;
    }

    public JPanel getPanel() {
        return gpaCalculatorPanel;
    }
}
