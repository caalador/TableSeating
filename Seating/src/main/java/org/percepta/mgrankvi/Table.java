package org.percepta.mgrankvi;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.AbstractComponent;
import org.percepta.mgrankvi.client.TableClientRpc;
import org.percepta.mgrankvi.client.TableSeatFillDirection;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.TableState;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.Arrays;
import java.util.List;

/**
 * Table with seats visualisation
 *
 * @author Mikael Grankvist - Vaadin }>
 */
public class Table extends AbstractComponent {

    private List<Contact> contacts = Lists.newLinkedList();

    public Table(int seats) {
        getState().seats = seats;
    }

    @Override
    protected TableState getState() {
        return (TableState) super.getState();
    }

    /**
     * Give the table a name for identification
     */
    public void setTableName(String name) {
        if (name == null) {
            name = "";
        }
        getState().name = name;
    }

    /**
     * Rotate table given degrees
     *
     * @param rotation degrees to rotate table
     */
    public void setTableRotation(int rotation) {
        getState().rotateDeg = rotation;
    }

    /**
     * Add a new contact to table
     * Note. Contacts will be seated in the order they are given in.
     *
     * @param contact Contact to add to table
     */
    public void addContact(Contact contact) {
        getState().seating.add(contact);

        contacts.add(contact);

        markAsDirty();
    }

    public void setFillDirection(TableSeatFillDirection direction) {
        getState().fillDirection = direction;
    }

    /**
     * Add new contacts to table
     * Note. Contacts will be seated in the order they are given in.
     *
     * @param contacts Contacts to add to table
     */
    public void addContacts(Contact... contacts) {
        getState().seating.addAll(Arrays.asList(contacts));

        this.contacts.addAll(Arrays.asList(contacts));

        markAsDirty();
    }

    /**
     * Set the seat Placing scheme for table
     *
     * @param placing Way seats should be placed around the table
     */
    public void setTableSeatPlacing(TableSeatPlacing placing) {
        getState().placing = placing;
    }

    /**
     * Set the seat size in px
     *
     * @param sizeInPx Seat size (rectangle)
     */
    public void setSeatSize(int sizeInPx) {
        getState().seatSize = sizeInPx;
    }

    /**
     * Enable/disable the hover feature for chair contacts.
     *
     * @param onHover Chair on hover enabled
     */
    public void setShowOnHover(boolean onHover) {
        getState().enableOnHover = onHover;
    }

    /**
     * Search for contact with name.
     *
     * @param name Name (or part of name) of contact
     * @return All contacts whose name contains given name.
     */
    public List<Contact> findContact(String name) {
        List<Contact> matchingContacts = Lists.newLinkedList();
        for (Contact contact : contacts) {
            if (contact.name.toLowerCase().contains(name.toLowerCase())) {
                matchingContacts.add(contact);
            }
        }
        return matchingContacts;
    }

    /**
     * Get all contacts for table.
     *
     * @return Contact list.
     */
    public List<Contact> getContacts() {
        return Lists.newArrayList(contacts);
    }

    /**
     * Highlight (show popup for) given contact on screen
     *
     * @param contact Contact whose seat to highlight
     */
    public void highlightContact(Contact contact) {
        getRpcProxy(TableClientRpc.class).highlightContact(contact);
    }
}
