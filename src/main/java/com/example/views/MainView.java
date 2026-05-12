package com.example.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H2("Tervetuloa"));
    }
}