package org.percepta.mgrankvi.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import org.percepta.mgrankvi.client.contact.Contact;
import org.percepta.mgrankvi.client.contact.ContactList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple component to visualize a table with seating and seat information
 *
 * @author Mikael Grankvist - Vaadin }>
 */
public class TableWidget extends SimplePanel {

    private Style style, contactHolderStyle;
    private ContactList contactList;
    private Element content, popup, table;

    private int seatSize = 50;
    private int tableHeight = 75;
    private int tableWidth = 300;
    private TableSeatPlacing seatPlacing;

    private static int LIST_WIDTH = 300;

    private List<Element> seats = new LinkedList<>();
    private List<Contact> seating = new LinkedList<>();

    private boolean hovering = true;

    public TableWidget() {
        content = DOM.createDiv();
        getElement().appendChild(content);
        style = content.getStyle();
        style.setPosition(Style.Position.RELATIVE);
        style.setTop(0, Style.Unit.PX);
        style.setWidth(tableWidth, Style.Unit.PX);
        style.setHeight(tableHeight, Style.Unit.PX);

        table = DOM.createDiv();
        Style st = table.getStyle();
        st.setZIndex(51);
        st.setPosition(Style.Position.ABSOLUTE);
        st.setBorderColor("BLACK");
        st.setBorderWidth(1.0, Style.Unit.PX);
        st.setBorderStyle(Style.BorderStyle.SOLID);
        st.setBackgroundColor("WHITE");
        st.setWidth(100, Style.Unit.PCT);
        st.setHeight(100, Style.Unit.PCT);
        content.appendChild(table);

        contactList = new ContactList();

        Element contactHolder = DOM.createDiv();
        contactHolder.setClassName("contact-list");
        contactHolder.appendChild(contactList.getElement());

        contactHolderStyle = contactHolder.getStyle();

        contactHolderStyle.setPosition(Style.Position.ABSOLUTE);

        contactHolderStyle.setWidth(0, Style.Unit.PX);
        contactHolderStyle.setHeight(0, Style.Unit.PX);
        contactHolderStyle.setTop(0, Style.Unit.PX);
        contactHolderStyle.setLeft(0, Style.Unit.PX);
        contactHolderStyle.setBackgroundColor("WHITE");

        contactHolderStyle.setBorderWidth(1, Style.Unit.PX);
        contactHolderStyle.setBorderColor("BLACK");
        contactHolderStyle.setBorderStyle(Style.BorderStyle.SOLID);
        contactHolderStyle.setVisibility(Style.Visibility.HIDDEN);
        contactHolderStyle.setOverflow(Style.Overflow.HIDDEN);

        addStyleVersions(contactHolderStyle, "transition", "all 1s ease");
        addStyleVersions(contactHolderStyle, "transitionProperty", "top, left, height, width");

        getElement().appendChild(contactHolder);

        Event.sinkEvents(table, Event.ONCLICK);
        Event.setEventListener(table, showListPopupListener);

    }

    EventListener showListPopupListener = new EventListener() {
        @Override
        public void onBrowserEvent(Event event) {
            boolean hide = contactHolderStyle.getVisibility().equals(Style.Visibility.VISIBLE.getCssName());

            hideAllContactLists();

            if (hide) {
                event.stopPropagation();
                Event.releaseCapture(getParent().getElement());
            } else {
                contactHolderStyle.setVisibility(Style.Visibility.VISIBLE);

                contactHolderStyle.setWidth(LIST_WIDTH, Style.Unit.PX);
                contactHolderStyle.setHeight(500, Style.Unit.PX);

                int offsetWidth = content.getOffsetWidth();
                int parentWidth = getParent().getOffsetWidth();
                contactHolderStyle.setLeft((content.getAbsoluteLeft() + offsetWidth + LIST_WIDTH) > parentWidth ? -LIST_WIDTH - 20 : offsetWidth + 20, Style.Unit.PX);

                event.stopPropagation();
                Event.sinkEvents(getParent().getElement(), Event.ONCLICK);
                Event.setEventListener(getParent().getElement(), hideListPopupListener);
            }
        }
    };

