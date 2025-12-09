package com.ems;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeFileDAO();
        EmployeeService service = new EmployeeService(dao);

        System.out.println("\n=== Employee Management System (Console) ===\n");

        while (true) {
            printMenu();
            System.out.print("Enter choice: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> addEmployee(service);
                case 2 -> updateEmployee(service);
                case 3 -> deleteEmployee(service);
                case 4 -> listEmployees(service);
                case 5 -> searchByName(service);
                case 6 -> {
                    System.out.println("Exiting. Bye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. List Employees");
        System.out.println("5. Search by Name");
        System.out.println("6. Exit");
    }

    private static void addEmployee(EmployeeService service) {
        System.out.print("Name: ");
        String name = readLine();
        System.out.print("Department: ");
        String dept = readLine();
        System.out.print("Salary: ");
        double sal = readDouble();

        Employee e = service.addEmployee(name, dept, sal);
        System.out.println("Added: " + e);
    }

    private static void updateEmployee(EmployeeService service) {
        System.out.print("ID to update: ");
        int id = readInt();

        if (service.findById(id).isEmpty()) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = readLine();
        System.out.print("New Department: ");
        String dept = readLine();
        System.out.print("New Salary: ");
        double sal = readDouble();

        boolean ok = service.updateEmployee(id, name, dept, sal);
        System.out.println(ok ? "Updated successfully." : "Update failed.");
    }

    private static void deleteEmployee(EmployeeService service) {
        System.out.print("ID to delete: ");
        int id = readInt();

        boolean ok = service.deleteEmployee(id);
        System.out.println(ok ? "Deleted." : "No employee with that ID.");
    }

    private static void listEmployees(EmployeeService service) {
        List<Employee> list = service.listEmployees();

        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- Employees ---");
        list.forEach(System.out::println);
        System.out.println("-----------------\n");
    }

    private static void searchByName(EmployeeService service) {
        System.out.print("Search query: ");
        String q = readLine();

        List<Employee> res = service.searchByName(q);

        if (res.isEmpty()) {
            System.out.println("No matches.");
            return;
        }

        res.forEach(System.out::println);
    }

    // input helpers
    private static int readInt() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static String readLine() {
        return sc.nextLine().trim();
    }
}
