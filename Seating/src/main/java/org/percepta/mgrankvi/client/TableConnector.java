package org.percepta.mgrankvi.client;

import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.Table;
import org.percepta.mgrankvi.client.contact.Contact;

/**
 * Connector for the level visualizer component
 *
 * @author Mikael Grankvist - Vaadin }>
 */
@Connect(Table.class)
public class TableConnector extends AbstractComponentConnector {

    public TableConnector() {
        registerRpc(TableClientRpc.class, new TableClientRpc() {
            @Override
            public void highlightContact(Contact contact) {
                if(contact != null ){
                    getWidget().showContactPopup(contact);
                } else {
                    getWidget().hidePopup();
                }
            }
        });
    }

    // We must implement getWidget() to cast to correct type
    @Override
    public TableWidget getWidget() {
        return (TableWidget) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public TableState getState() {
        return (TableState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        getWidget().setSeatPlacing(getState().placing);
        getWidget().addContacts(getState().seating);
        getWidget().rotate(getState().rotateDeg);
    }

    @OnStateChange("seats")
    void setSeats() {
        getWidget().setSeatAmount(getState().seats);
    }

    @OnStateChange("seatSize")
    void setSeatSize() {
        getWidget().setSeatSize(getState().seatSize);
    }

    @OnStateChange("enableOnHover")
    void setOnHover() {
        getWidget().setHovering(getState().enableOnHover);
    }

    @OnStateChange("fillDirection")
    void setFillDirection() {
        getWidget().setFillDirection(getState().fillDirection);
    }

    @OnStateChange("name")
    void setName() {
        getWidget().setTableName(getState().name);
    }
}
