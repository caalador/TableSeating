package org.percepta.mgrankvi.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.VConsole;
import org.percepta.mgrankvi.client.contact.Contact;
import org.percepta.mgrankvi.client.contact.ContactHandler;
import org.percepta.mgrankvi.client.contact.ContactList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Simple component to visualize a table with seating and seat information
 *
 * @author Mikael Grankvist - Vaadin }>
 */
public class TableWidget extends SimplePanel {

    private Style style, contactHolderStyle;
    private ContactList contactList;
    private Element content, popup;

    private int seatSize = 50;
    private int tableHeight = 100;
    private int tableWidth = 100;
    private TableSeatPlacing seatPlacing;

    private static int LIST_WIDTH = 300;

    private List<Element> seats = new LinkedList<>();
    private List<Contact> seating = new LinkedList<>();

    Map<Contact, Element> seatingMap = new HashMap<>();
    Map<String, Contact> idContact = new HashMap<>();

    private boolean hovering = true;
    private int seatAmount = 0;
    private int rotation = 0;

    private Element contactHolder;

    private SeatFillDirection fillDirection;

    public TableWidget() {
        content = DOM.createDiv();
        getElement().appendChild(content);
        style = content.getStyle();
        style.setPosition(Style.Position.RELATIVE);
        style.setTop(0, Style.Unit.PX);
        style.setWidth(tableWidth, Style.Unit.PX);
        style.setHeight(tableHeight, Style.Unit.PX);

        Element table = DOM.createDiv();
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
        contactList.addContactHandler(contactHandler);

        contactHolder = DOM.createDiv();
        contactHolder.setClassName("contact-list");
        contactHolder.appendChild(contactList.getElement());

        contactHolderStyle = contactHolder.getStyle();

        contactHolderStyle.setPosition(Style.Position.ABSOLUTE);
        contactHolderStyle.setZIndex(59);

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

        Event.sinkEvents(table, Event.ONCLICK);
        Event.setEventListener(table, showListPopupListener);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        getParent().getElement().appendChild(contactHolder);
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
                contactHolderStyle.setHeight(390, Style.Unit.PX);

                int offsetWidth = content.getOffsetWidth();
                int parentWidth = getParent().getOffsetWidth();

                Style elementStyle = getElement().getStyle();
                int elementLeft = parseInt(elementStyle.getLeft());
                int elementTop = parseInt(elementStyle.getTop());
                VConsole.log((content.getAbsoluteLeft() + offsetWidth + LIST_WIDTH + 20) + " : " + Window.getClientWidth());
                VConsole.log("Content absolute: " + content.getAbsoluteLeft());
                if (content.getAbsoluteLeft() + offsetWidth + LIST_WIDTH < parentWidth && content.getAbsoluteLeft() + offsetWidth + LIST_WIDTH + 20 < Window.getClientWidth()) {
                    contactHolderStyle.setLeft(elementLeft + offsetWidth + 20, Style.Unit.PX);
                } else {
                    contactHolderStyle.setLeft(elementLeft - LIST_WIDTH - 20, Style.Unit.PX);
                }
                contactHolderStyle.setTop(elementTop, Style.Unit.PX);

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

    public void rotate(int rotateDeg) {
        rotation = rotateDeg;
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
        if(seatAmount < seating.size()) {
            seatAmount = seating.size();
        }
        contactList.addData(seating);

        createSeatsAndSetTableWidth(seating);
    }

    private void createSeatsAndSetTableWidth(List<Contact> seating) {
        int position = 10;
        int emptySeats = seatAmount - seating.size();
        int lastSeat = 0;

        int realSeats = seating.size();
        if (fillDirection.equals(SeatFillDirection.FIRST) && emptySeats > 0) {
            for (; lastSeat < emptySeats; lastSeat++) {
                position = createEmptySeat(position, lastSeat);
            }
            realSeats += emptySeats;
        }

        for (int i = 0; lastSeat < realSeats; lastSeat++) {
            Element chair = createChair();
            Contact contact = seating.get(i++);
            seatingMap.put(contact, chair);
            idContact.put(contact.id, contact);
            seats.add(chair);
            Style s = chair.getStyle();
            s.setLeft(position, Style.Unit.PX);
            s.setBackgroundColor(contact.colour);
            addListenerForChair(contact, chair);

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
                    s.setTop(lastSeat % 2 == 0 ? -30 : tableHeight - 20, Style.Unit.PX);
                    // move position after we have added a chair to the bottom.
                    if (lastSeat % 2 == 1) {
                        position += 10 + seatSize;
                    }
            }

            content.appendChild(chair);
        }

        if (fillDirection.equals(SeatFillDirection.LAST) && emptySeats > 0) {
            for (; lastSeat < seatAmount; lastSeat++) {
                position = createEmptySeat(position, lastSeat);
            }
        }

        style.setWidth(seatAmount % 2 == 0 ? position : position + 10 + seatSize, Style.Unit.PX);
    }

