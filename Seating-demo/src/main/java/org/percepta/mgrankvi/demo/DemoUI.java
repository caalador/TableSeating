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

@Theme("demo")
@Title("MyComponent Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.percepta.mgrankvi.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // Initialize our new UI component
        final SeatingLayout component = new SeatingLayout("images/WaterTower.jpg");
        component.setSizeFull();
        int i = 0;
        Table table = new Table(135, 130);
        Contact c = new Contact("Perre Paras", null, Colours.cssColours[i++], "The sweetest of all sounds is that of the voice of the woman we love.", "Jean de la Bruyere");
        Contact c1 = new Contact("Pelle peloton", null, Colours.cssColours[i++], "Where there is love there is life.", "Mahatma Gandhi");
        Contact  c2 = new Contact("Fenix Fenkoli", null, Colours.cssColours[i++], "We loved with a love that was more than love.", "Edgar Allan Poe");
        table.addContacts(c, c1, c2);

        table.setTableRotation(-20);

        component.addComponent(table, new Position(200, 60));

        table = new Table(135, 130);
        c = new Contact("Heidi", null, Colours.cssColours[i++], "We loved with a love that was more than love.", "Edgar Allan Poe");
        c1 = new Contact("Mikael", null, Colours.cssColours[i++], "The sweetest of all sounds is that of the voice of the woman we love.", "Jean de la Bruyere");
        table.addContacts(c, c1);
        table.setTableSeatPlacing(TableSeatPlacing.ALL_UP);

        component.addComponent(table, new Position(1000, 60));

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponent(component);
        layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

}
