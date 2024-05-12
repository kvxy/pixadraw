package ui.terminal.menus;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;
import persistence.PDReader;
import ui.terminal.inputters.InputField;

import java.io.IOException;

// Menu for loading files
public class LoadFileMenu extends Menu {
    private InputField fileInputField;
    private Boolean hasError;

    // EFFECTS: creates a new file loader menu
    public LoadFileMenu(Collection collection, Screen screen) {
        super(collection, screen);
        hasError = false;
        fileInputField = new InputField("File", "default");
    }

    // EFFECTS: draws the load menu
    @Override
    public void draw() {
        TextGraphics text = screen.newTextGraphics();

        drawBackground(text);
        drawHeader(text, "Load File");
        drawFooter(text, "[Enter] Load File | [Tab] Back | [Del] Exit Program", "");

        drawFileInput(text);
        drawError(text);
    }

    // MODIFIES: text
    // EFFECTS: draws the file input field
    private void drawFileInput(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        text.setForegroundColor(new TextColor.RGB(0, 0, 200));
        text.putString(2, 2, "> File Path: " + getFilePath());

        text.setForegroundColor(new TextColor.RGB(100, 100, 100));
        text.putString(2, 4, "Press [Enter] to load file!");
    }

    // MODIFIES: text
    // EFFECTS: draws the error message
    private void drawError(TextGraphics text) {
        if (hasError) {
            text.setForegroundColor(new TextColor.RGB(200, 0, 0));
            text.putString(2, height - 4, "Given file not found!");
        }
    }

    // EFFECTS: returns the file path
    private String getFilePath() {
        return "./data/" + fileInputField.getStoredString() + ".json";
    }

    // EFFECTS: returns the name of the file
    public String getFileName() {
        return fileInputField.getStoredString();
    }

    // MODIFIES: this
    // EFFECTS: handles character input for selected item field
    @Override
    public void handleInput(char keyChar) {
        fileInputField.handleInput(keyChar);
    }

    // MODIFIES: this
    // EFFECTS: handles inputs for key types
    @Override
    public void handleInput(KeyType keyType) {
        hasError = false;
        if (keyType == KeyType.Enter) {
            PDReader reader = new PDReader();
            reader.setCollection(collection);
            try {
                reader.read(getFilePath());
            } catch (IOException e) {
                hasError = true;
            }

        }
        fileInputField.handleInput(keyType);
    }

    // EFFECTS: returns which menu to switch to
    //          MenuType.STAY to stay on current menu
    @Override
    public MenuType switchMenu(KeyType keyType) {
        switch (keyType) {
            case Tab:
                return MenuType.COLLECTION_MENU;
            case Enter:
                if (!hasError) {
                    return MenuType.COLLECTION_MENU;
                }
                break;
        }
        return MenuType.STAY;
    }

    // EFFECTS: returns the type of this menu
    @Override
    public MenuType getMenuType() {
        return MenuType.LOAD_FILE_MENU;
    }
}
