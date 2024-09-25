package Daniel.Employee.s.Management.System.controller;

import Daniel.Employee.s.Management.System.model.Employee;
import Daniel.Employee.s.Management.System.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService  employeeService;

    // Constructor-based dependency injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        //  age validation
        if (employee.getAge() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be a positive number");
        }

        //  email validation
        if (!employee.getEmail().contains("@") || !employee.getEmail().endsWith(".com")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must contain '@' and end with '.com'");
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        // Same validation checks as in createEmployee
        if (employee.getAge() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be a positive number");
        }

        if (!employee.getEmail().contains("@") || !employee.getEmail().endsWith(".com")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must contain '@' and end with '.com'");
        }

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
