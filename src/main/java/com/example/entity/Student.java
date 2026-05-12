package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Group tag is required")
    private String groupTag;

    // 1:1 relationship: yhdellä opiskelijalla on vain yksi opiskelijakortti
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private StudentCard studentCard;

    // 1:N relationship: yhdellä opiskelijalla voi olla monta kurssia
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    // Constructors

    public Student() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroupTag() { return groupTag; }

    public void setGroupTag(String groupTag) { this.groupTag = groupTag; }

    public StudentCard getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(StudentCard studentCard) {
        this.studentCard = studentCard;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}