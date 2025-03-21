package Notissimo.views.taskManager;

import Notissimo.noteSaving.NotesBuilder;
import Notissimo.views.CustomElements.FancyButton;
import Notissimo.views.CustomElements.HintTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * THIS IS THE TASK MANAGER VIEW
 * This can be called from another file as well
 * Uses the NotesBuilder object
 * UI
 *
 * - Aryan Mathur 2025
 */
public class TaskManagerView {

    NotesBuilder notesBuilder;

    // for formatting
    private final String[] monthsFormat = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private JFrame frame;
    private ArrayList<String> tasks;
    private ArrayList<String> days;
    private ArrayList<String> months;
    private ArrayList<String> years;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private JTextField taskField;
    private JSpinner dateTimeSpinner;

    // Default Constructor - Basically when called, a new window opens
    public TaskManagerView() {
        // setting frame default info
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        // initializing notes builder, the object I created for task saving
        notesBuilder = new NotesBuilder();

        // title on top of screen
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(new Color(255, 255, 255));
        JLabel label = new JLabel("Tasks");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(label);

        // saving everything in an array lists just for east use
        tasks = new ArrayList<>();
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();

        // setting up the view for the list of tasks
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 12));
        taskList.setCellRenderer(new TaskListCellRenderer());
        // putting this into a scrolling pane so its scrollable and has each item
        JScrollPane scrollPane = new JScrollPane(taskList);

        // importing the current data from the txt files from the note builder object
        for (int i = 0; i < notesBuilder.length(); i++) {
            String task = notesBuilder.getNote(i);
            String month = notesBuilder.getMonth(i);
            String day = notesBuilder.getDay(i);
            String year = notesBuilder.getYear(i);

            tasks.add(task);
            months.add(month);
            days.add(day);
            years.add(year);

            // formatted to show on screen
            String taskEntry = task + " (Due: " + month + " " + day + ", " + year + ")";
            taskListModel.addElement(taskEntry);

        }

        notesBuilder.printNotes(); // print to console for debugging

        // INPUT PANE - entering note, data, adding and removing notes
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(86, 0, 255, 255));

        // text field for new note
        taskField = new HintTextField("Enter a New Note");
        taskField.setColumns(20);
        taskField.setForeground(Color.WHITE);
        taskField.setFont(new Font("Arial", Font.PLAIN, 12));
        taskField.setBackground(new Color(86, 0, 255, 0));
        inputPanel.add(taskField);

        // date chooser
        dateTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateTimeEditor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd");
        dateTimeSpinner.setEditor(dateTimeEditor);
        // Set the font and background color for the JSpinner (date picker)
        dateTimeSpinner.setFont(new Font("Arial", Font.PLAIN, 12)); // Set the font
        dateTimeSpinner.setBackground(new Color(255, 255, 255, 0));  // Set background color
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) dateTimeSpinner.getEditor();
        editor.getTextField().setFont(new Font("Arial", Font.PLAIN, 12));  // Set font for the text field inside the spinner
        editor.getTextField().setBackground(new Color(240, 240, 240, 0));  // Set background color for the text field
        editor.getTextField().setForeground(Color.WHITE);  // Set the font color of the text field to white
        inputPanel.add(dateTimeSpinner);

        // BUTTONS using my custom FancyButtons class!! - looks slightly nicer

        // add button
        FancyButton addButton = new FancyButton("New Task");
        addButton.setColorOver(new Color(82, 174, 116));
        addButton.setColorClick(new Color(155, 197, 166));
        addButton.setBorderColor(new Color(43, 122, 68));
        addButton.setRadius(25);
        addButton.addActionListener(_ -> addTask());
        inputPanel.add(addButton);

        // remove task button
        FancyButton removeButton = new FancyButton("Remove Task");
        removeButton.setColorOver(new Color(237, 133, 159));
        removeButton.setColorClick(new Color(225, 172, 186));
        removeButton.setBorderColor(new Color(221, 0, 39));
        removeButton.setRadius(25);
        removeButton.addActionListener(_ -> removeTask());
        inputPanel.add(removeButton);

        inputPanel.setFocusable(true);

        // adding one by one in order
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true); // required statement
    }

    /**
     * METHOD to add a task based on certain parameters entered in the text fields
     */
    private void addTask() {

        // getting the task title and the date
        String taskText = taskField.getText().trim();
        Date dueDate = (Date) dateTimeSpinner.getValue();

        // make sure it is not empty
        if (!taskText.isEmpty()) {
            //get the values from the due date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueDate);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            // add all the values to the array lists
            days.add(Integer.toString(day));
            months.add(Integer.toString(month));
            years.add(Integer.toString(year));
            tasks.add(taskText);

            // add to the note builder and write
            notesBuilder.addNote(taskText, month, day, year);
            notesBuilder.write();
            notesBuilder.printNotes(); // to console for debugging

            // add to the display list
            String taskEntry = taskText + " (Due: " + monthsFormat[month-1] + " " + day + ", " + year + ")";
            taskListModel.addElement(taskEntry);
            taskField.setText("");
        } else {
            // if empty, throw an error
            JOptionPane.showMessageDialog(frame, "Task cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        // print for debugging
        System.out.println(tasks);
        System.out.println(months);
        System.out.println(days);
        System.out.println(years);
    }

    /**
     * METHOD to remove a task based on what is selected
     */
    private void removeTask() {

        // first, get the index based on what is selected
        int selectedIndex = taskList.getSelectedIndex();
        System.out.println("Selected Index: " + selectedIndex); // print for debugging

        // make sure it is an actual index
        if (selectedIndex != -1) {
            // remove index from every array list
            tasks.remove(selectedIndex);
            days.remove(selectedIndex);
            months.remove(selectedIndex);
            years.remove(selectedIndex);
            taskListModel.remove(selectedIndex);

            // remove from note builder and write onto the text file
            notesBuilder.removeNote(selectedIndex);
            notesBuilder.write();
            notesBuilder.printNotes();
        } else {
            // otherwise, throw an error message
            JOptionPane.showMessageDialog(frame, "No task selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // print for debugging
        System.out.println(tasks);
        System.out.println(months);
        System.out.println(days);
        System.out.println(years);
    }

    /**
     * MAIN METHOD - just making a new instance of the graphics window
     */
    public static void main(String[] args) {
        new TaskManagerView();
    }

    // custom ListCellRenderer class to add padding and filled rounded rectangles around each task item
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

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // casting Graphics to Graphics2D for advanced control over rendering
            Graphics2D g2d = (Graphics2D) g;

            // enable antialiasing for smoother edges
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // fefine the padding around the text
            int padding = 10;
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(getText()) + 2 * padding;
            int height = fm.getHeight() + 2 * padding - 4;

            // calculate the position of the rectangle
            int x = padding;
            int y = (getHeight() - height) / 2;

            // set color for the filled rounded rectangle
            g2d.setColor(new Color(195, 171, 255, 255));  // background color
            g2d.fillRoundRect(x, y, width, height, 20, 20);  // draw the filled rounded rectangle

            // set color for the text
            g2d.setColor(getForeground());  // text color
            g2d.drawString(getText(), x + padding, y + fm.getAscent() + padding-2);  // draw the text inside the rectangle
        }

    }
}
