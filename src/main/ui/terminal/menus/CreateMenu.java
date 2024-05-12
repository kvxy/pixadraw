package ui.terminal.menus;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;
import ui.terminal.inputters.InputField;
import ui.terminal.inputters.InputSelector;

// Menu for creating new sprites
public class CreateMenu extends Menu {
    private InputSelector inputSelector;

    // EFFECTS: constructs a create menu with 3 input fields: sprite name, width, and height
    public CreateMenu(Collection collection, Screen screen) {
        super(collection, screen);

        InputField[] items = {
                new InputField("Name", ""),
                new InputField("Width", 32),
                new InputField("Height", 32)
        };
        inputSelector = new InputSelector(items);
    }

    // EFFECTS: draws the create menu
    @Override
    public void draw() {
        TextGraphics text = screen.newTextGraphics();

        drawBackground(text);
        drawHeader(text, "Create Sprite");
        drawFooter(text, "[Arrow Down] Next Item | [Arrow Up] Previous Item | [Enter] Create",
                "[Tab] Back | [Del] Exit Program");

        drawItems(text);
        drawError(text);
    }

    // MODIFIES: text
    // EFFECTS: draws all input field items
    private void drawItems(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        inputSelector.draw(text, 2, 2);

        text.setForegroundColor(new TextColor.RGB(100, 100, 100));
        text.putString(2, 2 + inputSelector.size() * 2, "Press [Enter] when you're done!");
    }

    // MODIFIES: text
    // EFFECTS: draws input field errors
    private void drawError(TextGraphics text) {
        text.setForegroundColor(new TextColor.RGB(200, 0, 0));
        text.putString(2, height - 4, getErrors());
    }

    // EFFECTS: returns error message if any fields are incorrectly filled
    //          "" means no errors found
    private String getErrors() {
        String inputName = inputSelector.getInputItem(0).getStoredString();
        int inputWidth = inputSelector.getInputItem(1).getStoredInt();
        int inputHeight = inputSelector.getInputItem(2).getStoredInt();

        if (inputName.length() == 0) {
            return "Name can not be empty";
        } else if (inputWidth <= 3 || inputWidth > 32) {
            return "Width must be between 4 and 32 (including)";
        } else if (inputHeight <= 3 || inputHeight > 32) {
            return "Height must be between 4 and 32 (including)";
        }
        return "";
    }

    // MODIFIES: this
    // EFFECTS: creates a sprite in collection
    private void createSprite() {
        String inputName = inputSelector.getInputItem(0).getStoredString();
        int inputWidth = inputSelector.getInputItem(1).getStoredInt();
        int inputHeight = inputSelector.getInputItem(2).getStoredInt();

        collection.createSprite(inputName, inputWidth, inputHeight);
    }

    // MODIFIES: this
    // EFFECTS: handles character input for selected item field
    @Override
    public void handleInput(char keyChar) {
        inputSelector.getSelectedInput().handleInput(keyChar);
    }

    // MODIFIES: this
    // EFFECTS: handles inputs for key types
    @Override
    public void handleInput(KeyType keyType) {
        inputSelector.handleInput(keyType);
    }

    // EFFECTS: returns which menu to switch to
    //          MenuType.STAY to stay on current menu
    @Override
    public MenuType switchMenu(KeyType keyType) {
        switch (keyType) {
            case Tab:
                return MenuType.COLLECTION_MENU;
            case Enter:
                if (getErrors().equals("")) {
                    createSprite();
                    return MenuType.EDITOR_MENU;
                }
                break;
        }
        return MenuType.STAY;
    }

    // EFFECTS: returns the menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.CREATE_MENU;
    }
}
