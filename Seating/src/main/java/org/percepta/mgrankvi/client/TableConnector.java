package org.percepta.mgrankvi.client;

import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.Table;

/**
 * Connector for the level visualizer component
 *
 * @author Mikael Grankvist - Vaadin }>
 */
@Connect(Table.class)
public class TableConnector extends AbstractComponentConnector {

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

    @OnStateChange("updateHighlight")
    void showPopup() {
        if (getState().highlight != null) {
            getWidget().showContactPopup(getState().highlight);
        }
    }
}
