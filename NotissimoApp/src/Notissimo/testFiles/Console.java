package Notissimo.testFiles;
//integral imports for reading/writing to files, ArrayList/List, Date/Time, and user input.
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a single entry of volunteer work
class VolunteerEntry {
    private String description; // Description of the volunteer work
    private LocalDateTime date; // Date of the volunteer work
    private double hours; // Number of hours volunteered

    // Constructor to initialize a VolunteerEntry object
    public VolunteerEntry(String description, LocalDateTime date, double hours) {
        this.description = description;
        this.date = date;
        this.hours = hours;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Getter for the date
    public LocalDateTime getDate() {
        return date;
    }

    // Getter for the hours
    public double getHours() {
        return hours;
    }

    // Overrides the toString method to provide a formatted string representation of the entry
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define date format
        return String.format("Date: %s, Description: %s, Hours: %.2f", date.format(formatter), description, hours);
    }
}

// Represents a task with an ID, name, description, due date, and repeating status
class Task {
    private static int idCounter = 1; // Static counter for generating unique task IDs
    private int id; // Unique task ID
    private String name; // Task name
    private String description; // Task description
    private LocalDateTime dueDate; // Task due date and time
    private boolean isRepeating; // Flag to indicate if the task repeats daily

    // Constructor to create a new Task object
    public Task(String name, String description, LocalDateTime dueDate, boolean isRepeating) {
        this.id = idCounter++; // Assign a unique ID and increment the counter
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isRepeating = isRepeating;
    }

    // Getter for the task ID
    public int getId() {
        return id;
    }

    // Getter for the task name
    public String getName() {
        return name;
    }

    // Setter for the task name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for the task description
    public String getDescription() {
        return description;
    }

    // Setter for the task description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for the due date
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    // Setter for the due date
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    // Getter for the repeating status
    public boolean isRepeating() {
        return isRepeating;
    }

    // Setter for the repeating status
    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    // Overrides the toString method to return a string representation of the task, suitable for file storage
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"); // Define date and time format
        return id + "," + name + "," + description + "," + dueDate.format(formatter) + "," + (isRepeating ? "Yes" : "No");
    }

    // Setter for the static ID counter (used when loading tasks from file)
    public static void setIdCounter(int idCounter) {
        Task.idCounter = idCounter;
    }

    // Getter for the next available task ID
    public static int getNextId() {
        return idCounter;
    }
}

// Represents a course with its name, grade, and course type
class Course {
    public String name; // Course name
    public double grade; // Course grade
    public String courseType; // Course type (AP, DE, Honor, Academic)

    // Constructor to create a new Course object
    public Course(String name, double grade, String courseType) {
        this.name = name;
        this.grade = grade;
        this.courseType = courseType;
    }

    // Calculates a weighted grade based on the course type
    public static double calculateWeightedGrade(double grade, String courseType) {
        switch (courseType.toUpperCase()) {
            case "AP":
            case "DE":
                return Math.min(grade + 1.0, 100); // AP/DE courses get a +1.0 bump, capped at 100
            case "HONOR":
                return Math.min(grade + 0.5, 100); // Honors courses get a +0.5 bump, capped at 100
            case "ACADEMIC":
                return grade; // Academic courses get no bump
            default:
                return grade; // Default case: no weighting applied
        }
    }
}

// Represents a simple note with a title and content
class Note {
    private String title; // Note title
    private String content; // Note content

    // Constructor to create a new Note object
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter for the title
    public String getTitle() {
        return title;
    }

    // Getter for the content
    public String getContent() {
        return content;
    }

    // Setter for the content
    public void setContent(String content) {
        this.content = content;
    }

    // Overrides the toString method to return a formatted string representation of the note
    @Override
    public String toString() {
        return title + "\n" + content;
    }
}

// It said something about login/sign-in so I assume there should be a useraccount class
class UserAccount {
    private static String username;
    private static String password;

    //Constructor
    public UserAccount(String aUsername, String aPassword) {
        username = aUsername;
        password = aPassword;
    }

    //Getters and Setters
    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }
}

