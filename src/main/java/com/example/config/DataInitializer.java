package com.example.config;

import com.example.entity.Role;
import com.example.entity.AppUser;
import com.example.entity.Student;
import com.example.entity.StudentCard;
import com.example.repository.StudentRepository;
import com.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    private final StudentRepository studentRepo;

    public DataInitializer(UserRepository repo, PasswordEncoder encoder, StudentRepository studentRepo) {
        this.userRepo = repo;
        this.encoder = encoder;

        this.studentRepo = studentRepo;
    }

    @PostConstruct
    public void created() {
        System.out.println("DATA INITIALIZER CREATED");
    }

    @Override
    public void run(String... args) {

        if (userRepo.count() == 0) {

            AppUser admin = new AppUser();
            admin.setUsername("Admin");
            admin.setPasswordHash(encoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN, Role.SUPER));

            AppUser user = new AppUser();
            user.setUsername("User");
            user.setPasswordHash(encoder.encode("user123"));
            user.setRoles(Set.of(Role.USER));

            System.out.println("Creating users...");
            userRepo.save(admin);
            userRepo.save(user);
        }

        if (studentRepo.count() == 0) {

            Student s1 = new Student();
            s1.setFirstName("Matti");
            s1.setLastName("Meikäläinen");
            s1.setEmail("matti@example.com");
            s1.setPhone("0401234567");
            s1.setAddress("Testitie 1, 33100 Tampere");
            s1.setGroupTag("ET23KM");

            StudentCard c1 = new StudentCard();
            c1.setActive(true);
            c1.setExpiryDate(LocalDate.now().plusYears(2));

            s1.setStudentCard(c1);
            c1.setStudent(s1);


            Student s2 = new Student();
            s2.setFirstName("Liisa");
            s2.setLastName("Virtanen");
            s2.setEmail("liisa@example.com");
            s2.setPhone("0509876543");
            s2.setAddress("Testitie 2, 33100 Tampere");
            s2.setGroupTag("ES22SM");

            StudentCard c2 = new StudentCard();
            c2.setActive(false);
            c2.setExpiryDate(LocalDate.now().plusMonths(6));
            s2.setStudentCard(c2);
            c2.setStudent(s2);


            Student s3 = new Student();
            s3.setFirstName("Pekka");
            s3.setLastName("Korhonen");
            s3.setEmail("pekka@example.com");
            s3.setPhone("0451112222");
            s3.setAddress("Rantatie 51, 33250 Tampere");
            s3.setGroupTag("ET25SP");

            StudentCard c3 = new StudentCard();
            c3.setActive(true);
            c3.setExpiryDate(LocalDate.now().plusYears(1));

            s3.setStudentCard(c3);
            c3.setStudent(s3);


            studentRepo.saveAll(List.of(s1, s2, s3));

            System.out.println("Sample students added.");
        }
    }
}