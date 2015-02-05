package org.percepta.mgrankvi.client;

import com.vaadin.shared.ui.AbstractLayoutState;

import java.util.HashMap;
import java.util.Map;

public class SeatingLayoutState extends AbstractLayoutState {

    public String image = "";

    public Map<String, Integer[]> connectorToPosition = new HashMap<String, Integer[]>();
}