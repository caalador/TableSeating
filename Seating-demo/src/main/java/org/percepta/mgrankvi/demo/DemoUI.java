package org.percepta.mgrankvi.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.percepta.mgrankvi.Colours;
import org.percepta.mgrankvi.Position;
import org.percepta.mgrankvi.SeatingLayout;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.Random;

@Theme("demo")
@Title("MyComponent Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

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
        int i = 0;
        Table table = new Table(0, 130);
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

        table = new Table(0, 130);
        table.addContact(new Contact("Bride", null, Colours.cssColours[i++], "We loved with a love that was more than love.", "Edgar Allan Poe"));
        table.addContact(new Contact("Groom", null, Colours.cssColours[i++], "The sweetest of all sounds is that of the voice of the woman we love.", "Jean de la Bruyere"));

        table.setTableSeatPlacing(TableSeatPlacing.ALL_UP);

        component.addComponent(table, new Position(635, 60));

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
        return new Contact(firstName + " " + surName, null, Colours.cssColours[id], "", "");
    }

}
