package com.example.service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> searchStudents(
            String lastName,
            String firstName,
            String groupTag
    ) {

        return studentRepository.findAll(
                StudentSpecification.filterStudents(
                        lastName,
                        firstName,
                        groupTag
                )
        );
    }
}