// Main class for the console application
public class Console {
    private static List<Task> taskList = new ArrayList<>(); // List to store tasks
    private static List<Note> notesList = new ArrayList<>(); // List to store notes
    private static List<VolunteerEntry> volunteerEntries = new ArrayList<>(); // List to store volunteer entries
    private static Scanner scanner = new Scanner(System.in); // Scanner for user input
    private static boolean running = false; // Flag to control the main application loop

    private static final String TASK_FILE = "tasks.txt"; // File path for storing tasks
    private static final String NOTE_FILE = "notes.txt"; // File path for storing notes
    private static final String COURSE_FILE = "courses.txt"; // File path (not used)
    private static final String VOLUNTEER_FILE = "volunteer_entries.txt"; // File path for storing volunteer entries

    // Main method - entry point of the application
    public static void main(String[] args) {
        int LogSign = getValidIntegerInput("1. Make New Account or 2. Login");
        if (LogSign == 1) {
            System.out.println("Enter New Username: ");
            String username = scanner.nextLine(); // Get a username
            System.out.println("Enter New Password: ");
            String password = scanner.nextLine(); // Get a password
            UserAccount a1 = new UserAccount(username, password); //Modify later to make this add to an ArrayList of UserAccounts, you could also save specific files to each UserAccount
            System.out.println("Enter Username: ");
            String confirmUser = scanner.nextLine(); // confirm username
            System.out.println("Enter Password: ");
            String confirmPass = scanner.nextLine(); // confirm password
            if (confirmUser.equals(a1.getUsername()) || confirmPass.equals(a1.getPassword())) {
                System.out.println("Account confirmed.");
                running = true;
            } else {
                //loop around with a while loop to try and confirm username and password repeatedly until they get it right
            }
        } else {
            System.out.println("Enter Username: ");
            String username = scanner.nextLine(); // Give your username
            if (UserAccount.getUsername().equals(username)) { /*Find a way to store previously made UserAccounts and modify this to
            search across all previously stored UserAccounts to find one that matches the username */
                System.out.println("Enter Password: ");
                String password = scanner.nextLine(); // Try a password

                if (UserAccount.getPassword().equals(password)) {
                    System.out.println("Logging In...");
                    running = true;
                } else {
                    System.out.println("Incorrect Password, try again."); //This part should be modified later to let you loop back and try to enter another password.
                }

            } else { //this is for the situation where a UserAccount with the username does not exist
                System.out.println("Account does not exist.");
            }

        }

        loadTasks(); // Load tasks from file
        loadNotes(); // Load notes from file
        loadVolunteerEntries(); // Load volunteer entries from file

        // Create and start a thread for checking alerts (due tasks)
        Thread alertThread = new Thread(Console::checkAlerts);
        alertThread.setDaemon(true); // Set as a daemon thread so it doesn't prevent the program from exiting
        alertThread.start(); // Start the alert thread



        // Main application loop
        while (running) {
            System.out.println("\nWelcome to the Console Application:");
            System.out.println("1. Task Management System");
            System.out.println("2. GPA Calculation");
            System.out.println("3. Notes Management");
            System.out.println("4. Log Volunteer Hours");
            System.out.println("5. Exit");

            int choice = getValidIntegerInput("Choose an option (1-5): "); // Get user's choice
            switch (choice) {
                case 1 -> manageTasks(); // Manage tasks
                case 2 -> calculateGPA(); // Calculate GPA
                case 3 -> manageNotes(); // Manage notes
                case 4 -> manageVolunteerHours(); // Manage volunteer hours
                case 5 -> { // Exit the application
                    saveTasks(); // Save tasks to file
                    saveNotes(); // Save notes to file
                    saveVolunteerEntries(); // Save volunteer entries to file
                    System.out.println("Exiting Console Application...");
                    running = false; // Set running flag to false to exit the loop
                }
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid input
            }
        }
    }

    // Manages the task-related operations
    private static void manageTasks() {
        while (true) {
            System.out.println("\nTask Management System:");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Back to Main Menu");

            int choice = getValidIntegerInput("Choose an option (1-5): "); // Get user's choice
            switch (choice) {
                case 1 -> createTask(); // Create a new task
                case 2 -> displaySortedTasks(); // Display all tasks sorted by due date
                case 3 -> editTask(); // Edit an existing task
                case 4 -> deleteTask(); // Delete a task
                case 5 -> { return; } // Return to the main menu
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid input
            }
        }
    }

