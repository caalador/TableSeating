package org.percepta.mgrankvi.client;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.vaadin.client.Util;
import com.vaadin.client.VCaption;

import org.percepta.mgrankvi.preloader.client.image.ImageLoadEvent;
import org.percepta.mgrankvi.preloader.client.image.ImageLoadHandler;
import org.percepta.mgrankvi.preloader.client.image.ImagePreloader;
import org.percepta.mgrankvi.preloader.client.image.Size;

import java.util.HashMap;
import java.util.Map;

// Extend any GWT Widget
public class SeatingLayoutWidget extends Composite implements ImageLoadHandler {

    private final static String CLASSNAME = "c-visualisator";

    private ImagePreloader preloader = new ImagePreloader();

    public boolean imageLoaded = false;
    public Size imageSize;

    private Image image;

    private Map<Widget, Integer[]> children = new HashMap<Widget, Integer[]>();
    private Map<Widget, VCaption> hasCaption = new HashMap<Widget, VCaption>();
    private Map<Widget, HandlerRegistration> attachHandlers = new HashMap<Widget, HandlerRegistration>();

    private final AbsolutePanel panel = new AbsolutePanel();

    public SeatingLayoutWidget() {
        SimplePanel baseContent = new SimplePanel();
        baseContent.add(panel);

        initWidget(baseContent);

        setStyleName(CLASSNAME);

        preloader.addImageLoadListener(this);
    }


    /**
     * Add new Widget in coordinate position.
     *
     * @param widget
     * @param integers
     */
    public void add(Widget widget, Integer[] integers) {
        widget.getElement().getStyle().setZIndex(1);

        children.put(widget, integers);
        panel.add(widget, integers[0], integers[1]);
    }

    /**
     * Remove all child components
     */
    public void clearChildComponents() {
        for (Widget child : children.keySet()) {
            panel.remove(child);
        }
        for (VCaption caption : hasCaption.values()) {
            panel.remove(caption);
        }
        hasCaption.clear();
        children.clear();
    }

    @Override
    public void imageLoaded(ImageLoadEvent event) {
        if (!event.isSuccess()) {
            Window.alert("Failed to load image: " + event.getFile());
            return;
        }
        imageSize = event.getSize();
        imageLoaded = true;
        if (image != null) {
            panel.remove(image);
        }
        panel.setWidth(event.getSize().getWidth() + "px");
        panel.setHeight(event.getSize().getHeight() + "px");
        image = new Image();
        image.setUrl(event.getFile());
        panel.add(image, 0, 0);
    }

    /**
     * Set new image to be loaded and displayed.
     *
     * @param image
     */
    public void setImage(String image) {
        imageLoaded = false;
        preloader.preloadImage(Util.getAbsoluteUrl("./VAADIN/" + image));
    }

    /**
     * Set and position caption for child component
     *
     * @param widget
     * @param caption
     */
    public void setWidgetCaption(final Widget widget, final VCaption caption) {
        if (!hasCaption.containsKey(widget)) {
            final Integer[] position = children.get(widget);

            caption.getElement().getStyle().setZIndex(1);
            caption.updateCaption();

            HandlerRegistration handlerRegistration = caption.addAttachHandler(new AttachEvent.Handler() {
                @Override

                public void onAttachOrDetach(AttachEvent event) {
                    if (event.isAttached()) {
                        panel.setWidgetPosition(caption, position[0], widget.getAbsoluteTop() - caption.getOffsetHeight() - 5);
                        attachHandlers.remove(caption).removeHandler();
                    }
                }
            });
            attachHandlers.put(caption, handlerRegistration);

            panel.add(caption);
            hasCaption.put(widget, caption);
        }
    }
}