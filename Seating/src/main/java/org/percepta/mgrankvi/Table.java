package org.percepta.mgrankvi;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.AbstractComponent;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.TableState;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Really simple Level visualisation component
 *
 * @author Mikael Grankvist - Vaadin }>
 */
public class Table extends AbstractComponent {

    private List<Contact> contacts = Lists.newLinkedList();

    public Table(int seats, int maxHeight) {
        getState().seats = seats;
        getState().maxHeight = maxHeight;
    }

    @Override
    protected TableState getState() {
        return (TableState) super.getState();
    }

    /**
     * Rotate table given degrees
     * @param rotation degrees to rotate table
     */
    public void setTableRotation(int rotation) {
        getState().rotateDeg = rotation;
    }

    public void addContact(Contact contact){
        getState().seating.add(contact);

        contacts.add(contact);

        markAsDirty();
    }

    public void addContacts(Contact... contacts) {
        getState().seating.addAll(Arrays.asList(contacts));

        this.contacts.addAll(Arrays.asList(contacts));

        markAsDirty();
    }

    public void setTableSeatPlacing(TableSeatPlacing placing) {
        getState().placing = placing;
    }

    public void setSeatSize(int sizeInPx) {
        getState().seatSize = sizeInPx;
    }

    public void setShowOnHover(boolean onHover) {
        getState().enableOnHover = onHover;
    }
}