    // Creates a new task and adds it to the task list
    private static void createTask() {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine(); // Get the task name

        System.out.print("Enter task description: ");
        String description = scanner.nextLine(); // Get the task description

        LocalDateTime dueDate;
        while (true) {
            System.out.print("Enter due date and time (yyyy-MM-dd hh:mm a): ");
            String dueDateString = scanner.nextLine(); // Get the due date and time
            try {
                dueDate = LocalDateTime.parse(dueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")); // Parse the date string
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again."); // Handle invalid date format
            }
        }

        boolean isRepeating;
        while (true) {
            System.out.print("Should this task repeat daily? (yes/no): ");
            String repeatInput = scanner.nextLine().trim().toLowerCase(); // Get user input for repeating tasks
            if (repeatInput.equals("yes")) {
                isRepeating = true;
                break;
            } else if (repeatInput.equals("no")) {
                isRepeating = false;
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'."); // Handle invalid input for repeating tasks
            }
        }

        // Create new Task and add to the list
        Task task = new Task(name, description, dueDate, isRepeating); // Create a new task object
        taskList.add(task); // Add the task to the list
        System.out.println("Task created successfully!");
    }

    // Displays all tasks sorted by due date
    private static void displaySortedTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to display."); // Handle the case where there are no tasks
            return;
        }

        LocalDateTime now = LocalDateTime.now(); // Get the current date and time

