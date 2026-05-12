package com.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class StudentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate expiryDate;

    private boolean active;

    // 1:1 relationship: yksi kortti yhdellä opiskelijalla
    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;

    // Constructors

    public StudentCard() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}