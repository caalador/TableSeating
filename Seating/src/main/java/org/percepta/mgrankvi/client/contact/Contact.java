package org.percepta.mgrankvi.client.contact;

import org.percepta.mgrankvi.Colours;

import java.io.Serializable;

/**
 * Contact bean
 */
public class Contact implements Serializable {

    private static int nextId = 0;

    protected int id;

    public String name = "";
    public String image = null;
    public String quote = "";
    public String quotedPerson = "";

    public String colour = "";

    public Contact() { this("", null, "white", "", ""); }

    public Contact(String name) {
        this(name, null, "white", "", "");
    }

    public Contact(String name, String image, String colour, String quote, String quotedPerson) {
        nextId++;
        this.id = nextId;
        this.colour = colour;
        this.name = name;
        this.image = image;
        this.quote = quote;
        this.quotedPerson = quotedPerson;
    }
}
