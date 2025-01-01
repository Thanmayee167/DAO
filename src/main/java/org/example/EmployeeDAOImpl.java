package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDAOImpl implements EmployeeDAO {
  private Connection connection;

  public EmployeeDAOImpl() {
    try {
      Properties properties = PropertiesLoader.loadProperties("application.properties");
      if (properties != null) {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to database successfully.");
      } else {
        System.out.println("Unable to load database properties.");
      }
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      e.printStackTrace();
    }
  }

  @Override
  public List<Employee> getAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM employees");
      while (rs.next()) {
        Employee employee =
            new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("position"));
        employees.add(employee);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }

  @Override
  public Employee getEmployeeById(int id) {
    Employee employee = null;
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setPosition(rs.getString("position"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employee;
  }

  @Override
  public void addEmployee(Employee employee) {
    String query = "INSERT INTO employees (name, position) VALUES (?, ?)";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = connection.prepareStatement(query);
      // Setting values from the employee object to the PreparedStatement
      preparedStatement.setString(1, employee.getName());
      preparedStatement.setString(2, employee.getPosition());

      // Execute the insert operation
      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("A new employee was added successfully!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the PreparedStatement
      try {
        if (preparedStatement != null) preparedStatement.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  @Override
  public void updateEmployee(Employee employee) {
    PreparedStatement preparedStatement = null;
    try {
      String query = "UPDATE employees SET name = ?, position = ? WHERE id = ?";
      preparedStatement = connection.prepareStatement(query);

      // Setting new values for the specified employee
      preparedStatement.setString(1, employee.getName());
      preparedStatement.setString(2, employee.getPosition());
      preparedStatement.setInt(3, employee.getId());

      // Execute the update operation
      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Employee was updated successfully!");
      } else {
        System.out.println("No employee was updated. Please check the ID.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // Close the PreparedStatement
      try {
        if (preparedStatement != null) preparedStatement.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  @Override
  public void deleteEmployee(int id) {
    PreparedStatement preparedStatement = null;
    try {
      String query = "DELETE FROM employees WHERE id = ?";
      preparedStatement = connection.prepareStatement(query);

      // Setting the id for the employee to delete
      preparedStatement.setInt(1, id);

      // Execute the delete operation
      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Employee deleted successfully!");
      } else {
        System.out.println("No employee found with ID: " + id);
      }
    } catch (SQLException e) {
      System.out.println("Error occurred while deleting the employee.");
      e.printStackTrace();
    } finally {
      // Close the PreparedStatement
      try {
        if (preparedStatement != null) preparedStatement.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  } // Ensure you close the connection when it is no longer needed

  public void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        System.out.println("Database connection closed.");
      }
    } catch (SQLException e) {
      System.out.println("Failed to close connection.");
      e.printStackTrace();
    }
  }
}
