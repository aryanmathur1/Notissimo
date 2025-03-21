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

/**
 * ALERT AT START OF THE PROGRAM
 * So basically i copied my task manager code but removed the input fields and changed dimensions
 */
public class TaskAlert {

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
    public TaskAlert() {
        // setting frame default info
        frame = new JFrame("TO DO LIST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // initializing notes builder, the object I created for task saving
        notesBuilder = new NotesBuilder();

        // title on top of screen
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(new Color(255, 255, 255));
        JLabel label = new JLabel("Tasks TO DO");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel caption = new JLabel("Close this alert to continue");
        caption.setFont(new Font("Arial", Font.PLAIN, 12));

        titlePanel.add(label);
        //titlePanel.add(caption);

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

        JPanel continuePanel = new JPanel();
        // adding a continue button
        FancyButton continueButton = new FancyButton("Continue");
        continueButton.addActionListener(_ -> {
            frame.dispose();
            new NotissimoApp().draw();
        });
        continuePanel.add(continueButton);

        // adding one by one in order
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(continuePanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // this method display the JFrame to center position of a screen

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
        new TaskAlert();
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
