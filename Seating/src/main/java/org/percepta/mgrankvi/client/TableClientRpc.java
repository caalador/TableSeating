package org.percepta.mgrankvi.client;

import com.vaadin.shared.communication.ClientRpc;
import org.percepta.mgrankvi.client.contact.Contact;

/**
 *
 */
public interface TableClientRpc extends ClientRpc {

    void highlightContact(Contact contact);

}
