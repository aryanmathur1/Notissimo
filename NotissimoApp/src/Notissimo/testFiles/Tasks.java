package Notissimo.testFiles;

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

    public Task(String name, String description, LocalDateTime dueDate) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Task ID: " + id + ", Name: " + name + ", Description: " + description
                + ", Due Date: " + dueDate.format(formatter);
    }
}

public class Tasks {
    private static List<Task> taskList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Thread alertThread = new Thread(Tasks::checkAlerts);  // Background thread for alerts
        alertThread.setDaemon(true);
        alertThread.start();

        while (true) {
            System.out.println("\nTask Management System:");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    createTask();
                    displaySortedTasks();
                }
                case 2 -> displaySortedTasks();
                case 3 -> {
                    editTask();
                    displaySortedTasks();
                }
                case 4 -> {
                    deleteTask();
                    displaySortedTasks();
                }
                case 5 -> {
                    System.out.println("Exiting Task Management System...");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void createTask() {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter due date and time (yyyy-MM-dd HH:mm): ");
        String dueDateString = scanner.nextLine();
        LocalDateTime dueDate;
        try {
            dueDate = LocalDateTime.parse(dueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Task creation failed.");
            return;
        }

        Task task = new Task(name, description, dueDate);
        taskList.add(task);
        System.out.println("Task created successfully!");
    }

    private static void displaySortedTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }

        // Sort tasks by due date
        taskList.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate()));

        System.out.println("\nTasks (ordered by due date):");
        for (Task task : taskList) {
            System.out.println(task);
        }
    }

    private static void editTask() {
        System.out.print("Enter the Task ID to edit: ");
        int taskId = Integer.parseInt(scanner.nextLine());

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

        System.out.print("Enter new due date and time (yyyy-MM-dd HH:mm, leave blank to keep unchanged): ");
        String newDueDateString = scanner.nextLine();
        if (!newDueDateString.trim().isEmpty()) {
            try {
                LocalDateTime newDueDate = LocalDateTime.parse(newDueDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                task.setDueDate(newDueDate);
            } catch (Exception e) {
                System.out.println("Invalid date format. Due date not updated.");
            }
        }

        System.out.println("Task updated successfully!");
    }

    private static void deleteTask() {
        System.out.print("Enter the Task ID to delete: ");
        int taskId = Integer.parseInt(scanner.nextLine());

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

    private static void checkAlerts() {
        while (true) {
            try {
                Thread.sleep(1000); // Check every second
                LocalDateTime now = LocalDateTime.now();
                List<Task> dueTasks = new ArrayList<>();

                for (Task task : taskList) {
                    if (task.getDueDate().isBefore(now) || task.getDueDate().isEqual(now)) {
                        dueTasks.add(task);
                    }
                }

                for (Task task : dueTasks) {
                    System.out.println("\n*** ALERT: Task is due! ***");
                    System.out.println(task);
                    taskList.remove(task); // Remove the task after alert
                }
            } catch (Exception e) {
                System.out.println("Error in alerting system: " + e.getMessage());
            }
        }
    }
}