    EventListener hideListPopupListener = new EventListener() {
        @Override
        public void onBrowserEvent(Event event) {
            hideAllContactLists();

            event.stopPropagation();
            Event.releaseCapture(getParent().getElement());
        }
    };

    protected void hideAllContactLists() {
        for (Element element : getElementsByClassName("contact-list", getParent().getElement())) {
            Style style = element.getStyle();
            style.setVisibility(Style.Visibility.HIDDEN);
            style.setWidth(0, Style.Unit.PX);
            style.setHeight(0, Style.Unit.PX);
            style.setLeft(0, Style.Unit.PX);
        }
    }

    private Element createChair() {
        Element chair = DOM.createDiv();

        Style s = chair.getStyle();

        s.setWidth(seatSize, Style.Unit.PX);
        s.setHeight(seatSize, Style.Unit.PX);
        s.setPosition(Style.Position.ABSOLUTE);
        s.setZIndex(40);
        s.setBorderColor("BLACK");
        s.setBorderWidth(1.0, Style.Unit.PX);
        s.setBorderStyle(Style.BorderStyle.SOLID);

        return chair;
    }

    /**
     * Set width of content
     *
     * @param width
     */
    public void setWidth(int width) {
//        style.setWidth(width, Style.Unit.PX);
    }

    /**
     * Set height of content
     *
     * @param height
     */
    public void setHeight(int height) {
        style.setHeight(height, Style.Unit.PX);
    }

    /**
     * Set top position.
     *
     * @param top
     */
    public void setTop(int top) {
        style.setTop(top, Style.Unit.PX);
    }

    public void rotate(int rotateDeg) {
        addStyleVersions(style, "transform", "rotate(" + rotateDeg + "deg)");
    }

    private void addStyleVersions(Style style, String baseProperty, String value) {
        style.setProperty(baseProperty, value);

        // Make transition method first character uppercase
        char[] chars = baseProperty.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        baseProperty = new String(chars);

        style.setProperty("Moz" + baseProperty, value);
        style.setProperty("Webkit" + baseProperty, value);
    }

    public void addContacts(List<Contact> seating) {
        contactList.addData(seating);

        createSeatsAndSetTableWidth(seating);
    }

    private void createSeatsAndSetTableWidth(List<Contact> seating) {
        int position = 10;
        for (int i = 0; i < seating.size(); i++) {
            Element chair = createChair();
            seats.add(chair);
            Style s = chair.getStyle();
            s.setLeft(position, Style.Unit.PX);
            s.setBackgroundColor(seating.get(i).colour);
            addClickListenerForChair(seating.get(i), chair);

            switch (seatPlacing) {
                case ALL_DOWN:
                    s.setTop(tableHeight - 20, Style.Unit.PX);
                    position += 10 + seatSize;
                    break;
                case ALL_UP:
                    s.setTop(-30, Style.Unit.PX);
                    position += 10 + seatSize;
                    break;
                case EQUAL:
                default:
                    s.setTop(i % 2 == 0 ? -30 : tableHeight - 20, Style.Unit.PX);
                    // move position after we have added a chair to the bottom.
                    if (i % 2 == 1) {
                        position += 10 + seatSize;
                    }
            }

            content.appendChild(chair);
        }

        style.setWidth(seating.size() % 2 == 0 ? position : position + 10 + seatSize, Style.Unit.PX);
    }

