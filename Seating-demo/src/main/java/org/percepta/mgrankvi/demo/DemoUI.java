package org.percepta.mgrankvi.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.percepta.mgrankvi.Colours;
import org.percepta.mgrankvi.Position;
import org.percepta.mgrankvi.SeatingLayout;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.contact.Contact;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Random;

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
        search.setImmediate(true);
        search.setWidth("125px");
        Button searchButton = new Button("Search", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                component.clearHighLights();
                List<Contact> contact = component.findContact(search.getValue());
                if (contact.size() == 1) {
                    component.highlightContact(contact.get(0));
                } else {
//                    StringBuilder sb = new StringBuilder();
                    for (Contact c : contact) {
//                        sb.append(c.name).append("\n");
                        component.highlightContact(contact.get(0));
                    }
//                    Notification.show("Multiple contacts found", sb.toString(), Notification.Type.HUMANIZED_MESSAGE);

                }
            }
        });

        component.addComponent(search, new Position(50, 50));
        component.addComponent(searchButton, new Position(175, 50));

        int i = 0;
        Table table = new Table(10, 130);
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

        table = new Table(10, 130);
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));
        table.addContact(createContact(i++));

        table.setTableRotation(-20);

        component.addComponent(table, new Position(60, 450));

        table = new Table(10, 130);
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

        component.addComponent(table, new Position(60, 750));

        table = new Table(10, 130);
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

        table = new Table(10, 130);
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

        table = new Table(10, 130);
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

        component.addComponent(table, new Position(890, 450));

        table = new Table(10, 130);
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

        table = new Table(10, 130);
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

        table = new Table(2, 130);
        table.addContact(new Contact(Contact.uuid(), "Bride", null, Colours.cssColours[i++], "We loved with a love that was more than love.", "Edgar Allan Poe"));
        table.addContact(new Contact(Contact.uuid(), "Groom", null, Colours.cssColours[i++], "The sweetest of all sounds is that of the voice of the woman we love.", "Jean de la Bruyere"));

        table.setTableSeatPlacing(TableSeatPlacing.ALL_UP);

        component.addComponent(table, new Position(560, 80));

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponent(component);
        layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

    public Contact createContact(int id) {
        final String surName = Names.surNames[rand.nextInt(Names.surNames.length)];
        final String firstName = Names.firstNames[rand.nextInt(Names.firstNames.length)];
        return new Contact(Contact.uuid(), firstName + " " + surName, null, Colours.cssColours[id], "", "");
    }

}
