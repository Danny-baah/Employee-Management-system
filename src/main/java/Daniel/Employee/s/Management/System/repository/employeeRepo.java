//performing CRUD operations on Employee entities

package Daniel.Employee.s.Management.System.repository;

import Daniel.Employee.s.Management.System.model.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findAllByIsDeletedFalse();
    List<Employee> findAllByIsDeletedTrue();
    long countByIsDeletedFalse();
    long countBySexAndIsDeletedFalse(String sex);
    long countBySexIgnoreCase(String sex);

    @Override
    List<Employee> findAll();

    boolean existsByFirstnameAndLastnameAndIsDeletedFalse(@NotBlank(message = "First name is required") String firstname, @NotBlank(message = "Last name is required") String lastname);

    Optional<Employee> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);

    /*@Query(value = "select * from employees where is_deleted = true", nativeQuery = true)
    List<Employee> findDeletedEmp();*/
}
