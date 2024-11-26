package Daniel.Employee.s.Management.System.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "employees")
public class Employee {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastname;

    // The name is a concatenation of first name and last name
    private String name;

    @NotBlank(message = "Sex is required")
    @Pattern(regexp = "(?i)male|female", message = "Sex must be either 'Male' or 'Female'")
    private String sex;

    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    @Min(value = 15, message = "Age must be a positive number")
    private Integer age;

    @NotBlank(message = "Email is required")
//    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // Default constructor
    public Employee() {}

    // Parameterized constructor
    public Employee(String firstname, String lastname, String sex, LocalDate dateOfBirth, String address, Integer age, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.name = firstname + " " + lastname; // Automatically concatenate first and last names
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.age = age;
        this.email = email;
        this.isDeleted = false; // Default to false
    }

    // Update the name field whenever first or last name is changed
    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.name = firstname + " " + this.lastname; // Update the name
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.name = this.firstname + " " + lastname; // Update the name
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}


