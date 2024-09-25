package Daniel.Employee.s.Management.System.service;

import Daniel.Employee.s.Management.System.model.Employee;
import java.util.List;

public interface EmployeeService {

    // Save a new employee
    Employee saveEmployee(Employee employee);

    // Get a list of all employees
    List<Employee> getAllEmployees();

    // Get an employee by ID
    Employee getEmployeeById(Long id);

    // Update an existing employee
    Employee updateEmployee(Long id, Employee employeeDetails);

    // Delete an employee by ID
    void deleteEmployee(Long id);
}