        taskList.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate())); // Sort the task list by due date

        System.out.println("\nTasks (ordered by due date):");
        for (Task task : taskList) {
            if (task.getDueDate().isBefore(now) || task.getDueDate().isEqual(now)) {
                System.out.println("\u001B[31m" + task + "\u001B[0m"); // Print each task to the console, red for overdue
            } else if (Duration.between(task.getDueDate(), now).toDays() <= 2) {
                System.out.println("\u001B[33m" + task + "\u001B[0m"); // Print each task to the console, yellow for priority
            } else if (Duration.between(task.getDueDate(), now).toDays() > 2) {
                System.out.println("\u001B[32m" + task + "\u001B[0m"); // Print each task to the console, green for upcoming
            }
        }
    }

    // Edits an existing task
    private static void editTask() {
        int taskId = getValidIntegerInput("Enter the Task ID to edit: "); // Get the task ID to edit
        Task task = findTaskById(taskId); // Find the task by its ID
        if (task == null) {
            System.out.println("Task not found."); // Handle the case where the task is not found
            return;
        }

        System.out.println("Editing Task: " + task);
        System.out.print("Enter new name (leave blank to keep unchanged): ");
        String newName = scanner.nextLine(); // Get new name
        if (!newName.trim().isEmpty()) {
            task.setName(newName); // Update task name if provided
        }

        System.out.print("Enter new description (leave blank to keep unchanged): ");
        String newDescription = scanner.nextLine(); // Get new description
        if (!newDescription.trim().isEmpty()) {
            task.setDescription(newDescription); // Update task description if provided
        }

        LocalDateTime newDueDate = null;
        while (newDueDate == null) {
            System.out.print("Enter new due date and time (yyyy-MM-dd hh:mm a, leave blank to keep unchanged): ");
            String newDueDateString = scanner.nextLine(); // Get new due date
            if (newDueDateString.trim().isEmpty()) {
                break;
            }
            try {
                newDueDate = LocalDateTime.parse(newDueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")); // Parse the date string
                task.setDueDate(newDueDate); // Set the new due date
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again."); // Handle invalid date format
            }
        }

        String repeatOption = null;
        while (repeatOption == null) {
            System.out.print("Should this task repeat daily? (yes/no, leave blank to keep unchanged): ");
            repeatOption = scanner.nextLine(); // Get new repeat option
            if (repeatOption.trim().isEmpty()) {
                break;
            } else if (repeatOption.trim().equalsIgnoreCase("yes")) {
                task.setRepeating(true); // Set repeating to true
            } else if (repeatOption.trim().equalsIgnoreCase("no")) {
                task.setRepeating(false); // Set repeating to false
            } else {
                System.out.println("Invalid input. Please enter 'yes', 'no', or leave blank."); // Handle invalid input
                repeatOption = null;
            }
        }

        System.out.println("Task updated successfully!");
    }

    // Deletes a task
    private static void deleteTask() {
        int taskId = getValidIntegerInput("Enter the Task ID to delete: "); // Get the task ID to delete
        Task task = findTaskById(taskId); // Find the task by ID
        if (task == null) {
            System.out.println("Task not found."); // Handle the case where the task is not found
            return;
        }

        taskList.remove(task); // Remove the task from the list
        System.out.println("Task deleted successfully!");
    }

    // Finds a task by its ID
    private static Task findTaskById(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return task; // Return the task if found
            }
        }
        return null; // Return null if the task is not found
    }

    // Gets valid integer input from the user, handling potential errors
    private static int getValidIntegerInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt); // Display the prompt
            String line = scanner.nextLine(); // Read user input
            try {
                input = Integer.parseInt(line); // Try to parse the input as an integer
                return input; // Return the integer if successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer."); // Handle invalid input
            }
        }
    }

    // Checks for due tasks and provides alerts
    private static void checkAlerts() {
        while (true) {
            try {
                Thread.sleep(1000); // Sleep for 1 second
                LocalDateTime now = LocalDateTime.now(); // Get the current date and time
                List<Task> dueTasks = new ArrayList<>(); // List to store due tasks

                // Find tasks that are due or have already passed their due date
                for (Task task : taskList) {
                    if (task.getDueDate().isBefore(now) || task.getDueDate().isEqual(now)) {
                        dueTasks.add(task);
                    }
                }

                // Alert the user about due tasks
                for (Task task : dueTasks) {
                    System.out.println("\nALERT: Task is due!");
                    System.out.println(task); // Print the task details
                    if (task.isRepeating()) {
                        task.setDueDate(task.getDueDate().plusDays(1)); // Update the due date if the task is repeating
                    } else {
                        taskList.remove(task); // Remove the task if it does not repeat
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted state
            } catch (Exception e) {
                System.out.println("Error in alerting system: " + e.getMessage()); // Handle any other exceptions
            }
        }
    }

    // Calculates GPA
    private static void calculateGPA() {
        List<Course> courses = new ArrayList<>(); // List to store course objects
        Scanner scannerGPA = new Scanner(System.in); // Scanner for GPA input

        System.out.println("Welcome to the GPA Calculator for LCPS");
        System.out.println("Enter your courses. Type 'done' when you are finished.");

        while (true) {
            System.out.print("Enter Course Name (or type 'done' to finish): ");
            String courseName = scannerGPA.nextLine(); // Get course name
            if (courseName.equalsIgnoreCase("done")) {
                break; // Exit the loop if the user enters "done"
            }

            System.out.print("Enter your grade for " + courseName + " (0-100): ");
            double grade = scannerGPA.nextDouble(); // Get the grade
            if (grade < 0 || grade > 100) {
                System.out.println("Please enter a valid grade between 0 and 100.");
                scannerGPA.nextLine(); // Clear the buffer
                continue; // Skip to the next iteration if the grade is invalid
            }
            scannerGPA.nextLine(); // Consume the newline

            System.out.print("Enter Course Type (AP, DE, Honor, Academic): ");
            String courseType = scannerGPA.nextLine(); // Get the course type
            if (!isValidCourseType(courseType)) {
                System.out.println("Invalid course type. Please enter AP, DE, Honor, or Academic.");
                continue; // Skip to the next iteration if the course type is invalid
            }

            Course course = new Course(courseName, grade, courseType); // Create a new course object
            courses.add(course); // Add the course to the list
        }

        double gpa = calculateGPA(courses); // Calculate the GPA
        System.out.printf("Your calculated GPA is: %.2f%n", gpa); // Print the GPA
    }

    // Validates the course type input
    public static boolean isValidCourseType(String courseType) {
        return courseType.equalsIgnoreCase("AP") ||
                courseType.equalsIgnoreCase("DE") ||
                courseType.equalsIgnoreCase("Honor") ||
                courseType.equalsIgnoreCase("Academic");
    }

    // Calculates GPA based on a list of courses
    public static double calculateGPA(List<Course> courses) {
        double totalGPAPoints = 0.0; // Accumulated weighted GPA points
        int totalCourses = courses.size(); // Number of courses

        for (Course course : courses) {
            // Convert grade to GPA points using the extended LCPS scale
            double gpaPoints = 0.0;
            if (course.grade >= 98) { // A+
                gpaPoints = 4.3;
            } else if (course.grade >= 93) { // A
                gpaPoints = 4.0;
            } else if (course.grade >= 90) { // A-
                gpaPoints = 3.7;
            } else if (course.grade >= 87) { // B+
                gpaPoints = 3.3;
            } else if (course.grade >= 83) { // B
                gpaPoints = 3.0;
            } else if (course.grade >= 80) { // B-
                gpaPoints = 2.7;
            } else if (course.grade >= 77) { // C+
                gpaPoints = 2.3;
            } else if (course.grade >= 73) { // C
                gpaPoints = 2.0;
            } else if (course.grade >= 70) { // C-
                gpaPoints = 1.7;
            } else if (course.grade >= 67) { // D+
                gpaPoints = 1.3;
            } else if (course.grade >= 63) { // D
                gpaPoints = 1.0;
            } else if (course.grade >= 60) { // D-
                gpaPoints = 0.7;
            } else { // F
                gpaPoints = 0.0;
            }

            // Apply the GPA bump based on course type
            switch (course.courseType.toUpperCase()) {
                case "AP":
                case "DE":
                    gpaPoints += 1.0; // AP/DE courses get +1.0 bump
                    break;
                case "HONOR":
                    gpaPoints += 0.5; // Honors courses get +0.5 bump
                    break;
                case "ACADEMIC":
                    // No bump for Academic courses
                    break;
                default:
                    throw new IllegalArgumentException("Invalid course type: " + course.courseType);
            }

            // Add to the total GPA points
            totalGPAPoints += gpaPoints;
        }

        // Return the average GPA, or 0.0 if there are no courses
        return totalCourses > 0 ? totalGPAPoints / totalCourses : 0.0;
    }

    // Manages notes-related operations
    private static void manageNotes() {
        while (true) {
            System.out.println("\nNotes Management:");
            System.out.println("1. Create Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Back to Main Menu");

            int choice = getValidIntegerInput("Choose an option (1-5): "); // Get user's choice
            switch (choice) {
                case 1 -> createNote(); // Create a new note
                case 2 -> viewNotes(); // View all notes
                case 3 -> editNote(); // Edit a note
                case 4 -> deleteNote(); // Delete a note
                case 5 -> { return; } // Return to the main menu
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid input
            }
        }
    }

    // Creates a new note and adds it to the notes list
    private static void createNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine(); // Get the note title
        System.out.print("Enter note content: ");
        String content = scanner.nextLine(); // Get the note content

        Note note = new Note(title, content); // Create a new note object
        notesList.add(note); // Add the note to the list
        System.out.println("Note created successfully!");
    }

    // Displays all notes
    private static void viewNotes() {
        if (notesList.isEmpty()) {
            System.out.println("No notes to display."); // Handle the case where there are no notes
        } else {
            System.out.println("\nNotes:");
            for (int i = 0; i < notesList.size(); i++) {
                Note note = notesList.get(i);
                System.out.println((i + 1) + ". " + note.getTitle()); // Display the title of each note
            }
            int choice = getValidIntegerInput("Enter the note index you want to view: ") - 1; // Get user's choice to view a specific note
            if (choice >= 0 && choice < notesList.size()) {
                System.out.println(notesList.get(choice)); // Display the selected note
            } else {
                System.out.println("Invalid index. Returning to Notes Management."); // Handle invalid input
            }
        }
    }

    // Edits a note
    private static void editNote() {
        int noteIndex = getValidIntegerInput("Enter the note index to edit: ") - 1; // Get the index of the note to edit
        if (noteIndex >= 0 && noteIndex < notesList.size()) {
            Note note = notesList.get(noteIndex); // Get the note to edit
            System.out.println("Editing Note: " + note.getTitle()); // Display the title of the note being edited
            System.out.print("Enter new content (leave blank to keep unchanged): ");
            String newContent = scanner.nextLine(); // Get the new content
            if (!newContent.trim().isEmpty()) {
                note.setContent(newContent); // Update the content if provided
                System.out.println("Note updated successfully!");
            } else {
                System.out.println("No changes made."); // Handle the case where no changes were made
            }
        } else {
            System.out.println("Invalid note index."); // Handle invalid input
        }
    }

    // Deletes a note
    private static void deleteNote() {
        int noteIndex = getValidIntegerInput("Enter the note index to delete: ") - 1; // Get the index of the note to delete
        if (noteIndex >= 0 && noteIndex < notesList.size()) {
            notesList.remove(noteIndex); // Remove the note from the list
            System.out.println("Note deleted successfully!");
        } else {
            System.out.println("Invalid note index."); // Handle invalid input
        }
    }

    // Loads tasks from a file
    private static void loadTasks() {
        File file = new File(TASK_FILE); // Create a File object for the task file
        if (file.exists()) { // Check if the file exists
            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Use try-with-resources for automatic resource management
                String line;
                while ((line = br.readLine()) != null) { // Read the file line by line
                    String[] parts = line.split(","); // Split the line into parts based on the comma delimiter
                    int id = Integer.parseInt(parts[0]); // Parse the ID
                    String name = parts[1]; // Extract the name
                    String description = parts[2]; // Extract the description
                    LocalDateTime dueDate = LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")); // Parse the due date
                    boolean isRepeating = parts[4].equalsIgnoreCase("Yes"); // Parse the repeating status

                    Task task = new Task(name, description, dueDate, isRepeating); // Create a new task object
                    Task.setIdCounter(Math.max(Task.getNextId(), id + 1)); // Ensure the ID counter is updated
                    taskList.add(task); // Add the task to the list
                }
            } catch (IOException e) {
                System.out.println("Error reading tasks from file: " + e.getMessage()); // Handle IO exceptions
            } catch (Exception e) {
                System.out.println("Error parsing task data: " + e.getMessage()); // Handle parsing exceptions
            }
        }
    }

    // Saves tasks to a file
    private static void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASK_FILE))) { // Use try-with-resources for automatic resource management
            for (Task task : taskList) {
                bw.write(task.toString()); // Write the task data to the file
                bw.newLine(); // Write a new line
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage()); // Handle IO exceptions
        }
    }

    // Loads notes from a file
    private static void loadNotes() {
        File file = new File(NOTE_FILE); // Create a File object for the notes file
        if (file.exists()) { // Check if the file exists
            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Use try-with-resources for automatic resource management
                String line;
                while ((line = br.readLine()) != null) { // Read the title
                    String title = line;
                    String content = br.readLine(); // Read the content
                    notesList.add(new Note(title, content)); // Create a new note and add it to the list
                }
            } catch (IOException e) {
                System.out.println("Error reading notes from file: " + e.getMessage()); // Handle IO exceptions
            }
        }
    }

    // Saves notes to a file
    private static void saveNotes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOTE_FILE))) { // Use try-with-resources for automatic resource management
            for (Note note : notesList) {
                bw.write(note.getTitle()); // Write the title
                bw.newLine(); // Write a new line
                bw.write(note.getContent()); // Write the content
                bw.newLine(); // Write a new line
            }
        } catch (IOException e) {
            System.out.println("Error saving notes to file: " + e.getMessage()); // Handle IO exceptions
        }
    }

    // Manages volunteer hours-related operations
    private static void manageVolunteerHours() {
        while (true) {
            System.out.println("\nVolunteer Hours Tracking:");
            System.out.println("1. Add Volunteer Entry");
            System.out.println("2. View All Entries");
            System.out.println("3. Search Entries by Keyword");
            System.out.println("4. Check Total Logged Hours");
            System.out.println("5. Back to Main Menu");

            int choice = getValidIntegerInput("Choose an option (1-5): "); // Get the user's choice
            switch (choice) {
                case 1 -> addVolunteerEntry(); // Add a volunteer entry
                case 2 -> viewVolunteerEntries(); // View all volunteer entries
                case 3 -> searchVolunteerEntries(); // Search volunteer entries
                case 4 -> checkTotalLoggedHours(); // Check the total logged hours
                case 5 -> { return; } // Back to the main menu
                default -> System.out.println("Invalid choice, please try again."); // Handle invalid input
            }
        }
    }

    // Adds a new volunteer entry
    private static void addVolunteerEntry() {
        System.out.print("Enter description of volunteer work: ");
        String description = scanner.nextLine(); // Get the description of the volunteer work

        LocalDateTime date;
        while (true) {
            System.out.print("Enter date of volunteer work (yyyy-MM-dd): ");
            String dateString = scanner.nextLine(); // Get the date of the volunteer work
            try {
                date = LocalDateTime.parse(dateString + "T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME); // Parse the date (set time to midnight)
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again."); // Handle invalid date format
            }
        }

        double hours;
        while (true) {
            System.out.print("Enter number of hours volunteered: ");
            String hoursInput = scanner.nextLine(); // Get the number of hours volunteered
            try {
                hours = Double.parseDouble(hoursInput); // Parse the hours
                if (hours >= 0) break; // Ensure the number of hours is non-negative
                else System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number."); // Handle invalid input
            }
        }

        VolunteerEntry entry = new VolunteerEntry(description, date, hours); // Create a new VolunteerEntry object
        volunteerEntries.add(entry); // Add the entry to the list
        System.out.println("Volunteer entry added successfully!");
    }

    // Displays all volunteer entries
    private static void viewVolunteerEntries() {
        if (volunteerEntries.isEmpty()) {
            System.out.println("No volunteer entries to display."); // Handle the case where there are no entries
        } else {
            System.out.println("\nVolunteer Hours Entries:");
            for (int i = 0; i < volunteerEntries.size(); i++) {
                System.out.println((i + 1) + ". " + volunteerEntries.get(i)); // Display each entry
            }
        }
    }

    // Searches volunteer entries by a keyword
    private static void searchVolunteerEntries() {
        System.out.print("Enter keyword to search for in volunteer entries: ");
        String keyword = scanner.nextLine().toLowerCase(); // Get the keyword to search for

        List<VolunteerEntry> foundEntries = new ArrayList<>(); // List to store found entries

        for (VolunteerEntry entry : volunteerEntries) {
            if (entry.getDescription().toLowerCase().contains(keyword)) {
                foundEntries.add(entry); // Add the entry if the description contains the keyword
            }
        }

        if (foundEntries.isEmpty()) {
            System.out.println("No entries matched the keyword: " + keyword); // Handle the case where no entries match
        } else {
            System.out.println("Found Entries:");
            for (VolunteerEntry entry : foundEntries) {
                System.out.println(entry); // Display the found entries
            }
        }
    }

    // Checks and displays the total logged volunteer hours
    private static void checkTotalLoggedHours() {
        double totalHours = volunteerEntries.stream().mapToDouble(VolunteerEntry::getHours).sum(); // Calculate total hours
        System.out.println("Total logged volunteer hours: " + totalHours);

        // Optional: Allow the user to set a goal and compare
        System.out.print("Enter your volunteer hours goal: ");
        double goal = scanner.nextDouble(); // Get the volunteer hours goal
        scanner.nextLine(); // Consume newline
        System.out.printf("You have logged %.2f hours out of your goal of %.2f hours.%n", totalHours, goal);
    }



    private static void loadVolunteerEntries() {
        File file = new File(VOLUNTEER_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String description = line; // Read the description from the first line
                    LocalDateTime date = LocalDateTime.parse(br.readLine() + "T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME); // Read the date from the second line and parse it
                    double hours = Double.parseDouble(br.readLine()); // Read the hours from the third line and parse it
                    VolunteerEntry entry = new VolunteerEntry(description, date, hours); // Create a VolunteerEntry object
                    volunteerEntries.add(entry); // Add the created entry to the list
                }
            } catch (IOException e) {
                System.out.println("Error reading volunteer entries from file: " + e.getMessage()); // Handle any IO exceptions during file reading
            }
        }
    }

    private static void saveVolunteerEntries() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VOLUNTEER_FILE))) {
            for (VolunteerEntry entry : volunteerEntries) {
                bw.write(entry.getDescription()); // Write the description to the file
                bw.newLine(); // Write a newline character to separate entries
                bw.write(entry.getDate().toLocalDate().toString()); // Write the date (only the date part) to the file
                bw.newLine(); // Write a newline character
                bw.write(String.valueOf(entry.getHours())); // Write the hours to the file
                bw.newLine(); // Write a newline character
            }
        } catch (IOException e) {
            System.out.println("Error saving volunteer entries to file: " + e.getMessage()); // Handle any IO exceptions during file writing
        }
    }
}