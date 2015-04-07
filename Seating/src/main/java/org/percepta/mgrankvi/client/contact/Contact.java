package org.percepta.mgrankvi.client.contact;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.io.Serializable;

/**
 * Contact bean
 */
public class Contact implements Serializable {

    public String id;

    public String name = "";
    public String image = null;
    public String quote = "";
    public String quotedPerson = "";

    public String colour = "";

    public Contact() {
    }

    public Contact(String id, String name) {
        this(id, name, null);
    }

    public Contact(String id, String name, String image) {
        this(id, name, image, Integer.toHexString((int) (Math.random() * 0x1000000)), null, null);
    }

    public Contact(String id, String name, String image, String colour, String quote, String quotedPerson) {
        this.id = id;
        this.colour = colour;
        this.name = name;
        this.image = image;
        this.quote = quote;
        this.quotedPerson = quotedPerson;
    }

    private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String uuid() {
        char[] uuid = new char[36];
        int r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (int i = 0; i < 36; i++) {
            if (uuid[i] == 0) {
                r = (int) (Math.random() * 16);
                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }
        return new String(uuid);
    }

    @Override
    public int hashCode() {
        return (id + name).hashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Contact) {
            Contact other = (Contact) obj;
            return id.equals(other.id) && name.equals(other.name);
        }
        return false;
    }

    public SafeHtmlBuilder getContactRender() {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();

        safeHtmlBuilder.appendHtmlConstant("<table width=\"100%\" height=\"65px\"><tr>");

        safeHtmlBuilder.appendHtmlConstant("<td width=\"32px\" style=\"background-color: " + colour + ";\">");

        if (image == null) {
            safeHtmlBuilder.appendHtmlConstant("<img src=\"" + ContactList.defaultImage + "\" />");
        } else {
            safeHtmlBuilder.appendHtmlConstant("<img height=\"65px\" src=\"" + image + "\" />");
        }

        safeHtmlBuilder.appendHtmlConstant("</td>");

        safeHtmlBuilder.appendHtmlConstant("<td width=\"100%\">");

        safeHtmlBuilder.appendHtmlConstant("<div style=\"font-weight: bold;\">" + name + "</div>");
        safeHtmlBuilder.appendHtmlConstant("<div>" + quote + "</div>");
        safeHtmlBuilder.appendHtmlConstant("<div style=\"float: right;\">" + quotedPerson + "</div>");

        safeHtmlBuilder.appendHtmlConstant("</td>");

        safeHtmlBuilder.appendHtmlConstant("</tr></table>");

        return safeHtmlBuilder;
    }
}
