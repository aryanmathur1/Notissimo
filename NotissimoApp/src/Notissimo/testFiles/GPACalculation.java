package Notissimo.testFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class GPACalculation {

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

    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the GPA Calculator for LCPS");
        System.out.println("Enter your courses. Type 'done' when you are finished.");

        while (true) {
            System.out.print("Enter Course Name (or type 'done' to finish): ");
            String courseName = scanner.nextLine();
            if (courseName.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter your grade for " + courseName + " (0-100): ");
            double grade = scanner.nextDouble();
            if (grade < 0 || grade > 100) {
                System.out.println("Please enter a valid grade between 0 and 100.");
                scanner.nextLine(); // Clear the buffer
                continue;
            }
            scanner.nextLine(); // Consume the newline

            System.out.print("Enter Course Type (AP, DE, Honor, Academic): ");
            String courseType = scanner.nextLine();
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
}