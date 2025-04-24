package Notissimo.views.taskManager;

import Notissimo.noteSaving.NotesBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskAlert {

    NotesBuilder notesBuilder;

    // for formatting
    private final String[] monthsFormat = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private ArrayList<String> tasks;
    private ArrayList<String> days;
    private ArrayList<String> months;
    private ArrayList<String> years;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;

    // Default Constructor - Removed frame display
    public TaskAlert() {
        // initializing notes builder
        notesBuilder = new NotesBuilder();

        tasks = new ArrayList<>();
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();

        // setting up the view for the list of tasks
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 12));
        taskList.setCellRenderer(new TaskListCellRenderer());

        ArrayList<String> monthsConvert = new ArrayList<>(List.of(monthsFormat));

        // importing the current data from the txt files from the note builder object
        for (int i = 0; i < notesBuilder.length(); i++) {
            String task = notesBuilder.getNote(i);
            String month = String.valueOf(monthsConvert.indexOf(notesBuilder.getMonth(i)));
            String day = notesBuilder.getDay(i);
            String year = notesBuilder.getYear(i);

            // Convert the task's due date components to integers
            int taskMonth = Integer.parseInt(month); // No need to subtract 1, since month is already 0-indexed
            int taskDay = Integer.parseInt(day);
            int taskYear = Integer.parseInt(year);

            // Get current date components
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);
            int currentMonth = currentDate.get(Calendar.MONTH); // 0-indexed
            int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

            // Compare year, month, and day individually
            if (taskYear >= currentYear || taskMonth >= currentMonth || taskDay >= currentDay) {

                tasks.add(task);
                months.add(month);
                days.add(day);
                years.add(year);

                // formatted to show on screen
                String taskEntry = notesBuilder.getTitle(i) + ": " + notesBuilder.getNote(i) + " (Due: " + monthsFormat[Integer.parseInt(notesBuilder.getMonth(i))-1] + " " + day + ", " + year + ") Priority: " + (notesBuilder.getPriority(i) ? "High" : "Low");
                taskListModel.addElement(taskEntry);
            }
        }

        //notesBuilder.printNotes(); // print to console for debugging
    }

    // Custom ListCellRenderer class
    private class TaskListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // set padding around each list item
            label.setBorder(new EmptyBorder(10, 15, 10, 15));  // top, left, bottom, right padding

            // set the background color (if selected)
            if (isSelected) {
                label.setBackground(new Color(235, 232, 244));
            } else {
                label.setBackground(Color.WHITE);  // normal background color
            }

            // set text color
            label.setForeground(new Color(34, 34, 34));

            // set the label to be opaque so the background shows
            label.setOpaque(true);

            // return the label
            return label;
        }
    }

    // Method to create and return the task list panel
    public JPanel getTaskListPanel() {
        // Panel setup
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBackground(Color.WHITE);

        // Reuse the scrollPane with JList
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add components
        taskPanel.add(scrollPane, BorderLayout.CENTER);

        return taskPanel;
    }

    // The continue action can be handled when needed (not part of this update).
    public void showContinueButton() {
        // Create continue button to continue with the program
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> {
            // Implement what happens when "Continue" is clicked
            // For example, closing the window or proceeding to the main app
        });
    }
}
