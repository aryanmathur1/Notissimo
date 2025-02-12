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
        this.isRepeating = repeating;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return "Task ID: " + id + ", Name: " + name + ", Description: " + description
                + ", Due Date: " + dueDate.format(formatter) + ", Repeating: " + (isRepeating ? "Yes" : "No");
    }
}

public class Tasks {
    private static List<Task> taskList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Thread alertThread = new Thread(Tasks::checkAlerts);
        alertThread.setDaemon(true);
        alertThread.start();

        while (true) {
            System.out.println("\nTask Management System:");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");

            int choice = getValidIntegerInput("Choose an option (1-5): ");
            switch (choice) {
                case 1 -> { createTask(); displaySortedTasks(); }
                case 2 -> displaySortedTasks();
                case 3 -> { editTask(); displaySortedTasks(); }
                case 4 -> { deleteTask(); displaySortedTasks(); }
                case 5 -> { System.out.println("Exiting Task Management System..."); return; }
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
}