    private int createEmptySeat(int position, int i) {
        Element chair = createChair();
        seats.add(chair);
        Style s = chair.getStyle();
        s.setLeft(position, Style.Unit.PX);
        s.setBackgroundColor("WHITE");

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
        return position;
    }

    private int parseInt(String string) {
        return Integer.parseInt(string.substring(0, string.indexOf("px")));
    }

    private void addListenerForChair(final Contact contact, final Element chair) {
        if (!hovering) {
            return;
        }

        Event.sinkEvents(chair, Event.ONMOUSEOVER | Event.ONMOUSEOUT);
        Event.setEventListener(chair, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONMOUSEOVER) {
                    showChairPopup(chair, contact);
                } else if (event.getTypeInt() == Event.ONMOUSEOUT) {
                    if (popup != null) {
                        getParent().getElement().removeChild(popup);
                        popup = null;
                    }
                }
            }
        });

    }

    protected void showChairPopup(Element chair, Contact contact) {
        Style elementStyle = getElement().getStyle();

        int x = parseInt(elementStyle.getLeft()) + chair.getOffsetLeft();

        int elementTop = parseInt(elementStyle.getTop());
        int y = elementTop + seatSize + chair.getOffsetTop();
        if (rotation != 0) {
            int elementDiff = elementTop - getElement().getAbsoluteTop();
            y = elementTop + seatSize - (elementTop - (chair.getAbsoluteTop() + elementDiff));
            y += Math.cos(rotation) * seatSize;
        }

        if (x + LIST_WIDTH > getParent().getOffsetWidth()) {
            int offset = x + LIST_WIDTH - getParent().getOffsetWidth();
            x -= (offset + 20);
        }

        if (popup != null) {
            getParent().getElement().removeChild(popup);
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

        popup.setInnerSafeHtml(contact.getContactRender().toSafeHtml());

        getParent().getElement().appendChild(popup);
    }

    public void setSeatPlacing(TableSeatPlacing seatPlacing) {
        this.seatPlacing = seatPlacing;
    }

    public TableSeatPlacing getSeatPlacing() {
        return seatPlacing;
    }

    public void setSeatSize(int seatSize) {
        if (seatSize != this.seatSize) {
            this.seatSize = seatSize;

            tableHeight = seatSize * 2;
            style.setHeight(tableHeight, Style.Unit.PX);

            for (Element seat : seats) {
                content.removeChild(seat);
                Event.releaseCapture(seat);
            }
            seatingMap.clear();
            seats.clear();
            createSeatsAndSetTableWidth(seating);
        }
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;

        if(seatAmount < seating.size()) {
            this.seatAmount = seating.size();
        }
    }

    public void showContactPopup(Contact highlight) {
        VConsole.log("Highlight " + highlight);
        if (highlight != null) {
            Element chair = seatingMap.get(highlight);
            VConsole.log("Found chair for highlight: " + (chair != null));
            if (chair != null) {
                showChairPopup(chair, highlight);
                popup.scrollIntoView();
            }
        }
    }

    public void hidePopup() {
        if (popup != null) {
            getParent().getElement().removeChild(popup);
            popup = null;
        }
    }

    public void setFillDirection(SeatFillDirection fillDirection) {
        this.fillDirection = fillDirection;
    }

    /**
     * Native JS methods for traversing dom tree.
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
        } else if (document.evaluate) {
            var expression = ".//*[contains(concat(' ', @class, ' '), ' " + className + " ')]";
            var results = [];
            var query = $doc.evaluate(expression, parentElement || $doc, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            for (var i = 0, length = query.snapshotLength; i < length; i++)
                results.push(query.snapshotItem(i));
            return results;
        } else {
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

    private static native int getPageXOffset()
    /*-{
        if ($wnd.pageXOffset) {
            return $wnd.pageXOffset;
        }
        if ($wnd.scrollLeft) {
            return $wnd.scrollLeft;
        }
        return -1;
    }-*/;

    private static native int getPageYOffset()
    /*-{
        if ($wnd.pageYOffset) {
            return $wnd.pageYOffset;
        }
        if ($wnd.scrollTop) {
            return $wnd.scrollTop;
        }
        return -1;
    }-*/;

    public static int getScrollLeft() {
        int left = getPageXOffset();
        if (left == -1) {
            left = Window.getScrollLeft();
        }
        return left;
    }

    public static int getScrollTop() {
        int top = getPageYOffset();
        if (top == -1) {
            top = Window.getScrollTop();
        }
        return top;
    }

    /**
     * HANDLERS
     */


    private ContactHandler contactHandler = new ContactHandler() {
        @Override
        public void contactOver(String id) {
            seatingMap.get(idContact.get(id)).getStyle().setZIndex(55);
        }

        @Override
        public void contactOut(String id) {
            seatingMap.get(idContact.get(id)).getStyle().setZIndex(40);
        }
    };
}
