package org.percepta.mgrankvi.client;

import com.vaadin.shared.AbstractComponentState;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.LinkedList;
import java.util.List;

public class TableState extends AbstractComponentState {

    public int seats = 0;

    public String name = "";

    public TableSeatFillDirection fillDirection = TableSeatFillDirection.LAST;

    public int maxHeight = 0;

    public int seatSize = 50;

    public int rotateDeg = 0;

    public boolean enableOnHover = true;

    public List<Contact> seating = new LinkedList<>();

    public TableSeatPlacing placing = TableSeatPlacing.EQUAL;
}