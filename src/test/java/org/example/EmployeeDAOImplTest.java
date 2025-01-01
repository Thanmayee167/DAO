package org.example;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EmployeeDAOImplTest {
  private static EmployeeDAOImpl employeeDAO;

  @BeforeAll
  public static void setup() {
    employeeDAO = new EmployeeDAOImpl();
  }

  @AfterEach
  public void tearDown() {
    List<Employee> allEmployees = employeeDAO.getAllEmployees();
    allEmployees.forEach(employee -> employeeDAO.deleteEmployee(employee.getId()));
  }

  @Test
  void testAddAndGetEmployee() {
    Employee emp = new Employee();
    emp.setName("John Doe");
    emp.setPosition("Developer");
    employeeDAO.addEmployee(emp);

    Employee retrievedEmp =
        employeeDAO.getEmployeeById(5); // Assuming IDs are auto-generated starting from 1
    Assertions.assertNotNull(retrievedEmp);
    Assertions.assertEquals("John Doe", retrievedEmp.getName());
    Assertions.assertEquals("Developer", retrievedEmp.getPosition());
  }

  @Test
  void testUpdateEmployee() {
    // Add an employee to update
    Employee emp = new Employee();
    emp.setName("John Doe");
    emp.setPosition("Developer");
    employeeDAO.addEmployee(emp);
    emp.setId(2);
    // Update employee details
    emp.setName("Jane Doe");
    emp.setPosition("Senior Developer");
    employeeDAO.updateEmployee(emp);
    Employee updatedEmp = employeeDAO.getEmployeeById(2);
    Assertions.assertEquals("Jane Doe", updatedEmp.getName());
    Assertions.assertEquals("Senior Developer", updatedEmp.getPosition());
  }

  @Test
  void testDeleteEmployee() {
    // Add an employee to delete
    Employee emp = new Employee();
    emp.setName("John Doe");
    emp.setPosition("Developer");
    employeeDAO.addEmployee(emp);

    employeeDAO.deleteEmployee(1);

    Employee deletedEmp = employeeDAO.getEmployeeById(1);
    Assertions.assertNull(deletedEmp);
  }

  @Test
  void testGetAllEmployees() {
    Employee emp1 = new Employee();
    emp1.setName("John Doe");
    emp1.setPosition("Developer");
    employeeDAO.addEmployee(emp1);

    Employee emp2 = new Employee();
    emp2.setName("Jane Doe");
    emp2.setPosition("Manager");
    employeeDAO.addEmployee(emp2);

    List<Employee> employees = employeeDAO.getAllEmployees();
    Assertions.assertEquals(2, employees.size());
  }
}
