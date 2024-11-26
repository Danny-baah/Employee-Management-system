package Daniel.Employee.s.Management.System.service;

import Daniel.Employee.s.Management.System.model.Employee;
import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employeeDetails);
    
    boolean deleteEmployee(Long id);

    long countAllEmployees();

    long countEmployeesBySex(String male);

    long countAllActiveEmployees();

    long countActiveEmployeesBySex(String male);

    List<Employee> getAllActiveEmployees();

    List<Employee> getAllInActiveEmployees();

    void updateEmployee(Object employeeDetails);
}
