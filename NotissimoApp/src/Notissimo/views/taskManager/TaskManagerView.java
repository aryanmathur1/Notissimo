package Notissimo.views.taskManager;

import Notissimo.noteSaving.NotesBuilder;
import Notissimo.views.CustomElements.FancyButton;
import Notissimo.views.CustomElements.HintTextField;
import Notissimo.views.NotissimoApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskManagerView extends JPanel {

    private NotesBuilder notesBuilder;

    private final String[] monthsFormat = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private ArrayList<String> tasks;
    private ArrayList<String> days;
    private ArrayList<String> months;
    private ArrayList<String> years;
    private ArrayList<Boolean> priorities;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private JTextField taskField;
    private JSpinner dateTimeSpinner;

    private boolean filterHighPriority = false; // Flag to toggle filter mode

    // Default Constructor
    public TaskManagerView() {
        // Initializing NotesBuilder
        notesBuilder = new NotesBuilder();

        // Setting up the panel layout
        setLayout(new BorderLayout());

        // Panel for the title and home button
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(255, 255, 255));

        // Updated title styling
        JLabel label = new JLabel("Tasks");
        label.setFont(new Font("Arial", Font.BOLD, 24));  // Increased font size
        label.setForeground(new Color(86, 0, 255));  // Matching your UI color palette
        titlePanel.add(label);

        // Initializing lists for tasks and dates
        tasks = new ArrayList<>();
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        priorities = new ArrayList<>();

        // Setting up the task list display
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 12));
        taskList.setCellRenderer(new TaskListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Loading tasks from NotesBuilder
        for (int i = 0; i < notesBuilder.length(); i++) {
            String task = notesBuilder.getNote(i);
            String month = notesBuilder.getMonth(i);
            String day = notesBuilder.getDay(i);
            String year = notesBuilder.getYear(i);
            Boolean priority = notesBuilder.getPriority(i);

            tasks.add(task);
            months.add(month);
            days.add(day);
            years.add(year);
            priorities.add(priority);
        }

        // Sorting tasks based on date: year, month, day
        sortTasksByDate();

        // Adding tasks to the task list display after sorting
        for (int i = 0; i < tasks.size(); i++) {
            String taskEntry = tasks.get(i) + " (Due: " + monthsFormat[Integer.parseInt(months.get(i))-1] + " " + days.get(i) + ", " + years.get(i) + ") Priority: " + (priorities.get(i) ? "High" : "Low");
            taskListModel.addElement(taskEntry);
        }

        notesBuilder.printNotes(); // for debugging

        // Input panel for adding tasks
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(86, 0, 255, 255));

        taskField = new HintTextField("Enter a New Note");
        taskField.setColumns(20);
        taskField.setForeground(Color.WHITE);
        taskField.setFont(new Font("Arial", Font.PLAIN, 12));
        taskField.setBackground(new Color(86, 0, 255, 255));
        inputPanel.add(taskField);

        // Date spinner (datepicker)
        dateTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateTimeEditor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd");
        dateTimeSpinner.setEditor(dateTimeEditor);
        dateTimeSpinner.setFont(new Font("Arial", Font.PLAIN, 12));
        dateTimeSpinner.setBackground(new Color(86, 0, 255, 255));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) dateTimeSpinner.getEditor();
        editor.getTextField().setFont(new Font("Arial", Font.PLAIN, 12));
        editor.getTextField().setBackground(new Color(86, 0, 255, 255));
        editor.getTextField().setForeground(Color.WHITE);
        inputPanel.add(dateTimeSpinner);

        // Priority dropdown
        String[] priorityOptions = {"Low", "High"};
        JComboBox<String> priorityDropdown = new JComboBox<>(priorityOptions);
        priorityDropdown.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(priorityDropdown);

        // New task button
        FancyButton addButton = new FancyButton("New Task 📝");
        addButton.setColorOver(new Color(255, 255, 255));
        addButton.setColorClick(new Color(255, 255, 255));
        addButton.setBorderColor(new Color(255, 255, 255));
        addButton.setRadius(15);
        addButton.addActionListener(_ -> addTask(priorityDropdown));
        inputPanel.add(addButton);

        // Remove task button
        FancyButton removeButton = new FancyButton("Complete Task  ✅");
        removeButton.setColorOver(new Color(255, 255, 255));
        removeButton.setColorClick(new Color(255, 255, 255));
        removeButton.setBorderColor(new Color(255, 255, 255));
        removeButton.setRadius(15);
        removeButton.addActionListener(_ -> removeTask());
        inputPanel.add(removeButton);

        // Filter button for high-priority tasks in top-right corner
        FancyButton filterButton = new FancyButton("Filter");
        filterButton.setFont(new Font("Arial", Font.PLAIN, 12));
        filterButton.setForeground(new Color(255, 255, 255));
        filterButton.setColorOver(new Color(86, 0, 255));
        filterButton.setColorClick(new Color(86, 0, 255));
        filterButton.setBorderColor(new Color(86, 0, 255));
        filterButton.setRadius(20);
        filterButton.setPreferredSize(new Dimension(80, 30));
        filterButton.addActionListener(_ -> toggleFilter());
        filterButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(new Color(255, 255, 255));
        filterPanel.add(filterButton);

        // Adding components to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(filterPanel, BorderLayout.NORTH);
    }

    // Add a task to the list
    private void addTask(JComboBox<String> priorityDropdown) {
        String taskText = taskField.getText().trim();
        Date dueDate = (Date) dateTimeSpinner.getValue();

        if (!taskText.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueDate);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            boolean priority = priorityDropdown.getSelectedItem().equals("High"); // Get priority from dropdown

            tasks.add(taskText);
            months.add(String.valueOf(month));
            days.add(Integer.toString(day));
            years.add(Integer.toString(year));
            priorities.add(priority);

            // Sort the tasks again after adding a new one
            sortTasksByDate();

            // Refresh the task list display
            updateTaskList();

            notesBuilder.addNote(taskText, month, day, year, priority);
            notesBuilder.write();
            notesBuilder.printNotes();

            taskField.setText(""); // Clear the input field
        } else {
            JOptionPane.showMessageDialog(this, "Task cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Sort tasks by year, month, and day
    private void sortTasksByDate() {
        ArrayList<TaskEntry> taskEntries = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            int day = Integer.parseInt(days.get(i));
            int month = Integer.parseInt(months.get(i));
            int year = Integer.parseInt(years.get(i));
            boolean priority = priorities.get(i);
            taskEntries.add(new TaskEntry(tasks.get(i), day, month, year, priority));
        }

        // Sort tasks based on year -> month -> day
        Collections.sort(taskEntries, Comparator.comparingInt(TaskEntry::getYear)
                .thenComparingInt(TaskEntry::getMonth)
                .thenComparingInt(TaskEntry::getDay));

        // Update task lists with the sorted tasks
        tasks.clear();
        days.clear();
        months.clear();
        years.clear();
        priorities.clear();
        for (TaskEntry entry : taskEntries) {
            tasks.add(entry.getTask());
            days.add(String.valueOf(entry.getDay()));
            months.add(String.valueOf(entry.getMonth()));
            years.add(String.valueOf(entry.getYear()));
            priorities.add(entry.getPriority());
        }
    }

    // Update the task list display
    private void updateTaskList() {
        taskListModel.clear();

        // Check if filter mode is active
        for (int i = 0; i < tasks.size(); i++) {
            boolean isHighPriority = priorities.get(i);

            // Only show high-priority tasks if the filter is active
            if (filterHighPriority && !isHighPriority) {
                continue;
            }

            String taskEntry = tasks.get(i) + " (Due: " + monthsFormat[Integer.parseInt(months.get(i))-1] + " " + days.get(i) + ", " + years.get(i) + ") Priority: " + (isHighPriority ? "High" : "Low");
            taskListModel.addElement(taskEntry);
        }
    }

    // Toggle the filter for high-priority tasks
    private void toggleFilter() {
        filterHighPriority = !filterHighPriority;
        updateTaskList();
    }

    // Remove the selected task
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            days.remove(selectedIndex);
            months.remove(selectedIndex);
            years.remove(selectedIndex);
            priorities.remove(selectedIndex);
            taskListModel.remove(selectedIndex);

            notesBuilder.removeNote(selectedIndex);
            notesBuilder.write();
            notesBuilder.printNotes();
        } else {
            JOptionPane.showMessageDialog(this, "No task selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom list renderer for tasks
    private class TaskListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            label.setBorder(new EmptyBorder(10, 15, 10, 15));

            if (isSelected) {
                label.setBackground(new Color(235, 232, 244));
            } else {
                label.setBackground(Color.WHITE);
            }

            // Highlight high-priority tasks
            if (priorities.get(index) || filterHighPriority) {
                label.setBackground(new Color(255, 245, 183)); // Yellow for high priority
            }

            label.setForeground(new Color(34, 34, 34));
            label.setOpaque(true);
            return label;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int padding = 10;
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(getText()) + 2 * padding;
            int height = fm.getHeight() + 2 * padding - 4;

            int x = padding;
            int y = (getHeight() - height) / 2;

            g2d.setColor(new Color(195, 171, 255, 255));
            g2d.fillRoundRect(x, y, width, height, 20, 20);

            g2d.setColor(getForeground());
            g2d.drawString(getText(), x + padding, y + fm.getAscent() + padding - 2);
        }
    }

    // Helper class to represent task entries with dates
    private class TaskEntry {
        private String task;
        private int day;
        private int month;
        private int year;
        private boolean priority;

        public TaskEntry(String task, int day, int month, int year, boolean priority) {
            this.task = task;
            this.day = day;
            this.month = month;
            this.year = year;
            this.priority = priority;
        }

        public String getTask() {
            return task;
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

        public boolean getPriority() {
            return priority;
        }
    }
}
