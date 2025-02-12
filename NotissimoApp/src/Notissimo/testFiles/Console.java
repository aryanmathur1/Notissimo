package Notissimo.testFiles;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private boolean isRepeating;

    public Task(String name, String description, LocalDateTime dueDate, boolean isRepeating) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isRepeating = isRepeating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return id + "," + name + "," + description + "," + dueDate.format(formatter) + "," + (isRepeating ? "Yes" : "No");
    }

    public static void setIdCounter(int idCounter) {
        Task.idCounter = idCounter;
    }

    public static int getNextId() {
        return idCounter;
    }
}

class Course {
    public String name;
    public double grade;
    public String courseType;

    public Course(String name, double grade, String courseType) {
        this.name = name;
        this.grade = grade;
        this.courseType = courseType;
    }

    public static double calculateWeightedGrade(double grade, String courseType) {
        switch (courseType.toUpperCase()) {
            case "AP":
            case "DE":
                return Math.min(grade + 1.0, 100);
            case "HONOR":
                return Math.min(grade + 0.5, 100);
            case "ACADEMIC":
                return grade;
            default:
                return grade;
        }
    }
}

class Note {
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }
}

public class Console {
    private static List<Task> taskList = new ArrayList<>();
    private static List<Note> notesList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    private static final String TASK_FILE = "tasks.txt";
    private static final String NOTE_FILE = "notes.txt";
    private static final String COURSE_FILE = "courses.txt"; // Placeholder for course management

