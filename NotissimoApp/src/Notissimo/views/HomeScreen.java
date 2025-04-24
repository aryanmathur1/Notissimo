package Notissimo.views;

import Notissimo.views.gpaCalculator.GPACalculator;
import Notissimo.views.gpaCalculator.RoundedButtonUI;
import Notissimo.views.taskManager.TaskAlert; // Keep TaskAlert
import Notissimo.views.taskManager.TaskManagerView;
import Notissimo.views.volunteerLogger.VolunteerHourTracker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Your existing HomeScreen class

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

    private Timer gpaUpdateTimer;
    private Timer volunteerHoursUpdateTimer;
    private Timer taskUpdateTimer;

    JPanel upcomingTasksPanel;

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
        volunteerHoursPanel = new VolunteerHourTracker().getPanel();

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Task View", taskViewPanel);
        tabbedPane.addTab("GPA Calculator", gpaCalculatorPanel);
        tabbedPane.addTab("Volunteer Hours", volunteerHoursPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);

        // Start the background update timers
        startGPAUpdateTimer();
        startVolunteerHoursUpdateTimer();
    }

    private void loadFonts() {

        MAIN_FONT = new Font("Arial", Font.PLAIN, 16);
        BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);
        TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    }

    private JButton createPurpleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
        button.setPreferredSize(new Dimension(140, 40));
        button.setUI(new RoundedButtonUI());
        return button;
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

        // ==== Create and position the Refresh button above the Upcoming Tasks section ====
        JPanel refreshPanel = new JPanel();
        refreshPanel.setBackground(Color.WHITE);
        refreshPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align the button to the right
        JButton refreshButton = createPurpleButton("Refresh");
        refreshButton.addActionListener(e -> {
            // Refresh the home panel by re-creating it
            JPanel refreshedHomePanel = createHomePanel();
            tabbedPane.setComponentAt(0, refreshedHomePanel); // Set the refreshed home panel
        });

        refreshPanel.add(refreshButton);

        // ==== Embed the task list panel here ====
        TaskAlert taskAlert = new TaskAlert(); // initializes task list from saved notes
        upcomingTasksPanel = taskAlert.getTaskListPanel();

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
        homePanel.add(refreshPanel); // Add the Refresh button above the task list
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

        gpaValue = new JLabel("--", SwingConstants.CENTER);  // Placeholder for GPA
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

        volunteerHoursValue = new JLabel("--", SwingConstants.CENTER);  // Placeholder for volunteer hours
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

    private void startGPAUpdateTimer() {
        int delay = 1000; // milliseconds (update every 1 second)
        gpaUpdateTimer = new Timer(delay, e -> updateGPAValue());
        gpaUpdateTimer.start();
    }

    private void startVolunteerHoursUpdateTimer() {
        int delay = 1000; // milliseconds (update every 1 second)
        volunteerHoursUpdateTimer = new Timer(delay, e -> updateVolunteerHoursValue());
        volunteerHoursUpdateTimer.start();
    }

    private void updateGPAValue() {
        try (BufferedReader reader = new BufferedReader(new FileReader("lastcalculatedgpa.txt"))) {
            String latestGPA = reader.readLine();
            if (latestGPA != null && !latestGPA.equals(gpaValue.getText())) {
                SwingUtilities.invokeLater(() -> gpaValue.setText(latestGPA));
            }
        } catch (IOException e) {
            System.err.println("Failed to update GPA from file.");
        }
    }

    private void updateVolunteerHoursValue() {
        try (BufferedReader reader = new BufferedReader(new FileReader("totalhours.txt"))) {
            String latestVolunteerHours = reader.readLine();
            if (latestVolunteerHours != null && !latestVolunteerHours.equals(volunteerHoursValue.getText())) {
                SwingUtilities.invokeLater(() -> volunteerHoursValue.setText(latestVolunteerHours));
            }
        } catch (IOException e) {
            System.err.println("Failed to update Volunteer Hours from file.");
        }
    }
}
