package ui.graphical.listeners;

import ui.graphical.GraphicalApplication;
import ui.graphical.menus.*;
import java.awt.event.ActionListener;

// Creates action listeners
public class SwitchMenuListenerBuilder {
    private GraphicalApplication application;

    // EFFECTS: creates the switch menu action listener creator
    public SwitchMenuListenerBuilder(GraphicalApplication application) {
        this.application = application;
    }

    // EFFECTS: creates an action listener for switching menu
    public ActionListener createActionListener(MenuType toType, Menu currentMenu) {
        return new SwitchMenuListener(toType, application, currentMenu);
    }
}