    public static void main(String[] args) {
        loadTasks();
        loadNotes();

        Thread alertThread = new Thread(Console::checkAlerts);
        alertThread.setDaemon(true);
        alertThread.start();

        while (running) {
            System.out.println("\nWelcome to the Console Application:");
            System.out.println("1. Task Management System");
            System.out.println("2. GPA Calculation");
            System.out.println("3. Notes Management");
            System.out.println("4. Exit");

            int choice = getValidIntegerInput("Choose an option (1-4): ");
            switch (choice) {
                case 1 -> manageTasks();
                case 2 -> calculateGPA();
                case 3 -> manageNotes();
                case 4 -> {
                    saveTasks(); // Save tasks before exiting
                    saveNotes(); // Save notes before exiting
                    System.out.println("Exiting Console Application...");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void manageTasks() {
        while (true) {
            System.out.println("\nTask Management System:");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Back to Main Menu");

            int choice = getValidIntegerInput("Choose an option (1-5): ");
            switch (choice) {
                case 1 -> createTask();
                case 2 -> displaySortedTasks();
                case 3 -> editTask();
                case 4 -> deleteTask();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void createTask() {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        LocalDateTime dueDate;
        while (true) {
            System.out.print("Enter due date and time (yyyy-MM-dd hh:mm a): ");
            String dueDateString = scanner.nextLine();
            try {
                dueDate = LocalDateTime.parse(dueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        boolean isRepeating;
        while (true) {
            System.out.print("Should this task repeat daily? (yes/no): ");
            String repeatInput = scanner.nextLine().trim().toLowerCase();
            if (repeatInput.equals("yes")) {
                isRepeating = true;
                break;
            } else if (repeatInput.equals("no")) {
                isRepeating = false;
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        // Create new Task and add to the list
        Task task = new Task(name, description, dueDate, isRepeating);
        taskList.add(task);
        System.out.println("Task created successfully!");
    }

    private static void displaySortedTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }

        taskList.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate()));

        System.out.println("\nTasks (ordered by due date):");
        for (Task task : taskList) {
            System.out.println(task);
        }
    }

    private static void editTask() {
        int taskId = getValidIntegerInput("Enter the Task ID to edit: ");
        Task task = findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Editing Task: " + task);
        System.out.print("Enter new name (leave blank to keep unchanged): ");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) {
            task.setName(newName);
        }

        System.out.print("Enter new description (leave blank to keep unchanged): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.trim().isEmpty()) {
            task.setDescription(newDescription);
        }

        LocalDateTime newDueDate = null;
        while (newDueDate == null) {
            System.out.print("Enter new due date and time (yyyy-MM-dd hh:mm a, leave blank to keep unchanged): ");
            String newDueDateString = scanner.nextLine();
            if (newDueDateString.trim().isEmpty()) {
                break;
            }
            try {
                newDueDate = LocalDateTime.parse(newDueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
                task.setDueDate(newDueDate);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        String repeatOption = null;
        while (repeatOption == null) {
            System.out.print("Should this task repeat daily? (yes/no, leave blank to keep unchanged): ");
            repeatOption = scanner.nextLine();
            if (repeatOption.trim().isEmpty()) {
                break;
            } else if (repeatOption.trim().equalsIgnoreCase("yes")) {
                task.setRepeating(true);
            } else if (repeatOption.trim().equalsIgnoreCase("no")) {
                task.setRepeating(false);
            } else {
                System.out.println("Invalid input. Please enter 'yes', 'no', or leave blank.");
                repeatOption = null;
            }
        }

        System.out.println("Task updated successfully!");
    }

    private static void deleteTask() {
        int taskId = getValidIntegerInput("Enter the Task ID to delete: ");
        Task task = findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        taskList.remove(task);
        System.out.println("Task deleted successfully!");
    }

    private static Task findTaskById(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private static int getValidIntegerInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                input = Integer.parseInt(line);
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    private static void checkAlerts() {
        while (true) {
            try {
                Thread.sleep(1000);
                LocalDateTime now = LocalDateTime.now();
                List<Task> dueTasks = new ArrayList<>();

                for (Task task : taskList) {
                    if (task.getDueDate().isBefore(now) || task.getDueDate().isEqual(now)) {
                        dueTasks.add(task);
                    }
                }

                for (Task task : dueTasks) {
                    System.out.println("\nALERT: Task is due!");
                    System.out.println(task);
                    if (task.isRepeating()) {
                        task.setDueDate(task.getDueDate().plusDays(1));
                    } else {
                        taskList.remove(task);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Error in alerting system: " + e.getMessage());
            }
        }
    }

    private static void calculateGPA() {
        List<Course> courses = new ArrayList<>();
        Scanner scannerGPA = new Scanner(System.in);

        System.out.println("Welcome to the GPA Calculator for LCPS");
        System.out.println("Enter your courses. Type 'done' when you are finished.");

        while (true) {
            System.out.print("Enter Course Name (or type 'done' to finish): ");
            String courseName = scannerGPA.nextLine();
            if (courseName.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter your grade for " + courseName + " (0-100): ");
            double grade = scannerGPA.nextDouble();
            if (grade < 0 || grade > 100) {
                System.out.println("Please enter a valid grade between 0 and 100.");
                scannerGPA.nextLine(); // Clear the buffer
                continue;
            }
            scannerGPA.nextLine(); // Consume the newline

            System.out.print("Enter Course Type (AP, DE, Honor, Academic): ");
            String courseType = scannerGPA.nextLine();
            if (!isValidCourseType(courseType)) {
                System.out.println("Invalid course type. Please enter AP, DE, Honor, or Academic.");
                continue;
            }

            Course course = new Course(courseName, grade, courseType);
            courses.add(course);
        }

        double gpa = calculateGPA(courses);
        System.out.printf("Your calculated GPA is: %.2f%n", gpa);
    }

    public static boolean isValidCourseType(String courseType) {
        return courseType.equalsIgnoreCase("AP") ||
                courseType.equalsIgnoreCase("DE") ||
                courseType.equalsIgnoreCase("Honor") ||
                courseType.equalsIgnoreCase("Academic");
    }

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

    private static void manageNotes() {
        while (true) {
            System.out.println("\nNotes Management:");
            System.out.println("1. Create Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Back to Main Menu");

            int choice = getValidIntegerInput("Choose an option (1-5): ");
            switch (choice) {
                case 1 -> createNote();
                case 2 -> viewNotes();
                case 3 -> editNote();
                case 4 -> deleteNote();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void createNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        Note note = new Note(title, content);
        notesList.add(note);
        System.out.println("Note created successfully!");
    }

    private static void viewNotes() {
        if (notesList.isEmpty()) {
            System.out.println("No notes to display.");
        } else {
            System.out.println("\nNotes:");
            for (int i = 0; i < notesList.size(); i++) {
                Note note = notesList.get(i);
                System.out.println((i + 1) + ". " + note.getTitle());
            }
            int choice = getValidIntegerInput("Enter the note index you want to view: ") - 1;
            if (choice >= 0 && choice < notesList.size()) {
                System.out.println(notesList.get(choice));
            } else {
                System.out.println("Invalid index. Returning to Notes Management.");
            }
        }
    }

    private static void editNote() {
        int noteIndex = getValidIntegerInput("Enter the note index to edit: ") - 1;
        if (noteIndex >= 0 && noteIndex < notesList.size()) {
            Note note = notesList.get(noteIndex);
            System.out.println("Editing Note: " + note.getTitle());
            System.out.print("Enter new content (leave blank to keep unchanged): ");
            String newContent = scanner.nextLine();
            if (!newContent.trim().isEmpty()) {
                note.setContent(newContent);
                System.out.println("Note updated successfully!");
            } else {
                System.out.println("No changes made.");
            }
        } else {
            System.out.println("Invalid note index.");
        }
    }

    private static void deleteNote() {
        int noteIndex = getValidIntegerInput("Enter the note index to delete: ") - 1;
        if (noteIndex >= 0 && noteIndex < notesList.size()) {
            notesList.remove(noteIndex);
            System.out.println("Note deleted successfully!");
        } else {
            System.out.println("Invalid note index.");
        }
    }

    private static void loadTasks() {
        File file = new File(TASK_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    LocalDateTime dueDate = LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
                    boolean isRepeating = parts[4].equalsIgnoreCase("Yes");

                    Task task = new Task(name, description, dueDate, isRepeating);
                    Task.setIdCounter(Math.max(Task.getNextId(), id + 1)); // Ensure the ID counter is updated
                    taskList.add(task);
                }
            } catch (IOException e) {
                System.out.println("Error reading tasks from file: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error parsing task data: " + e.getMessage());
            }
        }
    }

    private static void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASK_FILE))) {
            for (Task task : taskList) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    private static void loadNotes() {
        File file = new File(NOTE_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String title = line;
                    String content = br.readLine(); // Get next line as content
                    notesList.add(new Note(title, content));
                }
            } catch (IOException e) {
                System.out.println("Error reading notes from file: " + e.getMessage());
            }
        }
    }

    private static void saveNotes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOTE_FILE))) {
            for (Note note : notesList) {
                bw.write(note.getTitle());
                bw.newLine(); // Write content on the next line
                bw.write(note.getContent());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving notes to file: " + e.getMessage());
        }
    }
}