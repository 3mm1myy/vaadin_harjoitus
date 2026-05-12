package com.example.views;

import com.example.entity.Student;
import com.example.entity.StudentCard;
import com.example.repository.StudentRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class StudentFormDialog extends Dialog {

    private final StudentRepository studentRepository;
    private final Runnable onSaveCallback;

    private final Binder<Student> binder = new Binder<>(Student.class);

    // ✅ KENTÄT LUKSUILE (tärkeä korjaus)
    private Checkbox active;
    private DatePicker expiryDate;

    private Student currentStudent;

    public StudentFormDialog(StudentRepository studentRepository, Runnable onSaveCallback) {
        this.studentRepository = studentRepository;
        this.onSaveCallback = onSaveCallback;

        setWidth("450px");

        // ====== BASIC FIELDS ======
        TextField lastName = new TextField("Sukunimi");
        TextField firstName = new TextField("Etunimi");
        TextField email = new TextField("Email");
        TextField phone = new TextField("Puhelin");
        TextField address = new TextField("Osoite");
        TextField groupTag = new TextField("Ryhmätunnus");

        // ====== STUDENT CARD FIELDS ======
        active = new Checkbox("Opiskelijakortti");
        expiryDate = new DatePicker("Voimassa asti");

        expiryDate.setEnabled(false);

        active.addValueChangeListener(e -> {
            boolean isActive = e.getValue();
            expiryDate.setEnabled(isActive);

            if (!isActive) {
                expiryDate.clear();
            }
        });

        // ====== VALIDATIONS ======
        binder.forField(firstName)
                .asRequired("Pakollinen")
                .withValidator(new StringLengthValidator("Min 2 merkkiä", 2, null))
                .bind(Student::getFirstName, Student::setFirstName);

        binder.forField(lastName)
                .asRequired("Pakollinen")
                .withValidator(new StringLengthValidator("Min 2 merkkiä", 2, null))
                .bind(Student::getLastName, Student::setLastName);

        binder.forField(email)
                .asRequired("Pakollinen")
                .withValidator(new EmailValidator("Virheellinen email"))
                .bind(Student::getEmail, Student::setEmail);

        binder.forField(phone)
                .asRequired("Pakollinen")
                .withValidator(new RegexpValidator("Vain numerot", "^[0-9]{5,15}$"))
                .bind(Student::getPhone, Student::setPhone);

        binder.forField(address)
                .asRequired("Pakollinen")
                .withValidator(new StringLengthValidator("Min 5 merkkiä", 5, null))
                .bind(Student::getAddress, Student::setAddress);
        binder.forField(groupTag)
                .asRequired("Pakollinen")
                .withValidator(new StringLengthValidator("Min 5 merkkiä", 5, null))
                .bind(Student::getGroupTag, Student::setGroupTag);

        FormLayout form = new FormLayout(
                firstName,
                lastName,
                email,
                phone,
                address,
                groupTag,
                active,
                expiryDate
        );

        // ====== SAVE ======
        Button save = new Button("Tallenna", e -> {

            if (currentStudent == null) {
                currentStudent = new Student();
            }

            try {
                binder.writeBean(currentStudent);

                // StudentCard käsittely
                StudentCard card = currentStudent.getStudentCard();

                if (card == null) {
                    card = new StudentCard();
                    currentStudent.setStudentCard(card);
                }

                card.setStudent(currentStudent);
                card.setActive(active.getValue());
                card.setExpiryDate(
                        expiryDate.getValue()
                );

                studentRepository.save(currentStudent);

                onSaveCallback.run();
                close();

                Notification.show("Tallennettu");

            } catch (Exception ex) {
                Notification.show("Tarkista kentät");
            }
        });

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancel = new Button("Peruuta", e -> close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        add(form, new HorizontalLayout(save, cancel));
    }

    // ====== OPEN EDIT / NEW ======
    public void openFor(Student student) {

        this.currentStudent = student;

        if (student.getStudentCard() == null) {
            student.setStudentCard(new StudentCard());
        }

        binder.readBean(student);

        StudentCard card = student.getStudentCard();

        active.setValue(card.isActive());

        expiryDate.setValue(
                card.getExpiryDate()
        );

        expiryDate.setEnabled(active.getValue());

        open();
    }
}