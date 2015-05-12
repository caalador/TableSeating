package org.percepta.mgrankvi.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.Util;
import com.vaadin.client.VCaption;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractHasComponentsConnector;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.shared.ui.Connect;
import org.percepta.mgrankvi.SeatingLayout;

import java.util.List;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(SeatingLayout.class)
public class SeatingLayoutConnector extends AbstractHasComponentsConnector implements SimpleManagedLayout {

    public SeatingLayoutConnector() {
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(SeatingLayoutWidget.class);
    }

    @Override
    public SeatingLayoutWidget getWidget() {
        return (SeatingLayoutWidget) super.getWidget();
    }

    @Override
    public SeatingLayoutState getState() {
        return (SeatingLayoutState) super.getState();
    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (!getWidget().imageLoaded) {
            updateComponentSize();
        } else {
            updateComponentSize(getWidget().imageSize.getWidth() + "px", getWidget().imageSize.getHeight() + "px");
        }
    }

    @OnStateChange("image")
    void imageChange(){
        getWidget().setImage(getState().image);
    }


    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        final List<ComponentConnector> children = getChildComponents();
        final SeatingLayoutWidget widget = getWidget();
        widget.clearChildComponents();
        for (final ComponentConnector connector : children) {
            widget.add(connector.getWidget(), getState().connectorToPosition.get(connector.getConnectorId()));

        }
        for (final ComponentConnector child : connectorHierarchyChangeEvent.getOldChildren()) {
            child.getWidget().removeFromParent();
        }
    }

    @Override
    public void updateCaption(ComponentConnector component) {
        boolean captionIsNeeded = VCaption.isNeeded(component);

        if (captionIsNeeded) {

            VCaption caption = new VCaption(component, getConnection());

            getWidget().setWidgetCaption(component.getWidget(),
                    caption);
        }
    }


    /**
     * Returns the deepest nested child component which contains "element". The
     * child component is also returned if "element" is part of its caption.
     *
     * @param element An element that is a nested sub element of the root element in
     *                this layout
     * @return The Paintable which the element is a part of. Null if the element
     * belongs to the layout and not to a child.
     * @deprecated As of 7.2, call or override
     * {@link #getConnectorForElement(com.google.gwt.dom.client.Element)} instead
     */
    @Deprecated
    protected ComponentConnector getConnectorForElement(
            com.google.gwt.user.client.Element element) {
        return Util.getConnectorForElement(getConnection(), getWidget(),
                element);
    }

    /**
     * Returns the deepest nested child component which contains "element". The
     * child component is also returned if "element" is part of its caption.
     *
     * @param element An element that is a nested sub element of the root element in
     *                this layout
     * @return The Paintable which the element is a part of. Null if the element
     * belongs to the layout and not to a child.
     * @since 7.2
     */
    protected ComponentConnector getConnectorForElement(Element element) {
        return getConnectorForElement(DOM.asOld(element));
    }

    @Override
    public void layout() {

    }
}
