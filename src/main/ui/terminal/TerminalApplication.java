package ui.terminal;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import model.Collection;
import model.Sprite;
import ui.terminal.menus.*;

import java.awt.Font;
import java.io.IOException;

// The Pixadraw application
public class TerminalApplication {
    private Screen screen;
    private Collection collection;
    private Menu currentMenu;
    private String fileName;
    private boolean running;

    // EFFECTS: constructs the application
    public TerminalApplication() throws IOException {
        runPixadraw();
    }

    // MODIFIES: this
    // EFFECTS: starts the application
    private void runPixadraw() throws IOException {
        collection = new Collection();
        fileName = "";

        screen = createTerminal();
        screen.startScreen();

        currentMenu = new CollectionMenu(collection, screen, fileName);

        running = true;
        while (running) {
            draw();
            handleInput();
        }

        System.exit(0);
    }

    // EFFECTS: creates terminal
    private Screen createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 16);
        SwingTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(font);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);

        terminalFactory.setTerminalEmulatorTitle("Pixadraw");
        terminalFactory.setInitialTerminalSize(new TerminalSize(96, 35));

        return terminalFactory.createScreen();
    }

    // MODIFIES: this
    // EFFECTS: draws current menu onto screen
    private void draw() throws IOException {
        screen.clear();
        currentMenu.draw();
        screen.refresh();
    }

    // MODIFIES: this
    // EFFECTS: handles user input
    private void handleInput() throws IOException {
        KeyStroke stroke = screen.readInput();

        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null && !Character.isISOControl(stroke.getCharacter())) {
            char keyChar = stroke.getCharacter();
            currentMenu.handleInput(keyChar);

            MenuType menuType = currentMenu.switchMenu(keyChar);
            handleMenuSwitch(menuType);
        } else if (stroke.getKeyType() != null) {
            KeyType keyType = stroke.getKeyType();
            currentMenu.handleInput(keyType);

            MenuType menuType = currentMenu.switchMenu(keyType);
            handleMenuSwitch(menuType);

            if (keyType == KeyType.Delete) {
                running = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: switches to collection menu
    private void switchToCollectionMenu() {
        if (currentMenu.getMenuType() == MenuType.LOAD_FILE_MENU) {
            fileName = ((LoadFileMenu)currentMenu).getFileName();
        } else if (currentMenu.getMenuType() == MenuType.SAVE_FILE_MENU) {
            fileName = ((SaveFileMenu)currentMenu).getFileName();
        }
        currentMenu = new CollectionMenu(collection, screen, fileName);
    }

    // MODIFIES: this
    // EFFECTS: switches to editor menu
    private void switchToEditorMenu() {
        Sprite editedSprite;
        if (currentMenu.getMenuType() == MenuType.COLLECTION_MENU) {
            String i = currentMenu.getClass().getSimpleName();
            editedSprite = collection.getSprite(((CollectionMenu) currentMenu).getSelected());
        } else { // CREATE_MENU
            editedSprite = collection.getSprite(collection.size() - 1);
        }
        currentMenu = new EditorMenu(collection, screen, editedSprite);
    }

    // MODIFIES: this
    // EFFECTS: switches the current menu
    private void handleMenuSwitch(MenuType menuType) {
        switch (menuType) {
            case COLLECTION_MENU:
                switchToCollectionMenu();
                break;
            case CREATE_MENU:
                currentMenu = new CreateMenu(collection, screen);
                break;
            case EDITOR_MENU:
                switchToEditorMenu();
                break;
            case LOAD_FILE_MENU:
                currentMenu = new LoadFileMenu(collection, screen);
                break;
            case SAVE_FILE_MENU:
                currentMenu = new SaveFileMenu(collection, screen, fileName);
                break;
        }
    }
}
