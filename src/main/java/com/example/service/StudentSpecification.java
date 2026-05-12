package com.example.service;

import com.example.entity.Student;
import com.example.entity.StudentCard;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.example.entity.Student;
import com.example.entity.StudentCard;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterStudents(
            String lastName,
            String firstName,
            String groupTag
    ) {

        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();

            // Sukunimi
            if (lastName != null && !lastName.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + lastName.toLowerCase() + "%"
                        )
                );
            }

            // Etunimi
            if (firstName != null && !firstName.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + firstName.toLowerCase() + "%"
                        )
                );
            }

            // Ryhmätunnus
            if (groupTag != null && !groupTag.isBlank()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("groupTag")),
                                "%" + groupTag.toLowerCase() + "%"
                        )
                );
            }

            return predicates;
        };
    }
}