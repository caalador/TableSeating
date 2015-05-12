package org.percepta.mgrankvi.demo;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Random;

import org.percepta.mgrankvi.Colours;
import org.percepta.mgrankvi.Position;
import org.percepta.mgrankvi.SeatingLayout;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.TableSeatFillDirection;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.contact.Contact;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("demo")
@Title("MyComponent Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.percepta.mgrankvi.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    private static Random rand = new Random(System.currentTimeMillis());

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        final SeatingLayout component = new SeatingLayout("images/Room.png");
        component.setWidth("1280px");
        component.setHeight("1500px");
        component.setMultiple(true);

        final TextField search = new TextField("Contact search");
        search.setWidth("125px");
        Button searchButton = new Button("Search", clickEvent -> {
            component.clearHighLights();
            List<Contact> contact = component.findContact(search.getValue());
            if (contact.size() == 1) {
                component.highlightContact(contact.get(0));
            } else if (contact.size() > 1) {
                buildMultiselectionWindow(contact, component);
            } else {
                Notification.show("No contact found for search string: "
                        + search.getValue());
            }
        });

        component.addComponent(search, new Position(50, 50));
        component.addComponent(searchButton, new Position(175, 50));

        populateRoom(component);

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.addComponent(component);
        layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

    private void populateRoom(SeatingLayout component) {
        int i = 0;
        Table table = new Table(10);
        table.setTableName("A");
        table.setSeatSize(40);
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(-20);

        component.addComponent(table, new Position(60, 200));

        table = new Table(10);
        table.setTableName("C");
        table.setSeatSize(60);
        table.setFillDirection(TableSeatFillDirection.FIRST);
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(-20);

        component.addComponent(table, new Position(60, 450));

        table = new Table(10);
        table.setTableName("E");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(20);

        component.addComponent(table, new Position(60, 750));

        table = new Table(10);
        table.setTableName("G");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(20);

        component.addComponent(table, new Position(60, 1000));

        table = new Table(10);
        table.setTableName("B");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(20);

        component.addComponent(table, new Position(890, 200));

        table = new Table(10);
        table.setTableName("D");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(new Contact());
        // table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(20);

        component.addComponent(table, new Position(890, 450));

        table = new Table(10);
        table.setTableName("F");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(-20);

        component.addComponent(table, new Position(890, 750));

        table = new Table(10);
        table.setTableName("H");
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(-20);

        component.addComponent(table, new Position(890, 1000));

        table = new Table(2);
        table.setTableName("Main Table");
        table.addContact(new Contact(Contact.uuid(), "Bride", null,
                Colours.cssColours[i++],
                "We loved with a love that was more than love.",
                "Edgar Allan Poe"));
        table.addContact(new Contact(Contact.uuid(), "Groom",
                "VAADIN/images/gloom.png", Colours.cssColours[i++],
                "The sweetest of all sounds is that of the voice of the woman we love.",
                "Jean de la Bruyere"));

        table.setTableSeatPlacing(TableSeatPlacing.ALL_UP);

        component.addComponent(table, new Position(560, 80));
    }

    protected void buildMultiselectionWindow(List<Contact> contact,
            final SeatingLayout component) {
        VerticalLayout select = new VerticalLayout();

        NativeSelect<Contact> list = new NativeSelect<>(
                "Select wanted contact");
        list.setWidth("100%");
        select.addComponent(list);

        list.setItems(contact);
        list.setItemCaptionGenerator(Contact::getName);

        final Window w = new Window("Multiple contacts found");
        w.setContent(select);
        w.setResizable(false);

        list.addValueChangeListener(valueChange -> {
            component.highlightContact(valueChange.getValue());
            w.close();
        });

        addWindow(w);
        w.center();
    }

    public Contact createContact(int id) {
        final String surName = Names.surNames[rand
                .nextInt(Names.surNames.length)];
        final String firstName = Names.firstNames[rand
                .nextInt(Names.firstNames.length)];
        return new Contact(Contact.uuid(), firstName + " " + surName, null,
                Colours.cssColours[id % Colours.cssColours.length], "", "");
    }

}
