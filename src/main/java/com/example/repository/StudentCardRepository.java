package com.example.repository;

import com.example.entity.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {
}