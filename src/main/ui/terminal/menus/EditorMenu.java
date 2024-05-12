package ui.terminal.menus;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import model.Collection;
import model.Sprite;
import model.Pixel;
import tools.*;
import ui.terminal.inputters.InputField;
import ui.terminal.inputters.InputSelector;

// Menu for editing sprites
public class EditorMenu extends Menu {
    enum Tab {
        TOOL_PICKER,
        CANVAS,
        COLOR_PICKER
    }

    private static final Tool[] TOOLS = {
            new PenTool(),
            new EraseTool()
    };
    private static final String[] TOOL_NAMES = {
            "Pen",
            "Eraser"
    };

    private Sprite sprite;
    private int frameIndex;

    private int canvasX;
    private int canvasY;
    private int cursorX;
    private int cursorY;

    private Tab tab;
    private InputSelector toolSelector;
    private InputSelector colorSelector;

    // EFFECTS: constructs the editor menu
    public EditorMenu(Collection collection, Screen screen, Sprite sprite) {
        super(collection, screen);
        this.sprite = sprite;
        frameIndex = 0;

        canvasX = width / 2 - sprite.getWidth();
        canvasY = height / 2 - sprite.getHeight() / 2;
        cursorX = sprite.getWidth() / 2;
        cursorY = sprite.getHeight() / 2;

        tab = Tab.CANVAS; // default to canvas tab

        toolSelector = new InputSelector(TOOL_NAMES);

        InputField[] colorFields = {
                new InputField("Red", 0),
                new InputField("Blue", 0),
                new InputField("Green", 0)
        };
        colorSelector = new InputSelector(colorFields);
    }

    // MODIFIES: this
    // EFFECTS: draws the editor menu
    @Override
    public void draw() {
        TextGraphics text = screen.newTextGraphics();

        drawBackground(text);
        drawHeader(text, "Sprite Editor - " + sprite.getName());
        drawVariableFooter(text);

        drawToolBar(text);
        drawColorBar(text);
        drawFrameInfo(text);
        drawCanvas(text);
    }

    // MODIFIES: text
    // EFFECTS: draws the footer depending on which tab is selected (tools, canvas, or color)
    private void drawVariableFooter(TextGraphics text) {
        String footerText = "[q] Tool Picker | [w] Canvas | [e] Color Picker | [Tab] Back | [Del] Exit Program";
        switch (tab) {
            case TOOL_PICKER:
                drawFooter(text, footerText,
                        "<Tool Picker>: [Arrow Up] Previous Tool | [Arrow Down] Next Tool");
                break;
            case CANVAS:
                drawFooter(text, footerText,
                        "<Canvas>: [Arrow Keys] Move Cursor in Canvas | [Space] Use Tool");
                break;
            case COLOR_PICKER:
                drawFooter(text, footerText,
                        "<Color Picker>: [Arrow Up] Previous Item | [Arrow Down] Next Item");
                break;
        }
    }

    // MODIFIES: text
    // EFFECTS: draws the canvas
    private void drawCanvas(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(230, 230, 230));
        text.setForegroundColor(new TextColor.RGB(50, 50, 50));
        text.fillRectangle(new TerminalPosition(16, 1), new TerminalSize(32 * 2, 32), ' ');

