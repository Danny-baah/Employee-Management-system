package Daniel.Employee.s.Management.System.controller;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Daniel.Employee.s.Management.System.model.Employee;
import Daniel.Employee.s.Management.System.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Controller;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;


    // Adding New Employee (POST)
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        logger.info("Controller: Attempting to create employee: {}", employee.getName());

        // Age validation
        if (employee.getAge() < 0) {
            logger.warn("Controller: Failed to create employee. Invalid age: {}", employee.getAge());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be a positive number");
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);
        logger.info("Controller: Employee {} created successfully with ID: {}", savedEmployee.getName(), savedEmployee.getId());

        return ResponseEntity.ok(savedEmployee);
    }

    // Displaying All Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Controller: Fetching all employees");

        List<Employee> employees = employeeService.getAllEmployees();
        logger.info("Controller: Fetched {} employees", employees.size());

        return ResponseEntity.ok(employees);
    }

    // Displaying a Specific Employee (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        logger.info("Controller: Fetching employee with ID: {}", id);

        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            logger.warn("Controller: Employee with ID {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        logger.info("Controller: Found employee with ID: {}", id);
        return ResponseEntity.ok(employee);
    }

    // Updating the State of an Employee (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        logger.info("Controller: Attempting to update employee with ID: {}", id);

        if (employee.getAge() < 0) {
            logger.warn("Controller: Failed to update employee with ID {}. Invalid age: {}", employee.getAge());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be a positive number");
        }

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        logger.info("Controller: Employee with ID: {} updated successfully", id);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.warn("Controller: Attempting to delete employee with ID: {}", id);

        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            logger.info("Controller: Employee with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();  // 204 No Content
        }

        logger.warn("Controller: Failed to delete employee with ID: {}. Employee not found");
        return ResponseEntity.notFound().build();  // 404 Not Found
    }
}
