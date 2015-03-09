package org.percepta.mgrankvi;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.AbstractComponent;
import org.percepta.mgrankvi.client.SeatFillDirection;
import org.percepta.mgrankvi.client.TableClientRpc;
import org.percepta.mgrankvi.client.TableSeatPlacing;
import org.percepta.mgrankvi.client.TableState;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.Arrays;
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
     *
     * @param rotation degrees to rotate table
     */
    public void setTableRotation(int rotation) {
        getState().rotateDeg = rotation;
    }

    /**
     * Add a new contact to table
     * @param contact
     */
    public void addContact(Contact contact) {
        getState().seating.add(contact);

        contacts.add(contact);

        markAsDirty();
    }

    public void setFillDirection(SeatFillDirection direction) {
        getState().fillDirection = direction;
    }

    /**
     * Add new contacts to table
     * @param contacts
     */
    public void addContacts(Contact... contacts) {
        getState().seating.addAll(Arrays.asList(contacts));

        this.contacts.addAll(Arrays.asList(contacts));

        markAsDirty();
    }

    /**
     * Set the seat Placing scheme for table
     * @param placing
     */
    public void setTableSeatPlacing(TableSeatPlacing placing) {
        getState().placing = placing;
    }

    /**
     * Set the seat size in px
     * @param sizeInPx
     */
    public void setSeatSize(int sizeInPx) {
        getState().seatSize = sizeInPx;
    }

    /**
     * Enable/disable the hover feature for chair contacts.
     * @param onHover
     */
    public void setShowOnHover(boolean onHover) {
        getState().enableOnHover = onHover;
    }

    /**
     * Search for contact with name.
     *
     * @param name
     * @return
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
     * Highlight (show popup for) given contact on screen
     * @param contact
     */
    public void highlightContact(Contact contact) {
        getRpcProxy(TableClientRpc.class).highlightContact(contact);
    }
}
