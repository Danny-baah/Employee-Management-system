package Daniel.Employee.s.Management.System.controller;

import Daniel.Employee.s.Management.System.model.Employee;
import Daniel.Employee.s.Management.System.repository.EmployeeRepo;
import Daniel.Employee.s.Management.System.service.EmployeeService;
import Daniel.Employee.s.Management.System.exception.CustomExceptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UiController {

    private static final Logger logger = LoggerFactory.getLogger(UiController.class);
    private final EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;


    @GetMapping({"/home","/"})
    public String showEmployeeManagementSystem(Model model) {
        // Fetching counts and active employees
        long totalEmployees = employeeService.countAllActiveEmployees(); // Only active employees
        long maleEmployees = employeeService.countActiveEmployeesBySex("Male"); // Only active male employees
        long femaleEmployees = employeeService.countActiveEmployeesBySex("Female"); // Only active female employees
        List<Employee> activeEmployees = employeeService.getAllActiveEmployees(); // Fetch only active employees

        // Logging the values to check if they're fetched correctly
        logger.info("Active Employees List: {}", activeEmployees);
        logger.info("Total Employees: {}", totalEmployees);
        logger.info("Male Employees: {}", maleEmployees);
        logger.info("Female Employees: {}", femaleEmployees);


        // Adding data to the model
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("maleEmployees", maleEmployees);
        model.addAttribute("femaleEmployees", femaleEmployees);

        // Check if employees list is empty before rendering
        if (activeEmployees.isEmpty()) {
            model.addAttribute("noEmployeesMessage", "There are currently no active employees in the system.");
        } else {
            model.addAttribute("employees", activeEmployees);
        }

        return "index";  // Return the Thymeleaf view for active employees
    }


    @GetMapping("/form")
    public String addEmployeeForm(Model model) {
        logger.info("Controller: Displaying add new employee form");
        model.addAttribute("employee", new Employee()); // Adding a blank employee object for the form
        return "form";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        try {
            logger.info("Controller: Adding new employee - {}", employee);
            employeeService.saveEmployee(employee);
            return "redirect:/home"; // Redirecting to home to refresh the employee list
        }
        catch (CustomExceptions.EmailAlreadyException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "form";  // Return to the form if there's an error
        }
    }

    @DeleteMapping("deleteEmp/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/home";
    }


    @GetMapping("/DeletedEmployees")
    public String deletedEmployees(Model model) {
        List<Employee> inactiveEmployees = employeeService.getAllInActiveEmployees();
        model.addAttribute("deletedemployees", inactiveEmployees);
        logger.info("Controller: Displaying deleted employees");
        return "table";
    }

    @PutMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        employeeService.updateEmployee(employee.getId(), employee);
        return "redirect:/home";
    }

}
