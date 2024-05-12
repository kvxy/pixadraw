package ui.graphical.listeners;

import ui.graphical.GraphicalApplication;
import ui.graphical.menus.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action listener for switching menus
public class SwitchMenuListener implements ActionListener {
    private MenuType to;
    private GraphicalApplication application;
    private Menu menu;

    // EFFECTS: constructs the switch menu action listener
    public SwitchMenuListener(MenuType to, GraphicalApplication application, Menu menu) {
        this.to = to;
        this.application = application;
        this.menu = menu;
    }

    // MODIFIES: this
    // EFFECTS: switches menus and passes in appropriate data
    @Override
    public void actionPerformed(ActionEvent e) {
        if (menu.getMenuType() == MenuType.LOAD_MENU) {
            application.setFilePath(((LoadMenu) menu).getFilePath());
        } else if (menu.getMenuType() == MenuType.SAVE_MENU) {
            application.setFilePath(((SaveMenu) menu).getFilePath());
        } else if (to == MenuType.EDITOR_MENU) {
            if (menu.getMenuType() == MenuType.CREATE_MENU) {
                application.setSpriteIndex(((CreateMenu) menu).getSpriteIndex());
            } else if (menu.getMenuType() == MenuType.COLLECTION_MENU) {
                application.setSpriteIndex(((CollectionMenu) menu).getSpriteIndex());
            }
        }
        application.switchMenu(to);
    }
}
