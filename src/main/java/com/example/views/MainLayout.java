package com.example.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainLayout extends AppLayout {

    public MainLayout() {

        Header header = new Header();
        H1 h1 = new H1("Opiskelijat ja kurssit");
        header.add(h1);


        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem(
                "Opiskelijat",
                StudentView.class,
                VaadinIcon.USERS.create()
        ));

        nav.addItem(new SideNavItem(
                "Kurssit",
                CourseView.class,
                VaadinIcon.BOOK.create()
        ));


        Footer footer = new Footer(new Span("© 2026 Student Management"));

        VerticalLayout drawerContent = new VerticalLayout();
        drawerContent.setSizeFull();
        drawerContent.setPadding(false);
        drawerContent.setSpacing(false);

        h1.getStyle().set("padding", "var(--lumo-space-m)");

        drawerContent.add(header, nav);

        // tämä "työntää footerin alas"
        drawerContent.expand(nav);

        addToDrawer(drawerContent, footer);
    }
}