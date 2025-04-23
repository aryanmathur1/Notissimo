package Notissimo.views;

import Notissimo.views.gpaCalculator.GPACalculator;
import Notissimo.views.taskManager.TaskAlert; // Keep TaskAlert
import Notissimo.views.taskManager.TaskManagerView;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HomeScreen {

    private static final Color ACCENT_COLOR = new Color(0x6a00ff);
    private static Font MAIN_FONT;
    private static Font TITLE_FONT;
    private static Font BUTTON_FONT;

    private JFrame frame;
    private JTabbedPane tabbedPane;

    private JPanel taskViewPanel;
    private JPanel gpaCalculatorPanel;
    private JPanel volunteerHoursPanel;
    private JPanel bottomSectionPanel;  // Bottom section for GPA and Volunteer Hours

    // Add variables for the last calculated GPA and total volunteer hours
    private JLabel gpaValue;
    private JLabel volunteerHoursValue;

    public HomeScreen() {
        loadFonts();

        // Set minimal UI look
        UIManager.put("TabbedPane.selected", Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("TabbedPane.borderHightlightColor", Color.WHITE);
        UIManager.put("TabbedPane.light", Color.WHITE);
        UIManager.put("TabbedPane.focus", ACCENT_COLOR);
        UIManager.put("TabbedPane.selectedForeground", ACCENT_COLOR);
        UIManager.put("TabbedPane.foreground", Color.GRAY);
        UIManager.put("TabbedPane.tabAreaBackground", Color.WHITE);

        frame = new JFrame("Notissimo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(BUTTON_FONT);

        JPanel homePanel = createHomePanel();
        taskViewPanel = new TaskManagerView();
        gpaCalculatorPanel = new GPACalculator().getPanel();
        volunteerHoursPanel = createPlaceholderPanel("Volunteer Hours (replace with your panel)");

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Task View", taskViewPanel);
        tabbedPane.addTab("GPA Calculator", gpaCalculatorPanel);
        tabbedPane.addTab("Volunteer Hours", volunteerHoursPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void loadFonts() {
        try {
            MAIN_FONT = new Font("Arial", Font.PLAIN, 16);
            BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
            TITLE_FONT = new Font("Arial", Font.BOLD, 28);
        } catch (Exception e) {
            System.err.println("Error loading Arial font.");
            MAIN_FONT = new Font("Arial", Font.PLAIN, 16);
            BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
            TITLE_FONT = new Font("Arial", Font.BOLD, 28);
        }
    }

    public JPanel createHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome to Notissimo", SwingConstants.CENTER);
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(ACCENT_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        JLabel subtitle = new JLabel("to help you best", SwingConstants.CENTER);
        subtitle.setFont(MAIN_FONT.deriveFont(14f));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ==== Embed the task list panel here ====
        TaskAlert taskAlert = new TaskAlert(); // initializes task list from saved notes
        JPanel upcomingTasksPanel = taskAlert.getTaskListPanel();

        // Add border for the "Upcoming Tasks" section
        upcomingTasksPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                "Upcoming Tasks",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                ACCENT_COLOR));

        upcomingTasksPanel.setPreferredSize(new Dimension(600, 200));
        upcomingTasksPanel.setMaximumSize(new Dimension(600, 200));
        upcomingTasksPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ========================================

        // Add the bottom section (GPA and Volunteer Hours)
        bottomSectionPanel = createBottomSectionPanel();

        homePanel.add(welcomeLabel);
        homePanel.add(subtitle);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(upcomingTasksPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        homePanel.add(bottomSectionPanel);  // Add the bottom section here

        return homePanel;
    }

    private JPanel createBottomSectionPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));  // Align horizontally
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(600, 150));  // Match width with Upcoming Tasks
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the bottom panel

        // Create GPA section (Last GPA and GPA Value)
        JPanel gpaPanel = new JPanel();
        gpaPanel.setLayout(new BoxLayout(gpaPanel, BoxLayout.Y_AXIS));  // Stack vertically
        gpaPanel.setBackground(Color.WHITE);
        gpaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the panel

        JLabel gpaLabelText = new JLabel("Last GPA Calculated:", SwingConstants.CENTER);
        gpaLabelText.setFont(MAIN_FONT);
        gpaLabelText.setForeground(Color.GRAY);

        gpaValue = new JLabel("4.0", SwingConstants.CENTER);  // Placeholder for GPA
        gpaValue.setFont(new Font("Arial", Font.BOLD, 40));  // Huge font size for number
        gpaValue.setForeground(ACCENT_COLOR);

        gpaPanel.add(gpaLabelText);
        gpaPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add spacing
        gpaPanel.add(gpaValue);

        // Create Volunteer Hours section (Last Volunteer Hours and Volunteer Hours Value)
        JPanel volunteerPanel = new JPanel();
        volunteerPanel.setLayout(new BoxLayout(volunteerPanel, BoxLayout.Y_AXIS));  // Stack vertically
        volunteerPanel.setBackground(Color.WHITE);
        volunteerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the panel

        JLabel volunteerHoursLabelText = new JLabel("Volunteer Hours:", SwingConstants.CENTER);
        volunteerHoursLabelText.setFont(MAIN_FONT);
        volunteerHoursLabelText.setForeground(Color.GRAY);

        volunteerHoursValue = new JLabel("50", SwingConstants.CENTER);  // Placeholder for volunteer hours
        volunteerHoursValue.setFont(new Font("Arial", Font.BOLD, 40));  // Huge font size for number
        volunteerHoursValue.setForeground(ACCENT_COLOR);

        volunteerPanel.add(volunteerHoursLabelText);
        volunteerPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add spacing
        volunteerPanel.add(volunteerHoursValue);

        // Add both panels (GPA and Volunteer Hours) to the bottom panel
        bottomPanel.add(gpaPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(50, 0)));  // Add space between the panels
        bottomPanel.add(volunteerPanel);

        return bottomPanel;
    }

    private JButton styledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(BUTTON_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createPlaceholderPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(MAIN_FONT);
        label.setForeground(Color.GRAY);
        panel.add(label);
        return panel;
    }
}