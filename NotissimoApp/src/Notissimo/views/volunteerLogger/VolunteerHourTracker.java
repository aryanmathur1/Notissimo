package Notissimo.views.volunteerLogger;

import Notissimo.views.CustomElements.FancyButton;
import Notissimo.views.gpaCalculator.RoundedButtonUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VolunteerHourTracker {

    // UI components
    private JPanel volunteerHourPanel;
    private JButton addActivityButton, saveButton;
    private JLabel totalHoursLabel;

    // Lists to track each row's fields (activity, date, hours) and delete buttons
    private ArrayList<JTextField> activityFields;
    private ArrayList<JTextField> dateFields;
    private ArrayList<JTextField> hourFields;
    private ArrayList<JButton> deleteButtons;

    // Styling constants
    private final Color purple = new Color(86, 0, 255);
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 14);
    private final Color deleteButtonColor = new Color(250, 85, 85);

    private JPanel activityPanel;
    private VolunteerHour volunteerHour;

    // Constructor to initialize and set up everything
    public VolunteerHourTracker() {
        // Initialize field lists
        activityFields = new ArrayList<>();
        dateFields = new ArrayList<>();
        hourFields = new ArrayList<>();
        deleteButtons = new ArrayList<>();

        // Set up main panel
        volunteerHourPanel = new JPanel(new BorderLayout());
        volunteerHourPanel.setBackground(Color.WHITE);

        // Set up the panel where activity rows will be added
        setupActivityPanel();

        // Label for total hours
        totalHoursLabel = new JLabel("Total Hours: 0");
        totalHoursLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalHoursLabel.setForeground(purple);
        totalHoursLabel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Load saved entries from storage
        loadExistingEntries();

        // Button to add a new row
        addActivityButton = createPurpleButton("Add Activity");
        addActivityButton.addActionListener(e -> addActivityFields());

        // Button to save all current entries
        saveButton = createPurpleButton("Save Hours");
        saveButton.addActionListener(e -> saveVolunteerHours());

        // Panel to hold the add/save buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addActivityButton);
        buttonPanel.add(saveButton);

        // Panel to display the total hours label at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(totalHoursLabel);

        // Scroll pane for activity rows
        JScrollPane scrollPane = new JScrollPane(activityPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Assemble the full layout
        volunteerHourPanel.add(titlePanel, BorderLayout.NORTH);
        volunteerHourPanel.add(scrollPane, BorderLayout.CENTER);
        volunteerHourPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Sets up the column headers for the activity input area
    private void setupActivityPanel() {
        activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Header row with labels
        JPanel headerRow = new JPanel(new GridLayout(1, 4, 10, 0));
        headerRow.setBackground(Color.WHITE);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel activityHeader = new JLabel("Activity Name");
        JLabel dateHeader = new JLabel("Date");
        JLabel hourHeader = new JLabel("Hours");
        JLabel deleteHeader = new JLabel("Delete");

        activityHeader.setFont(defaultFont);
        dateHeader.setFont(defaultFont);
        hourHeader.setFont(defaultFont);
        deleteHeader.setFont(defaultFont);

        headerRow.add(activityHeader);
        headerRow.add(dateHeader);
        headerRow.add(hourHeader);
        headerRow.add(deleteHeader);

        activityPanel.add(headerRow);
    }

    // Adds a new row for activity input (activity name, date, hours, delete)
    private void addActivityFields() {
        JPanel row = new JPanel(new GridLayout(1, 4, 10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // Input fields
        JTextField activityField = createRoundedTextField(15);
        JTextField dateField = createRoundedTextField(10);
        JTextField hourField = createRoundedTextField(5);

        // Validate that hours input is a number
        hourField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateInput(hourField); }
            public void removeUpdate(DocumentEvent e) { validateInput(hourField); }
            public void changedUpdate(DocumentEvent e) {}
        });

        // Delete button to remove the row
        FancyButton deleteButton = new FancyButton("Delete  🗑");
        deleteButton.setColor(deleteButtonColor);
        deleteButton.setColorClick(deleteButtonColor);
        deleteButton.setColorOver(deleteButtonColor);
        deleteButton.setBorderColor(deleteButtonColor);
        deleteButton.setRadius(20);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(defaultFont);
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(30, 30));
        deleteButton.addActionListener(e -> deleteActivityRow(row, activityField, dateField, hourField, deleteButton));

        // Add everything to the row and track them
        row.add(activityField);
        row.add(dateField);
        row.add(hourField);
        row.add(deleteButton);

        activityFields.add(activityField);
        dateFields.add(dateField);
        hourFields.add(hourField);
        deleteButtons.add(deleteButton);

        activityPanel.add(row);
        activityPanel.revalidate();
        activityPanel.repaint();
    }

    // Checks if the hour field is a valid number and highlights it red if not
    private void validateInput(JTextField field) {
        String text = field.getText();
        try {
            if (!text.isEmpty()) Double.parseDouble(text);
            field.setForeground(Color.BLACK);
        } catch (NumberFormatException e) {
            field.setForeground(Color.RED);
        }
    }

    // Removes a row and its associated fields
    private void deleteActivityRow(JPanel row, JTextField activityField, JTextField dateField, JTextField hourField, JButton deleteButton) {
        activityPanel.remove(row);
        activityFields.remove(activityField);
        dateFields.remove(dateField);
        hourFields.remove(hourField);
        deleteButtons.remove(deleteButton);

        activityPanel.revalidate();
        activityPanel.repaint();
    }

    // Collects all current input and saves them using the VolunteerHour helper
    private void saveVolunteerHours() {
        try {
            double totalHours = 0;
            VolunteerHour vh = new VolunteerHour();
            vh.clear(); // Start fresh

            for (int i = 0; i < hourFields.size(); i++) {
                String name = activityFields.get(i).getText().trim();
                String date = dateFields.get(i).getText().trim();
                String hourText = hourFields.get(i).getText().trim();

                if (name.isEmpty() || date.isEmpty() || hourText.isEmpty()) continue;

                double hours = Double.parseDouble(hourText);
                vh.addEntry(name, date, hours);
                totalHours += hours;
            }

            vh.write(); // Persist the data
            totalHoursLabel.setText("Total Hours: " + String.format("%.2f", totalHours));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric hours in all fields.");
        }
    }

    // Loads saved entries from storage and populates the input rows
    private void loadExistingEntries() {
        volunteerHour = new VolunteerHour();

        for (int i = 0; i < volunteerHour.getNames().size(); i++) {
            addActivityFields();
            activityFields.get(i).setText(volunteerHour.getName(i));
            dateFields.get(i).setText(volunteerHour.getDate(i));
            hourFields.get(i).setText(String.valueOf(volunteerHour.getHour(i)));
        }

        totalHoursLabel.setText("Total Hours: " + String.format("%.2f", volunteerHour.getTotalHours()));
    }

    // Creates a styled button in the purple theme
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

    // Creates a styled rounded text field
    private JTextField createRoundedTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(defaultFont);
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        field.setPreferredSize(new Dimension(60, 30));
        return field;
    }

    // Returns the main panel to be added to the app
    public JPanel getPanel() {
        return volunteerHourPanel;
    }
}
