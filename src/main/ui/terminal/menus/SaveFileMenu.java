package ui.terminal.menus;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;
import persistence.PDWriter;
import ui.terminal.inputters.InputField;

import java.io.FileNotFoundException;

// Menu for saving files
public class SaveFileMenu extends Menu {
    private InputField fileInputField;
    private Boolean hasError;

    // EFFECTS: creates a new file saver menu
    public SaveFileMenu(Collection collection, Screen screen, String fileName) {
        super(collection, screen);
        hasError = false;
        fileInputField = new InputField("File", fileName);
    }

    // EFFECTS: draws the save menu
    @Override
    public void draw() {
        TextGraphics text = screen.newTextGraphics();

        drawBackground(text);
        drawHeader(text, "Save File");
        drawFooter(text, "[Enter] Save File | [Tab] Back | [Del] Exit Program", "");

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
        text.putString(2, 4, "Press [Enter] to save file!");
    }

    // MODIFIES: text
    // EFFECTS: draws the error message
    private void drawError(TextGraphics text) {
        if (hasError) {
            text.setForegroundColor(new TextColor.RGB(200, 0, 0));
            text.putString(2, height - 4, "File not found :(");
        }
    }

    // EFFECTS: returns the file path
    //          defaults to default.json if no specified file name
    public String getFilePath() {
        if (fileInputField.getStoredString().length() == 0) {
            return "./data/default.json";
        }
        return "./data/" + fileInputField.getStoredString() + ".json";
    }

    // EFFECTS: returns the file name
    //          defaults to "default" if no specified file name
    public String getFileName() {
        if (fileInputField.getStoredString().length() == 0) {
            return "default";
        }
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
            try {
                PDWriter writer = new PDWriter(getFilePath());
                writer.write(collection);
                writer.close();
            } catch (FileNotFoundException e) {
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
        return MenuType.SAVE_FILE_MENU;
    }
}
