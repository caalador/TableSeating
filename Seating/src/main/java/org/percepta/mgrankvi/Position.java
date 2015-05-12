package org.percepta.mgrankvi;

import java.io.Serializable;

/**
 * Position of component containing top and left position in relation to parent.
 *
 * @author Mikael Grankvist - Vaadin }>
 */
public class Position implements Serializable {

    private Integer topValue = 0;
    private Integer leftValue = 0;

    public Position() {
    }

    public Position(Integer leftValue, Integer topValue) {
        this.topValue = topValue;
        this.leftValue = leftValue;
    }

    public Integer getTopValue() {
        return topValue;
    }

    public void setTopValue(Integer topValue) {
        this.topValue = topValue;
    }

    public Integer getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(Integer leftValue) {
        this.leftValue = leftValue;
    }

    public Integer[] toArray() {
        return new Integer[]{leftValue, topValue};
    }
}
