package org.percepta.mgrankvi;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import org.percepta.mgrankvi.client.SeatingLayoutState;
import org.percepta.mgrankvi.client.contact.Contact;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SeatingLayout extends AbstractLayout implements HasComponents {

    private LinkedHashMap<Component, Position> componentToCoordinates = new LinkedHashMap<Component, Position>();

    private Map<Contact, Table> searchResults = new HashMap<>();

    public SeatingLayout(String image) {
        getState().image = image;
    }

    public void setImage(String image) {
        getState().image = image;
    }

    @Override
    public SeatingLayoutState getState() {
        return (SeatingLayoutState) super.getState();
    }


    /**
     * Find contact by name from added tables and seats.
     *
     * @param name Full name or part name
     * @return List of contacts with matching/containing Name value
     */
    public List<Contact> findContact(String name) {
        List<Contact> contacts = Lists.newLinkedList();
        for (Component component : componentToCoordinates.keySet()) {
            if (component instanceof Table) {
                Table table = (Table) component;
                List<Contact> tableMatches = table.findContact(name);
                contacts.addAll(tableMatches);

                for (Contact contact : tableMatches) {
                    if (!searchResults.containsKey(contact)) {
                        searchResults.put(contact, table);
                    }
                }
            }
        }
        return contacts;
    }

    /**
     * Highlight contact on screen (Show contact popup)
     * @param contact
     */
    public void highlightContact(Contact contact) {
        if (contact != null) {
            Table table = searchResults.get(contact);
            if(table != null) {
                for(Component component : componentToCoordinates.keySet()) {
                    if(component instanceof Table) {
                        ((Table)component).highlightContact(null);
                    }
                }
                table.highlightContact(contact);

            }
        }
    }

    @Override
    public void addComponent(final Component c) {
        addComponent(c, new Position());
    }

    public void addComponent(Component c, Position position)
            throws IllegalArgumentException {
        /*
         * Create position instance and add it to componentToCoordinates map. We
         * need to do this before we call addComponent so the attachListeners
         * can access this position. #6368
         */
        internalSetPosition(c, position);
        try {
            super.addComponent(c);
        } catch (IllegalArgumentException e) {
            internalRemoveComponent(c);
            throw e;
        }
        markAsDirty();
    }

    /**
     * Gets an iterator for going through all components enclosed in the
     * absolute layout.
     */
    @Override
    public Iterator<Component> iterator() {
        return componentToCoordinates.keySet().iterator();
    }

    /**
     * Gets the number of contained components. Consistent with the iterator
     * returned by {@link #getComponentIterator()}.
     *
     * @return the number of contained components
     */
    @Override
    public int getComponentCount() {
        return componentToCoordinates.size();
    }

    /**
     * Replaces one component with another one. The new component inherits the
     * old components position.
     */
    @Override
    public void replaceComponent(Component oldComponent, Component newComponent) {
        Position position = getPosition(oldComponent);
        removeComponent(oldComponent);
        addComponent(newComponent, position);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        // This could be in internalRemoveComponent and internalSetComponent if
        // Map<Connector,String> was supported. We cannot get the child
        // connectorId unless the component is attached to the application so
        // the String->String map cannot be populated in internal* either.
        Map<String, Integer[]> connectorToPosition = new HashMap<String, Integer[]>();
        for (Iterator<Component> ci = getComponentIterator(); ci.hasNext(); ) {
            Component c = ci.next();
            connectorToPosition.put(c.getConnectorId(), getPosition(c).toArray());
        }
        getState().connectorToPosition = connectorToPosition;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vaadin.ui.AbstractComponentContainer#removeComponent(com.vaadin.ui
     * .Component)
     */
    @Override
    public void removeComponent(Component c) {
        internalRemoveComponent(c);
        super.removeComponent(c);
        markAsDirty();
    }

    /**
     * Gets the position of a component in the layout. Returns null if component
     * is not attached to the layout.
     * <p>
     * Note that you cannot update the position by updating this object. Call
     * {@link #setPosition(com.vaadin.ui.Component, Position)} with the updated
     * {@link Position} object.
     * </p>
     *
     * @param component The component which position is needed
     * @return An instance of ComponentPosition containing the position of the
     * component, or null if the component is not enclosed in the
     * layout.
     */
    public Position getPosition(Component component) {
        return componentToCoordinates.get(component);
    }

    /**
     * Sets the position of a component in the layout.
     *
     * @param component
     * @param position
     */
    public void setPosition(Component component, Position position) {
        if (!componentToCoordinates.containsKey(component)) {
            throw new IllegalArgumentException(
                    "Component must be a child of this layout");
        }
        internalSetPosition(component, position);
    }

    /**
     * Updates the position for a component. Caller must ensure component is a
     * child of this layout.
     *
     * @param component The component. Must be a child for this layout. Not enforced.
     * @param position  New position. Must not be null.
     */
    private void internalSetPosition(Component component,
                                     Position position) {
        componentToCoordinates.put(component, position);
        markAsDirty();
    }

    /**
     * Removes the component from all internal data structures. Does not
     * actually remove the component from the layout (this is assumed to have
     * been done by the caller).
     *
     * @param c The component to remove
     */
    private void internalRemoveComponent(Component c) {
        componentToCoordinates.remove(c);
    }
}
