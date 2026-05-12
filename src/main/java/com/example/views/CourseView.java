package com.example.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "courses", layout = MainLayout.class)
@PageTitle("Courses")
@PermitAll
public class CourseView extends VerticalLayout {

    public CourseView() {
        add(new H1("Courses"));
    }
}