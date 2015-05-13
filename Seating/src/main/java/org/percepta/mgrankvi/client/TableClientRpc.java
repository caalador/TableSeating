package org.percepta.mgrankvi.client;

import com.vaadin.shared.communication.ClientRpc;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.List;

/**
 *
 */
public interface TableClientRpc extends ClientRpc {

    void highlightContact(Contact contact);

    void highlightContacts(List<Contact> contact);

}
