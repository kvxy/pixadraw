package ui.graphical;

import model.Collection;
import model.Event;
import model.EventLog;
import ui.graphical.listeners.SwitchMenuListenerBuilder;
import ui.graphical.menus.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// The Pixadraw application
// Handles switching between menus + init frame
public class GraphicalApplication extends JFrame implements WindowListener {
    private Menu currentMenu;
    private Collection collection;
    private String filePath;
    private int spriteIndex;

    // EFFECTS: creates the graphical application
    public GraphicalApplication() {
        super("Pixadraw");
        runPixadraw();
        filePath = "./data/default.json";
        spriteIndex = 0;
    }

    // MODIFIES: this
    // EFFECTS: initiates and runs the pixadraw application
    private void runPixadraw() {
        setSize(1000, 750);
        setVisible(true);
        addWindowListener(this);

        collection = new Collection();
        switchMenu(MenuType.COLLECTION_MENU);
    }

    // MODIFIES: this
    // EFFECTS: switches to another menu
    private void switchMenu(Menu menu) {
        if (currentMenu != null) {
            remove(currentMenu);
        }
        currentMenu = menu;
        add(menu);
        menu.render();
    }

    // MODIFIES: this
    // EFFECTS: switches to another menu given menu type
    public void switchMenu(MenuType type) {
        SwitchMenuListenerBuilder smlBuilder = new SwitchMenuListenerBuilder(this);

        switch (type) {
            case COLLECTION_MENU:
                switchMenu(new CollectionMenu(getWidth(), getHeight(), collection, smlBuilder));
                break;
            case LOAD_MENU:
                switchMenu(new LoadMenu(getWidth(), getHeight(), collection, smlBuilder, filePath));
                break;
            case SAVE_MENU:
                switchMenu(new SaveMenu(getWidth(), getHeight(), collection, smlBuilder, filePath));
                break;
            case CREATE_MENU:
                switchMenu(new CreateMenu(getWidth(), getHeight(), collection, smlBuilder));
                break;
            case EDITOR_MENU:
                switchMenu(new EditorMenu(getWidth(), getHeight(), collection, smlBuilder, spriteIndex));
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the current file path
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // MODIFIES: this
    // EFFECTS: sets the sprite index
    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    // EFFECTS: on window open, does nothing
    @Override
    public void windowOpened(WindowEvent e) {}

    // EFFECTS: on window closing, print event logs to console and exit program
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString() + "\n");
        }
        System.exit(1);
    }

    // EFFECTS: on window closed, does nothing
    @Override
    public void windowClosed(WindowEvent e) {}

    // EFFECTS: on window iconified, does nothing
    @Override
    public void windowIconified(WindowEvent e) {}

    // EFFECTS: on window deiconified, does nothing
    @Override
    public void windowDeiconified(WindowEvent e) {}

    // EFFECTS: on window activated, does nothing
    @Override
    public void windowActivated(WindowEvent e) {}

    // EFFECTS: on window deactivated, does nothing
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
