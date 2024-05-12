package ui.terminal.menus;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;

// Menu for creating/editing/deleting sprites in your collection
public class CollectionMenu extends Menu {
    private int selected;
    private String fileName;

    // EFFECTS: constructs the collection menu
    public CollectionMenu(Collection collection, Screen screen, String fileName) {
        super(collection, screen);
        this.fileName = fileName.equals("") ? "Unnamed" : fileName;
        selected = 0;
    }

    // EFFECTS: draws the collection menu
    @Override
    public void draw() {
        TextGraphics text = screen.newTextGraphics();

        drawBackground(text);
        drawHeader(text, "My Collection - " + fileName);
        drawFooter(text, "[Arrow Down] Next Sprite | [Arrow Up] Previous Sprite | [e] Edit Sprite",
                "[c] Create Sprite | [d] Delete Sprite | [l] Load | [s] Save | [Del] Exit Program");

        drawSprites(text);
    }

    // EFFECTS: draws the sprites list
    // MODIFIES: text
    private void drawSprites(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));

        if (collection.size() == 0) {
            text.setForegroundColor(new TextColor.RGB(50, 50, 50));
            text.putString(2, 2, "No sprites created. Press [c] to create sprite.");
        }

        int maxSize = height - 6;
        int offset = (selected / maxSize) * maxSize;

        for (int i = 0; i < Math.min(collection.size() - offset, maxSize + 1); i++) {
            String name = collection.getSprite(i + offset).getName();
            if (i == maxSize) {
                text.setForegroundColor(new TextColor.RGB(50, 50, 50));
                text.putString(2, 2 + i, "More ...");
            } else if (i + offset == selected) {
                text.setForegroundColor(new TextColor.RGB(0, 0, 200));
                text.putString(2, 2 + i, "> [" + (i + 1 + offset) + "] " + name);
            } else {
                text.setForegroundColor(new TextColor.RGB(0, 0, 0));
                text.putString(2, 2 + i, "[" + (i + 1 + offset) + "] " + name);
            }
        }
    }

    // EFFECTS: handles input for collection menu
    // MODIFIES: this
    @Override
    public void handleInput(char keyChar) {
        if (keyChar == 'd') {
            tryDeleteSprite();
        }
    }

    // EFFECTS: handles input for collection menu
    // MODIFIES: this
    @Override
    public void handleInput(KeyType keyType) {
        switch (keyType) {
            case ArrowDown:
                if (selected < collection.size() - 1) {
                    selected++;
                }
                break;
            case ArrowUp:
                if (selected > 0) {
                    selected--;
                }
                break;
        }
    }

    // EFFECTS: deletes sprite at selected index if it exists and updates selected sprite
    // MODIFIES: this
    private void tryDeleteSprite() {
        if (collection.size() != 0) {
            collection.deleteSprite(selected);
            if (selected >= collection.size()) {
                selected--;
            }
        }
    }

    // EFFECTS: returns which menu to switch to
    //          MenuType.STAY to stay on current menu
    @Override
    public MenuType switchMenu(char keyChar) {
        switch (keyChar) {
            case 'c':
                return MenuType.CREATE_MENU;
            case 'e':
                if (collection.size() > 0) {
                    return MenuType.EDITOR_MENU;
                }
                break;
            case 'l':
                return MenuType.LOAD_FILE_MENU;
            case 's':
                return MenuType.SAVE_FILE_MENU;
        }
        return MenuType.STAY;
    }

    // EFFECTS: returns the menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.COLLECTION_MENU;
    }

    // EFFECTS: returns selected index
    public int getSelected() {
        return selected;
    }
}
