package org.percepta.mgrankvi.client.contact;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.vaadin.client.ui.ClickEventHandler;
import com.vaadin.shared.MouseEventDetails;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ContactList extends CellList<Contact> {
    private static String defaultImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgAAZABkAAD/7AARRHVja3kAAQAEAAAAZAAA/+ENSWh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8APD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNC4yLjItYzA2MyA1My4zNTI2MjQsIDIwMDgvMDcvMzAtMTg6MDU6NDEgICAgICAgICI+CiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iCiAgICB4bWxuczp4bXBSaWdodHM9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9yaWdodHMvIgogICAgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIgogICAgeG1sbnM6SXB0YzR4bXBDb3JlPSJodHRwOi8vaXB0Yy5vcmcvc3RkL0lwdGM0eG1wQ29yZS8xLjAveG1sbnMvIgogICB4bXBSaWdodHM6TWFya2VkPSJGYWxzZSIKICAgeG1wUmlnaHRzOldlYlN0YXRlbWVudD0iIgogICBwaG90b3Nob3A6QXV0aG9yc1Bvc2l0aW9uPSIiPgogICA8ZGM6cmlnaHRzPgogICAgPHJkZjpBbHQ+CiAgICAgPHJkZjpsaSB4bWw6bGFuZz0ieC1kZWZhdWx0Ii8+CiAgICA8L3JkZjpBbHQ+CiAgIDwvZGM6cmlnaHRzPgogICA8ZGM6Y3JlYXRvcj4KICAgIDxyZGY6U2VxPgogICAgIDxyZGY6bGkvPgogICAgPC9yZGY6U2VxPgogICA8L2RjOmNyZWF0b3I+CiAgIDxkYzp0aXRsZT4KICAgIDxyZGY6QWx0PgogICAgIDxyZGY6bGkgeG1sOmxhbmc9IngtZGVmYXVsdCIvPgogICAgPC9yZGY6QWx0PgogICA8L2RjOnRpdGxlPgogICA8eG1wUmlnaHRzOlVzYWdlVGVybXM+CiAgICA8cmRmOkFsdD4KICAgICA8cmRmOmxpIHhtbDpsYW5nPSJ4LWRlZmF1bHQiLz4KICAgIDwvcmRmOkFsdD4KICAgPC94bXBSaWdodHM6VXNhZ2VUZXJtcz4KICAgPElwdGM0eG1wQ29yZTpDcmVhdG9yQ29udGFjdEluZm8KICAgIElwdGM0eG1wQ29yZTpDaUFkckV4dGFkcj0iIgogICAgSXB0YzR4bXBDb3JlOkNpQWRyQ2l0eT0iIgogICAgSXB0YzR4bXBDb3JlOkNpQWRyUmVnaW9uPSIiCiAgICBJcHRjNHhtcENvcmU6Q2lBZHJQY29kZT0iIgogICAgSXB0YzR4bXBDb3JlOkNpQWRyQ3RyeT0iIgogICAgSXB0YzR4bXBDb3JlOkNpVGVsV29yaz0iIgogICAgSXB0YzR4bXBDb3JlOkNpRW1haWxXb3JrPSIiCiAgICBJcHRjNHhtcENvcmU6Q2lVcmxXb3JrPSIiLz4KICA8L3JkZjpEZXNjcmlwdGlvbj4KIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAKPD94cGFja2V0IGVuZD0idyI/Pv/uAA5BZG9iZQBkwAAAAAH/2wCEAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQECAgICAgICAgICAgMDAwMDAwMDAwMBAQEBAQEBAgEBAgICAQICAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDA//AABEIACAAIAMBEQACEQEDEQH/xABvAAEAAgMBAAAAAAAAAAAAAAAKBgcABQgLAQEBAQAAAAAAAAAAAAAAAAAAAQIQAAAHAAEBBQgDAAAAAAAAAAECAwQFBgcIABESExUJIbbWN3eXGFgUFhcRAQEBAQEBAAAAAAAAAAAAAAABETFRIf/aAAwDAQACEQMRAD8AUtjeN5DKZDlUnJ5Vm8jJSOb0Z9ISD6jVh29fPXdYi3Dt48duItRdy6crqGOoocxjnOYRERERHrMkyfATv1duZNqieUV5wPEo+q5HQcnWYV6RVotLq1dstltRo5pIzEhI2ZjEhNJMmazz+O2btl0ERIQTqFOcwdy5PBr/AEmua1pW5PZ7hO3xtW1/PNdlf6iyVvVMq1is1XtT9FU0DKRtnfxJptZk6fpkbOWzlddEE1O+mUhyiJmTwLU2TG8hi8h1WTjMqzeOko7N7y+j5BjRqw0esXrSsSjho8Zu28Wmu2dNl0ynTUIYpyHKAgICAD1LJl+CyMN+SmPfS3PvdKI6s5ADj1k8d0PDud2uyd+UCSjdimHWpUWwpFUIhK1iYXM1SYmDwEk039ccMzMVyE7xQ8IpgEQOHVEd9JDGNG3/AJx44TOlvK0ssscXrFys6qayravVipyTRdYwgVBQh38y8USYtSH7pTLOAExgKUR6B9O5fJTYfpboPulL9S8opVjyPwPjzx5yWxbjsOd5ZEoZNnrgy91tUTCLKomqsSQp2jB05LIPgMcBAARSUER9nScgFx68Pqoce+a16yehcdyrWWq4sa3rSWsOmC8SlbJa0+St1Ieus3yDeUVrsMSC8QrpYEyuVnBhSJ4ZQUUojPoceqzg3BHQtVrfIGGkWlM2hvU009RgozzyRo7+pefmQZSsU37ZZxWJ3zztXOzBZVFw3RMKJyd46YNWJys438m+POuz+BbXnWqR58q0Q6idSs0c/lGgJVKU8UJCDFZOajjIicCmBdun3TdoD7QEOpeUeZTzVzTm3qfKfdrI9w/kzoUMhqFzgaRYE8q0qwQx8/rk69gaK1rMg3gHMerWmlUjWaTEzYwoHbEIYomAe8MlmT6OW/xp5i/qtyV+xum/DHV2ejPxp5i/qtyV+xum/DHTZ6OkuH2U81c15PYVZmuEcmaHGf6lRoi3WE2T6XARiFFnLLGxN2b2GSWr7ZmjWnVYeOk34uDggDYxxOIAHaEtmdH/2Q==";

    List<ContactHandler> handlers = new LinkedList<>();

    private static class ContactCell extends AbstractCell<Contact> {

        public ContactCell() {
//            super(BrowserEvents.CLICK, BrowserEvents.MOUSEOVER, BrowserEvents.MOUSEOUT);
        }


//        @Override
//        public void onBrowserEvent(Context context, Element parent, Contact value, NativeEvent event, ValueUpdater<Contact> valueUpdater) {
//            super.onBrowserEvent(context, parent, value, event, valueUpdater);
//
//            Window.alert(event.getType());
//            if (BrowserEvents.MOUSEOVER.equals(event.getType())) {
////                for (ContactHandler handler : handlers) {
////                    handler.contactOver(value.id);
////                }
//                Window.alert("Over item: " + value.id);
//            } else if (BrowserEvents.MOUSEOUT.equals(event.getType())) {
////                for (ContactHandler handler : handlers) {
////                    handler.contactOut();
////                }
//                Window.alert("Left item.");
//            }
//        }

        @Override
        public void render(Context context, Contact contact, SafeHtmlBuilder safeHtmlBuilder) {
            if (contact != null) {
                getContactRender(contact, safeHtmlBuilder);
            }
        }
    }

    public static void getContactRender(Contact contact, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendHtmlConstant("<table width=\"100%\"><tr>");

        safeHtmlBuilder.appendHtmlConstant("<td width=\"32px\" style=\"background-color: "+ contact.colour + ";\">");

        if (contact.image == null) {
            safeHtmlBuilder.appendHtmlConstant("<img src=\"" + defaultImage + "\" />");
        } else {
            safeHtmlBuilder.appendHtmlConstant("<img src=\"" + contact.image + "\" />");
        }

        safeHtmlBuilder.appendHtmlConstant("</td>");

        safeHtmlBuilder.appendHtmlConstant("<td width=\"100%\">");

        safeHtmlBuilder.appendHtmlConstant("<div style=\"font-weight: bold;\">" + contact.name + "</div>");
        safeHtmlBuilder.appendHtmlConstant("<div>" + contact.quote + "</div>");
        safeHtmlBuilder.appendHtmlConstant("<div style=\"float: right;\">" + contact.quotedPerson + "</div>");

        safeHtmlBuilder.appendHtmlConstant("</td>");

        safeHtmlBuilder.appendHtmlConstant("</tr></table>");
    }

    private static ProvidesKey<Contact> keyProvider = new ProvidesKey<Contact>() {
        public Object getKey(Contact item) {
            // Always do a null check.
            return (item == null) ? null : item.id;
        }
    };

    public ContactList() {
        super(new ContactCell(), keyProvider);


        SelectionModel<Contact> selectionModel = new SingleSelectionModel<>(keyProvider);
        setSelectionModel(selectionModel);

        setRowCount(10);
        setSize("100%", "100%");

        Event.sinkEvents(getElement(), Event.ONCLICK);
        Event.setEventListener(getElement(), new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                Window.setTitle("Event " + event.getString());
            }
        });

//        addDomHandler(new ClickHandler(){
//            @Override
//            public void onClick(ClickEvent event) {
//                Window.alert("Click");
//            }
//        }, ClickEvent.getType());
//        addDomHandler(new MouseOverHandler() {
//            @Override
//            public void onMouseOver(MouseOverEvent event) {
//
//            }
//        }, MouseOverEvent.getType());
//
//        addDomHandler(new MouseOutHandler() {
//            @Override
//            public void onMouseOut(MouseOutEvent event) {
//
//            }
//        }, MouseOutEvent.getType());
    }

    /**
     * Clear list and input new data.
     *
     * @param contacts
     */
    public void addData(List<Contact> contacts) {
        setRowData(contacts);
    }

    public void addContactHandler(ContactHandler handler) {
        handlers.add(handler);
    }

    public void removeContactHandler(ContactHandler handler) {
        handlers.remove(handler);
    }
}
