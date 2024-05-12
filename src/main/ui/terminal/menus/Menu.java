package ui.terminal.menus;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;

// Terminal-based GUI
public abstract class Menu {
    protected Collection collection;
    protected Screen screen;
    protected int width;
    protected int height;

    // EFFECTS:
    public Menu(Collection collection, Screen screen) {
        this.collection = collection;
        this.screen = screen;
        this.width = screen.getTerminalSize().getColumns();
        this.height = screen.getTerminalSize().getRows();
    }

    // MODIFIES: this
    // EFFECTS: draws the menu
    public void draw() {
        TextGraphics text = screen.newTextGraphics();
        drawBackground(text);
    }

    // EFFECTS: draws the collection menu
    // MODIFIES: text
    protected void drawBackground(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        text.fill(' ');

        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height - 1), ' ');
        text.drawRectangle(new TerminalPosition(0, height - 1), new TerminalSize(width, 1), ' ');
    }

    // EFFECTS: draws the header
    // MODIFIES: text
    protected void drawHeader(TextGraphics text, String title) {
        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.setForegroundColor(new TextColor.RGB(255, 255, 255));
        text.putString(1, 0, title);
    }

    // EFFECTS: draws the footer with two rows of text
    // MODIFIES: text
    protected void drawFooter(TextGraphics text, String rowText0, String rowText1) {
        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.setForegroundColor(new TextColor.RGB(100, 255, 100));
        text.putString(1, height - 2, rowText0);
        text.putString(1, height - 1, rowText1);
    }

    // MODIFIES: this
    // EFFECTS: handles user input for characters
    public void handleInput(char keyChar) {
        // nothing here
    }

    // MODIFIES: this
    // EFFECTS: handles user input for key types
    public void handleInput(KeyType keyType) {
        // nothing here
    }

    // EFFECTS: sends which menu to switch to
    //          MenuType.STAY to stay on current menu
    public MenuType switchMenu(KeyType keyType) {
        return MenuType.STAY;
    }

    // EFFECTS: returns which menu to switch to
    //          MenuType.STAY to stay on current menu
    public MenuType switchMenu(char keyChar) {
        return MenuType.STAY;
    }

    // EFFECTS: returns the type of the menu
    public abstract MenuType getMenuType();
}