        TerminalSize size = new TerminalSize(2, 1);
        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                TerminalPosition position = new TerminalPosition(canvasX + x * 2, canvasY + y);
                Pixel pixel = sprite.getFrame(frameIndex).getPixel(x, y);
                if (pixel.getTransparent()) {
                    text.setBackgroundColor(new TextColor.RGB(255, 255, 255));
                } else {
                    text.setBackgroundColor(new TextColor.RGB(pixel.getRed(), pixel.getBlue(), pixel.getGreen()));
                }
                text.fillRectangle(position, size, (cursorX == x && cursorY == y ? 'X' : ' '));
            }
        }
    }

    // MODIFIES: text
    // EFFECTS: draws the toolbar
    private void drawToolBar(TextGraphics text) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        text.setForegroundColor(new TextColor.RGB(0, 0, 0));
        text.putString(2, 2, tab == Tab.TOOL_PICKER ? "[Tools]" : "Tools");

        toolSelector.draw(text, 2, 4);
    }

    // MODIFIES: text
    // EFFECTS: draws color bar
    private void drawColorBar(TextGraphics text) {
        int posX = 32 * 2 + 16 + 1;

        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        text.setForegroundColor(new TextColor.RGB(0, 0, 0));
        text.putString(posX, 2, tab == Tab.COLOR_PICKER ? "[Color]" : "Color");

        colorSelector.draw(text, posX, 4);
    }

    // MODIFIES: text
    // EFFECTS: draws frame information
    private void drawFrameInfo(TextGraphics text) {
        int posX = 32 * 2 + 16 + 1;
        int posY = height - 9;

        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        text.setForegroundColor(new TextColor.RGB(0, 0, 0));
        text.putString(posX, posY, "Frame " + (frameIndex + 1) + "/" + sprite.getFramesLength());
        text.putString(posX, posY + 1, "[>] Next");
        text.putString(posX, posY + 2, "[<] Previous");
        text.putString(posX, posY + 3, "[+] Add Frame");
        text.putString(posX, posY + 4, "[d] Delete");
    }

    // MODIFIES: this
    // EFFECTS: moves the cursor based on given inputs
    private void moveCursor(KeyType keyType) {
        int movementX = (keyType == KeyType.ArrowRight ? 1 : (keyType == KeyType.ArrowLeft ? -1 : 0));
        int movementY = (keyType == KeyType.ArrowDown ? 1 : (keyType == KeyType.ArrowUp ? -1 : 0));
        int newX = movementX + cursorX;
        int newY = movementY + cursorY;
        if (newX >= sprite.getWidth() || newX < 0 || newY >= sprite.getHeight() || newY < 0) {
            return;
        }
        cursorX = newX;
        cursorY = newY;
    }

    // MODIFIES: this
    // EFFECTS: uses the current tool selected at cursor coordinates
    private void useTool() {
        int r = colorSelector.getInputItem(0).getStoredInt();
        int g = colorSelector.getInputItem(1).getStoredInt();
        int b = colorSelector.getInputItem(2).getStoredInt();
        TOOLS[toolSelector.getSelectedIndex()].use(cursorX, cursorY, r, g, b, sprite.getFrame(frameIndex));
    }

    // MODIFIES: this
    // EFFECTS: handles input for the color tab
    private void handleColorInput(char keyChar) {
        if (tab == Tab.COLOR_PICKER) {
            InputField colorField = colorSelector.getSelectedInput();
            colorField.handleInput(keyChar);
            if (colorField.getStoredInt() > 255) {
                colorField.setData(255);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles input for moving frame index forward/backwards
    private void handleFrameScrollInput(char keyType) {
        switch (keyType) {
            case '>':
                if (frameIndex < sprite.getFramesLength() - 1) {
                    frameIndex++;
                } else {
                    frameIndex = 0;
                }
                break;
            case '<':
                if (frameIndex > 0) {
                    frameIndex--;
                } else {
                    frameIndex = sprite.getFramesLength() - 1;
                }
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles input for frame manipulation (deletion and creation)
    private void handleFrameManipulationInput(char keyType) {
        switch (keyType) {
            case '+':
                sprite.addFrame(frameIndex + 1);
                frameIndex++;
                break;
            case 'd':
                if (sprite.getFramesLength() != 1) {
                    sprite.deleteFrame(frameIndex);
                    if (frameIndex <= sprite.getFramesLength()) {
                        frameIndex = sprite.getFramesLength() - 1;
                    }
                }
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles input for editor menu
    @Override
    public void handleInput(KeyType keyType) {
        switch (tab) {
            case TOOL_PICKER:
                toolSelector.handleInput(keyType);
                break;
            case CANVAS:
                moveCursor(keyType);
                break;
            case COLOR_PICKER:
                colorSelector.handleInput(keyType);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles character inputs for editor menu
    @Override
    public void handleInput(char keyChar) {
        switch (keyChar) {
            case 'q':
                tab = Tab.TOOL_PICKER;
                break;
            case 'w':
                tab = Tab.CANVAS;
                break;
            case 'e':
                tab = Tab.COLOR_PICKER;
                break;
            case ' ':
                if (tab == Tab.CANVAS) {
                    useTool();
                }
                break;
        }
        handleColorInput(keyChar);
        handleFrameManipulationInput(keyChar);
        handleFrameScrollInput(keyChar);
    }

    // EFFECTS: returns which menu to switch to.
    //          MenuType.STAY to stay on current menu
    @Override
    public MenuType switchMenu(KeyType keyType) {
        if (keyType == KeyType.Tab) {
            return MenuType.COLLECTION_MENU;
        }
        return MenuType.STAY;
    }

    // EFFECTS: returns the menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.EDITOR_MENU;
    }
}
