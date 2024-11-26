package Daniel.Employee.s.Management.System.service;

import Daniel.Employee.s.Management.System.exception.CustomExceptions;
import Daniel.Employee.s.Management.System.model.Employee;
import Daniel.Employee.s.Management.System.repository.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepo employeeRepository;

    public EmployeeServiceImpl(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Get only active employees
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findAllByIsDeletedFalse();
    }

    // Get deleted employees
    public List<Employee> getDeletedEmployees() {
        return employeeRepository.findAllByIsDeletedTrue();
    }

    @Override
    public long countEmployeesBySex(String sex) {
        long count = employeeRepository.countBySexIgnoreCase(sex);
        logger.info("Count for sex '{}': {}", sex, count);  // Log the count
        return count;
    }

    public long countAllActiveEmployees() {
        return employeeRepository.countByIsDeletedFalse();
    }

    public long countActiveEmployeesBySex(String sex) {
        return employeeRepository.countBySexAndIsDeletedFalse(sex);
    }

    @Override
    public List<Employee> getAllActiveEmployees() {
        return employeeRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<Employee> getAllInActiveEmployees() {
        return employeeRepository.findAllByIsDeletedTrue();
    }

    @Override
    public void updateEmployee(Object employeeDetails) {

    }

    @Override
    public long countAllEmployees() {
        return employeeRepository.count();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Concatenate firstname and lastname to create the name
        employee.setName(employee.getFirstname() + " " + employee.getLastname());
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
            throw new CustomExceptions.EmailAlreadyException("Email already exists: " + employee.getEmail());
        }


        if (employee.getAge() < 18) {
            throw new CustomExceptions.EmployeeUnderageException("Employee must be 18 years or older.");
        }

        logger.info("Attempting to save employee: {}", employee);
        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Employee saved successfully with ID: {}", savedEmployee.getId());
        return savedEmployee;
    }


    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Retrieved {} employees", employees.size());
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent() && !employee.get().isDeleted()) {
            logger.info("Employee found: {}", employee.get());
            return employee.get();
        } else {
            logger.error("Employee not found with ID: {}", id);
            throw new CustomExceptions.EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        logger.info("Attempting to update employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.EmployeeNotFoundException("Employee not found with id: " + id));

        // Check if the employee is marked as deleted
        if (existingEmployee.isDeleted()) {
            throw new CustomExceptions.EmployeeNotFoundException("Cannot update deleted employee with ID: " + id);
        }

        // Updating the employee's details
        existingEmployee.setFirstname(employeeDetails.getFirstname());
        existingEmployee.setLastname(employeeDetails.getLastname());
        existingEmployee.setName(employeeDetails.getFirstname() + " " + employeeDetails.getLastname());
        existingEmployee.setSex(employeeDetails.getSex());
        existingEmployee.setDateOfBirth(employeeDetails.getDateOfBirth());
        existingEmployee.setAddress(employeeDetails.getAddress());
        existingEmployee.setAge(employeeDetails.getAge());
        existingEmployee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        logger.info("Employee with ID: {} updated successfully", updatedEmployee.getId());
        return updatedEmployee;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.EmployeeNotFoundException("Employee not found with id: " + id));

        // Check if already marked as deleted
        if (existingEmployee.isDeleted()) {
            logger.warn("Employee with ID: {} is already marked as deleted", id);
            return false;
        }

        // Mark the employee as deleted
        existingEmployee.setDeleted(true);
        employeeRepository.save(existingEmployee);
        logger.info("Employee with ID: {} marked as deleted", id);
        return true;
    }
}
