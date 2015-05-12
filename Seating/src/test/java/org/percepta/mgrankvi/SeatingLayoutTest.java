package org.percepta.mgrankvi;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.List;

public class SeatingLayoutTest {

    List<Contact> contacts = Lists
            .newArrayList(new Contact(Contact.uuid(), "Single Person"),
            new Contact(Contact.uuid(), "Tripplets One"),
            new Contact(Contact.uuid(), "Tripplets Two"),
            new Contact(Contact.uuid(), "Tripplets Three"));

    @Test
    public void testSeatingLayoutTableAdding() {
        SeatingLayout layout = new SeatingLayout();

        Table one = new Table(2);

        layout.addComponent(one);

        Assert.assertEquals("Missing added component.", 1, layout.getComponentCount());
    }

    @Test
    public void testSearchPerson() {
        SeatingLayout layout = new SeatingLayout();

        Table one = new Table(2);
        one.addContact(contacts.get(2));

        layout.addComponent(one);
        Table two = new Table(2);
        two.addContacts(contacts.get(0), contacts.get(1));

        layout.addComponent(two);

        Assert.assertEquals("Expected one result", 1, layout.findContact("Single").size());

        Assert.assertEquals("Expected multiple results", 2, layout.findContact("Tripplets").size());
    }

    @Test
    public void testHighlightContact() {
        SeatingLayout layout = new SeatingLayout();

        Table one = new Table(2);
        one.addContact(contacts.get(2));

        layout.addComponent(one);
        Table two = new Table(2);
        two.addContacts(contacts.get(0), contacts.get(1));

        layout.addComponent(two);

        Table three = Mockito.mock(Table.class);
        Mockito.when(three.findContact(Mockito.anyString())).thenReturn(Lists.newArrayList(contacts.get(3)));

        layout.addComponent(three);

        List<Contact> result = layout.findContact("Three");
        Assert.assertEquals("Found the wrong amount of contacts", 1, result.size());

        layout.highlightContact(result.get(0));

        Mockito.verify(three, Mockito.atLeastOnce()).highlightContact(contacts.get(3));

    }
}