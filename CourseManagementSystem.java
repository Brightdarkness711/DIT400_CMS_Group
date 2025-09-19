package pre_oop_assignment;

import java.io.*;
import java.util.*;

public class CourseManagementSystem {

    // Constants and global arrays
    static final int MAX_COURSES = 500;
    static String[] courseIds = new String[MAX_COURSES];
    static String[] titles = new String[MAX_COURSES];
    static int[] creditHours = new int[MAX_COURSES];
    static int courseCount = 0;

    static String currentUser;

    // Load courses from file
    static void loadCourses() {
        try (BufferedReader in = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            courseCount = 0;
            while ((line = in.readLine()) != null && courseCount < MAX_COURSES) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courseIds[courseCount] = parts[0];
                    titles[courseCount] = parts[1];
                    creditHours[courseCount] = Integer.parseInt(parts[2]);
                    courseCount++;
                }
            }
        } catch (IOException e) {
            // Ignore if file doesn’t exist yet
        }
    }

    // Save courses to file
    static void saveCourses() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("courses.txt"))) {
            for (int i = 0; i < courseCount; i++) {
                out.write(courseIds[i] + "," + titles[i] + "," + creditHours[i]);
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving courses.");
        }
    }

    // Register user
    static boolean registerUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        try (BufferedReader in = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    System.out.println("Username already exists.");
                    return false;
                }
            }
        } catch (IOException e) {
            // File may not exist, ignore
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter("users.txt", true))) {
            out.write(username + "," + password);
            out.newLine();
        } catch (IOException e) {
            System.out.println("Error registering user.");
            return false;
        }

        System.out.println("Registration successful.");
        return true;
    }

    // Login user
    static boolean loginUser(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try (BufferedReader in = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    currentUser = username;
                    System.out.println("Login successful. Welcome " + username + "!");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file.");
        }

        System.out.println("Invalid credentials.");
        return false;
    }

    // List courses
    static void listCourses() {
        if (courseCount == 0) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("\n--- Course List ---");
        for (int i = 0; i < courseCount; i++) {
            System.out.println(courseIds[i] + " | " + titles[i] + " | " + creditHours[i] + " hrs");
        }
    }

    // Add a course
    static void addCourse(Scanner scanner) {
        if (courseCount >= MAX_COURSES) {
            System.out.println("Course limit reached.");
            return;
        }

        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(id)) {
                System.out.println("Course ID already exists.");
                return;
            }
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter credit hours (1-6): ");
        int hours = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (hours < 1 || hours > 6) {
            System.out.println("Invalid credit hours.");
            return;
        }

        courseIds[courseCount] = id;
        titles[courseCount] = title;
        creditHours[courseCount] = hours;
        courseCount++;
        saveCourses();
        System.out.println("Course added successfully.");
    }

    // Delete a course
    static void deleteCourse(Scanner scanner) {
        System.out.print("Enter course ID to delete: ");
        String id = scanner.nextLine();

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(id)) {
                for (int j = i; j < courseCount - 1; j++) {
                    courseIds[j] = courseIds[j + 1];
                    titles[j] = titles[j + 1];
                    creditHours[j] = creditHours[j + 1];
                }
                courseCount--;
                saveCourses();
                System.out.println("Course deleted.");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    // Update a course
    static void updateCourse(Scanner scanner) {
        System.out.print("Enter course ID to update: ");
        String id = scanner.nextLine();

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(id)) {
                System.out.print("Enter new title: ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter new credit hours (1-6): ");
                int newHours = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (newHours < 1 || newHours > 6) {
                    System.out.println("Invalid credit hours.");
                    return;
                }

                titles[i] = newTitle;
                creditHours[i] = newHours;
                saveCourses();
                System.out.println("Course updated.");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    // Search courses
    static void searchCourse(Scanner scanner) {
        System.out.print("Enter course ID or title keyword: ");
        String keyword = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i].equals(keyword) || titles[i].contains(keyword)) {
                System.out.println(courseIds[i] + " | " + titles[i] + " | " + creditHours[i] + " hrs");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching course found.");
        }
    }

    // Menu
    static void menu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Course Management Menu ---");
            System.out.println("1. Add Course");
            System.out.println("2. Delete Course");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. List All Courses");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: addCourse(scanner); break;
                case 2: deleteCourse(scanner); break;
                case 3: searchCourse(scanner); break;
                case 4: updateCourse(scanner); break;
                case 5: listCourses(); break;
                case 6: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadCourses();
        int option;
        do {
            System.out.println("\n===|| Welcome to the Course Management System ||===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1: registerUser(scanner); break;
                case 2: if (loginUser(scanner)) menu(scanner); break;
                case 3: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid option.");
            }
        } while (option != 3);
        scanner.close();
    }
}

	