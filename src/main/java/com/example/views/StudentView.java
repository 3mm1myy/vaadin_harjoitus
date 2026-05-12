package com.example.views;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import com.example.service.StudentSpecification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.util.List;

@Route(value = "students", layout = MainLayout.class)
@PageTitle("Opiskelijat")
@PermitAll
public class StudentView extends VerticalLayout {

    private final StudentRepository studentRepository;
    private final Grid<Student> grid = new Grid<>(Student.class);

    // FILTER FIELDS
    private final TextField lastNameField = new TextField("Sukunimi");
    private final TextField firstNameField = new TextField("Etunimi");
    private final TextField groupTagField = new TextField("Ryhmä");

    public StudentView(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;

        setSizeFull();

        // GRID
        configureGrid();

        // ADD BUTTON
        Button addButton =
                new Button("Lisää opiskelija", new Icon(VaadinIcon.PLUS));

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addButton.addClickListener(e ->
                openFormDialog(new Student()));

        // SEARCH BUTTON
        Button searchBtn = new Button("Hae", e -> updateGrid());

        // CLEAR BUTTON
        Button clearBtn = new Button("Tyhjennä", e -> {

            lastNameField.clear();
            firstNameField.clear();
            groupTagField.clear();

            updateGrid();
        });

        HorizontalLayout filters = new HorizontalLayout();

        filters.add(
                lastNameField,
                firstNameField,
                groupTagField,
                searchBtn,
                clearBtn
        );
        filters.setAlignItems(Alignment.END);

        add(
                new HorizontalLayout(addButton),
                filters,
                grid
        );

        updateGrid();
    }

    private void configureGrid() {

        grid.removeAllColumns();

        grid.addColumn(Student::getId)
                .setHeader("ID")
                .setWidth("70px")
                .setFlexGrow(0);

        grid.addColumn(Student::getLastName)
                .setHeader("Sukunimi");

        grid.addColumn(Student::getFirstName)
                .setHeader("Etunimi");

        grid.addColumn(Student::getEmail)
                .setHeader("Email");

        grid.addColumn(Student::getPhone)
                .setHeader("Puhelin");

        grid.addColumn(Student::getAddress)
                .setHeader("Osoite");

        grid.addColumn(Student::getGroupTag)
                .setHeader("Ryhmä");

        grid.addColumn(student ->
                student.getStudentCard() != null
                        && student.getStudentCard().isActive()
                        && student.getStudentCard()
                        .getExpiryDate()
                        .isAfter(LocalDate.now())
                        ? "Kyllä"
                        : "Ei"
        ).setHeader("Opiskelijakortti voimassa");

        // EDIT + DELETE
        grid.addComponentColumn(student -> {

            Button edit =
                    new Button(new Icon(VaadinIcon.EDIT));

            edit.addClickListener(e ->
                    openFormDialog(student));

            Button delete =
                    new Button(new Icon(VaadinIcon.TRASH));

            delete.addClickListener(e -> {

                studentRepository.delete(student);

                updateGrid();

                Notification.show("Poistettu");
            });

            return new HorizontalLayout(edit, delete);

        }).setHeader("Toiminnot");

        grid.setSizeFull();
    }

    private void openFormDialog(Student student) {

        StudentFormDialog dialog =
                new StudentFormDialog(
                        studentRepository,
                        this::updateGrid
                );

        dialog.openFor(student);
    }

    private void updateGrid() {

        List<Student> students =
                studentRepository.findAll(
                        StudentSpecification.filterStudents(
                                lastNameField.getValue(),
                                firstNameField.getValue(),
                                groupTagField.getValue()
                        )
                );

        grid.setItems(students);
    }
}