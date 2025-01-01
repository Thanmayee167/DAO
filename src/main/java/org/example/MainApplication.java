package org.example;

import java.util.List;
import java.util.Scanner;

public class MainApplication {

  public static void main(String[] args) {
    EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
    Scanner scanner = new Scanner(System.in);

    try {
      while (true) {
        System.out.println("Employee Management System");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Retrieve Employee");
        System.out.println("4. Retrieve all Employees");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1:
            // Add employee
            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();
            System.out.print("Enter employee position: ");
            String position = scanner.nextLine();
            Employee newEmployee = new Employee();
            newEmployee.setName(name);
            newEmployee.setPosition(position);
            employeeDAO.addEmployee(newEmployee);
            break;
          case 2:
            // Update employee
            System.out.print("Enter employee ID: ");
            int updateId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new employee name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new employee position: ");
            String newPosition = scanner.nextLine();
            Employee updateEmployee = new Employee();
            updateEmployee.setId(updateId);
            updateEmployee.setName(newName);
            updateEmployee.setPosition(newPosition);
            employeeDAO.updateEmployee(updateEmployee);
            break;
          case 3:
            // Retrieve employee
            System.out.print("Enter employee ID to retrieve: ");
            int retrieveId = scanner.nextInt();
            Employee retrievedEmployee = employeeDAO.getEmployeeById(retrieveId);
            if (retrievedEmployee != null) {
              System.out.println("Employee ID: " + retrievedEmployee.getId());
              System.out.println("Employee Name: " + retrievedEmployee.getName());
              System.out.println("Employee Position: " + retrievedEmployee.getPosition());
            } else {
              System.out.println("No employee found with ID: " + retrieveId);
            }
            break;
          case 4:
            // Retrieves all Employees
            List<Employee> employees = employeeDAO.getAllEmployees();
            employees.forEach(System.out::println);
            break;
          case 5:
            // Delete employee
            System.out.print("Enter employee ID to delete: ");
            int deleteId = scanner.nextInt();
            employeeDAO.deleteEmployee(deleteId);
            break;
          case 6:
            // Exit
            System.out.println("Exiting the system...");
            return;
          default:
            System.out.println("Invalid choice, please enter a correct option.");
            break;
        }
      }
    } finally {
      scanner.close();
      employeeDAO.closeConnection(); // Make sure to close the database connection
      System.out.println("System shutdown properly.");
    }
  }
}
