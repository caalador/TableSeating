package org.percepta.mgrankvi;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.List;

public class TableTest {

    List<Contact> contacts = Lists
            .newArrayList(new Contact(Contact.uuid(), "Single Person"),
            new Contact(Contact.uuid(), "Tripplets One"),
            new Contact(Contact.uuid(), "Tripplets Two"),
            new Contact(Contact.uuid(), "Tripplets Three"));

    @Test
    public void testAddingContactsToTable() {
        Table t = new Table(5);
        t.addContact(contacts.get(0));

        Assert.assertFalse("No contact found when one should exist", t.getContacts().isEmpty());

        t.addContact(contacts.get(1));

        Assert.assertEquals("Contact amount is wrong after addContact(Contact contact)", 2, t.getContacts().size());

        t.addContacts(contacts.get(2), contacts.get(3));

        Assert.assertEquals("Contact amount is wrong after addContacts(Contact... contacts)", 4, t.getContacts().size());
    }

    @Test
    public void testFindSingleMatchForTable() {
        Table t = new Table(5);

        t.addContacts(contacts.toArray(new Contact[contacts.size()]));

        Assert.assertEquals("Got too many matches", 1, t.findContact("Single").size());
        Assert.assertEquals("Got too many matches", 1, t.findContact("One").size());
    }

    @Test
    public void testFindMultipleMatchForTable() {
        Table t = new Table(5);

        t.addContacts(contacts.toArray(new Contact[]{}));//(contacts.size())));

        // Full match on first part in 3 names
        Assert.assertEquals("Missing expected contacts", 3, t.findContact("Tripplets").size());

        // Contains match on first part in 3 names
        Assert.assertEquals("Missing expected contacts", 3, t.findContact("Trip").size());

        // Contains in 2 names
        Assert.assertEquals("Missing expected contacts", 2, t.findContact("on").size());

        // Contains in all names
        Assert.assertEquals("Missing expected contacts", 4, t.findContact("le").size());

    }


}