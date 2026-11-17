import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple command-line application for managing employee details.
 * Compile with: javac EmployeeManagement.java
 * Run with:     java EmployeeManagement
 */
public class EmployeeManagement {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Employee Management ===");
        System.out.println("1. Add employee");
        System.out.println("2. Display all employees");
        System.out.println("3. Search employee by ID");
        System.out.println("4. Exit");
    }

    private static void addEmployee() {
        System.out.print("Employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }
        if (findEmployeeById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Department: ");
        String department = scanner.nextLine().trim();
        if (department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        double salary = readNonNegativeDouble("Salary: ");
        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }

        System.out.printf("%-12s %-24s %-20s %12s%n", "ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%-12s %-24s %-20s %12.2f%n",
                    employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary());
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee found:");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Department: " + employee.getDepartment());
            System.out.printf("Salary: %.2f%n", employee.getSalary());
        }
    }

    private static Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Show the common validation message below.
            }
            System.out.println("Please enter a valid non-negative salary.");
        }
    }

    private static final class Employee {
        private final String id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private String getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        private String getDepartment() {
            return department;
        }

        private double getSalary() {
            return salary;
        }
    }
}
