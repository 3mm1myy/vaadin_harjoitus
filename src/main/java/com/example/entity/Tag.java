package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    // M:N relationship: tagit voi olla liitettyinä useampiin kursseihin ja useilla kursseilla voi olla samoja tageja
    @ManyToMany(mappedBy = "tags")
    private Set<Course> courses = new HashSet<>();

    // Constructors

    public Tag() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}