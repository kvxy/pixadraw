package ui.terminal.inputters;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;

// A column of input fields where each item is selectable with arrow keys
public class InputSelector {
    enum SelectorType {
        INPUT_FIELD,
        STRING
    }

    private SelectorType type;
    private int selected;
    private int length;
    private InputField[] inputFieldItems;
    private String[] stringItems;

    // EFFECTS: constructs a select row of input fields
    public InputSelector(InputField[] items) {
        inputFieldItems = items;
        selected = 0;
        length = items.length;
        type = SelectorType.INPUT_FIELD;
    }

    // EFFECTS: constructs a select row of string
    public InputSelector(String[] items) {
        stringItems = items;
        selected = 0;
        length = items.length;
        type = SelectorType.STRING;
    }

    // MODIFIES: screen
    // EFFECTS: draws the select row at given position
    public void draw(TextGraphics text, int x, int y) {
        text.setBackgroundColor(new TextColor.RGB(200, 200, 200));
        for (int i = 0; i < length; i++) {
            String line = (selected == i ? "> " : "");

            if (selected == i) {
                text.setForegroundColor(new TextColor.RGB(0, 0, 200));
            } else {
                text.setForegroundColor(new TextColor.RGB(0, 0, 0));
            }

            if (type == SelectorType.INPUT_FIELD) {
                InputField item = inputFieldItems[i];
                line += item.getName() + ": ";

                if (item.getType() == InputField.FieldType.INT) {
                    line += item.getStoredInt();
                } else {
                    line += item.getStoredString();
                }
            } else {
                line += stringItems[i];
            }

            text.putString(x, y + i * 2, line);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles user input
    public void handleInput(KeyType keyType) {
        switch (keyType) {
            case ArrowDown:
                if (selected < length - 1) {
                    selected++;
                }
                break;
            case ArrowUp:
                if (selected > 0) {
                    selected--;
                }
                break;
        }
        if (type == SelectorType.INPUT_FIELD) {
            inputFieldItems[selected].handleInput(keyType);
        }
    }

    // REQUIRES: 0 <= index < length,
    //           type is input field
    // EFFECTS: returns input field at given index
    public InputField getInputItem(int index) {
        return inputFieldItems[index];
    }

    // REQUIRES: type is input field
    // EFFECTS: returns the selected input field
    public InputField getSelectedInput() {
        return inputFieldItems[selected];
    }

    // REQUIRES: 0 <= index < length,
    //           type is string
    // EFFECTS: returns string at given index
    public String getStringItem(int index) {
        return stringItems[index];
    }

    // REQUIRES: type is string
    // EFFECTS: returns the selected string
    public String getSelectedString() {
        return stringItems[selected];
    }

    // EFFECTS: returns the selected index
    public int getSelectedIndex() {
        return selected;
    }

    // EFFECTS: returns number of items
    public int size() {
        return length;
    }
}
