package ui.graphical.menus;

import model.Collection;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import java.awt.*;

// Abstract class for menu, used for GUI
public abstract class Menu extends JPanel {
    protected Collection collection;
    protected SwitchMenuListenerBuilder smlBuilder;

    // EFFECTS: initiates the menu
    public Menu(int width, int height, Collection collection, SwitchMenuListenerBuilder smlBuilder) {
        super();
        setBounds(0, 0, width, height);

        this.collection = collection;
        this.smlBuilder = smlBuilder;
    }

    // MODIFIES: component
    // EFFECTS: sets the dimensions of a component
    protected void setDimensions(Component component, int width, int height) {
        Dimension dimensions = new Dimension(width, height);
        component.setPreferredSize(dimensions);
        component.setMaximumSize(dimensions);
    }

    // MODIFIES: this
    // EFFECTS: draws/redraws the menu
    public abstract void draw();

    // MODIFIES: this
    // EFFECTS: renders the menu
    public void render() {
        removeAll();
        draw();
        revalidate();
    }

    // EFFECTS: gets the menu type
    public abstract MenuType getMenuType();
}
