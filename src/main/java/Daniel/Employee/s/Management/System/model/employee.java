package Daniel.Employee.s.Management.System.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents an employee entity in the system.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique id for each employee

    @Column(nullable = false)
    private String name; // Employee's name

    @Column(nullable = false)
    private String sex; // Employee's sex

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth; // Employee's date of birth

    @Column(nullable = false)
    private String address; // Employee's address

    @Column(nullable = false)
    private Integer age; // Employee's age

    @Column(nullable = false, unique = true)
    private String email; // Employee's email address

    // Default constructor
    public Employee() {}

    // Parameterized constructor
    public Employee(String name, String sex, LocalDate dateOfBirth, String address, Integer age, String email) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.age = age;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
