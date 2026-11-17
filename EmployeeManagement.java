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
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number from 1 to 6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Employee Management ===");
        System.out.println("1. Add employee");
        System.out.println("2. Display all employees");
        System.out.println("3. Search employee by ID");
        System.out.println("4. Update employee details");
        System.out.println("5. Delete employee");
        System.out.println("6. Exit");
    }

    private static void addEmployee() {
        String id = readNonEmpty("Employee ID: ", "Employee ID");
        if (findEmployeeById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readNonEmpty("Name: ", "Name");
        String department = readNonEmpty("Department: ", "Department");
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
            printEmployee(employee);
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Press Enter to keep the current value.");
        System.out.print("Name [" + employee.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            employee.setName(name);
        }

        System.out.print("Department [" + employee.getDepartment() + "]: ");
        String department = scanner.nextLine().trim();
        if (!department.isEmpty()) {
            employee.setDepartment(department);
        }

        while (true) {
            System.out.print("Salary [" + employee.getSalary() + "]: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }

            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    employee.setSalary(salary);
                    break;
                }
            } catch (NumberFormatException ignored) {
                // Show the validation message below.
            }
            System.out.println("Please enter a valid non-negative salary, or press Enter to keep the current value.");
        }

        System.out.println("Employee updated successfully.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee deleted successfully.");
    }

    private static void printEmployee(Employee employee) {
        System.out.println("Employee found:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.printf("Salary: %.2f%n", employee.getSalary());
    }

    private static Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static String readNonEmpty(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(fieldName + " cannot be empty.");
        }
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
        private String name;
        private String department;
        private double salary;

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

        private void setName(String name) {
            this.name = name;
        }

        private String getDepartment() {
            return department;
        }

        private void setDepartment(String department) {
            this.department = department;
        }

        private double getSalary() {
            return salary;
        }

        private void setSalary(double salary) {
            this.salary = salary;
        }
    }
}