    private void addClickListenerForChair(final Contact contact, Element chair) {
        if (!hovering) {
            return;
        }

        Event.sinkEvents(chair, Event.ONMOUSEOVER | Event.ONMOUSEOUT);
        Event.setEventListener(chair, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONMOUSEOVER) {
                    int x = event.getClientX() - content.getAbsoluteLeft();
                    int y = event.getClientY() - content.getAbsoluteTop() + 5;

                    int offsetWidth = content.getOffsetWidth();
                    int parentWidth = getParent().getOffsetWidth();

                    if ((content.getAbsoluteLeft() + offsetWidth + LIST_WIDTH) > parentWidth) {
                        x -= LIST_WIDTH;
                    } else {
                        int clientWidth = Window.getClientWidth();
                        if (event.getClientX() + LIST_WIDTH > clientWidth) {
                            int offset = clientWidth - event.getClientX();
                            x -= (LIST_WIDTH - offset) + 20;
                        }
                    }
                    if (popup != null) {
                        getElement().removeChild(popup);
                        popup = null;
                    }
                    popup = DOM.createDiv();
                    Style popupStyle = popup.getStyle();
                    popupStyle.setWidth(LIST_WIDTH, Style.Unit.PX);
                    popupStyle.setZIndex(60);
                    popupStyle.setPosition(Style.Position.ABSOLUTE);
                    popupStyle.setBorderColor("BLACK");
                    popupStyle.setBorderWidth(1.0, Style.Unit.PX);
                    popupStyle.setBorderStyle(Style.BorderStyle.SOLID);
                    popupStyle.setBackgroundColor("white");
                    popupStyle.setLeft(x, Style.Unit.PX);
                    popupStyle.setTop(y, Style.Unit.PX);

                    SafeHtmlBuilder builder = new SafeHtmlBuilder();
                    ContactList.getContactRender(contact, builder);
                    popup.setInnerSafeHtml(builder.toSafeHtml());

                    getElement().appendChild(popup);

                } else if (event.getTypeInt() == Event.ONMOUSEOUT) {
                    if (popup != null) {
                        getElement().removeChild(popup);
                        popup = null;
                    }
                }
            }
        });

    }

    public void setSeatPlacing(TableSeatPlacing seatPlacing) {
        this.seatPlacing = seatPlacing;
    }

    public TableSeatPlacing getSeatPlacing() {
        return seatPlacing;
    }

    public void setSeatSize(int seatSize) {
        if (seatSize != this.seatSize) {
            for (Element seat : seats) {
                content.removeChild(seat);
                Event.releaseCapture(seat);
            }
            seats.clear();
            createSeatsAndSetTableWidth(seating);
        }
        this.seatSize = seatSize;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }


    /**
     *
     */

    /**
     * List all elements with the specified class. Try first the implementation of DOM's getElementsByClassName.
     * If not present try to use XPATH. If xpath is not supported searches DOM structure.
     */
    public static List<Element> getElementsByClassName(String className, Element parentElement) {
        JavaScriptObject elements = getElementsByClassNameInternal(className, parentElement);

        int length = getArrayLength(elements);

        Element[] result = new Element[length];
        for (int i = 0; i < length; i++) {
            result[i] = getArrayElement(elements, i);
        }
        return Arrays.asList(result);
    }

    private static native JavaScriptObject getElementsByClassNameInternal(String className, Element parentElement)/*-{
        if ((parentElement && parentElement.getElementsByClassName) || $doc.getElementsByClassName) {
            return (parentElement || $doc).getElementsByClassName(className);
        }
        else if (document.evaluate) {
            var expression = ".//*[contains(concat(' ', @class, ' '), ' " + className + " ')]";
            var results = [];
            var query = $doc.evaluate(expression, parentElement || $doc, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            for (var i = 0, length = query.snapshotLength; i < length; i++)
                results.push(query.snapshotItem(i));
            return results;
        }
        else {
            var children = (parentElement || $doc.body).getElementsByTagName('*');
            var elements = [], child, pattern = new RegExp("(^|\\s)" + className + "(\\s|$)");
            for (var i = 0, length = children.length; i < length; i++) {
                child = children[i];
                var elementClassName = child.className;
                if (elementClassName.length == 0) continue;
                if (elementClassName == className || elementClassName.match(pattern))
                    elements.push(child);
            }
            return elements;
        }
    }-*/;

    private static native int getArrayLength(JavaScriptObject array)/*-{
        return array.length;
    }-*/;

    private static native Element getArrayElement(JavaScriptObject array, int position)/*-{
        return (position >= 0 && position < array.length ? array[position] : null);
    }-*/;
}
