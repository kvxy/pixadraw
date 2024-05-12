package ui.terminal.inputters;

import com.googlecode.lanterna.input.KeyType;

// Takes in inputs and stores them in a field
public class InputField {
    // Types of input field
    enum FieldType {
        INT,
        STRING
    }

    private String name;
    private FieldType type;
    private String storedData;

    // EFFECTS: constructs an input field
    public InputField(String name, String defaultValue) {
        this.name = name;
        type = FieldType.STRING;
        storedData = defaultValue;
    }

    public InputField(String name, int defaultValue) {
        this.name = name;
        type = FieldType.INT;
        storedData = "" + defaultValue;
    }

    // MODIFIES: this
    // EFFECTS: handles inputs into this input field
    public void handleInput(char keyChar) {
        switch (type) {
            case STRING:
                storedData = storedData + keyChar;
                break;
            case INT:
                try {
                    Integer.parseInt("" + keyChar);
                } catch (NumberFormatException exception) {
                    return;
                }
                storedData += keyChar;
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles key inputs for backspace
    public void handleInput(KeyType keyType) {
        if (keyType == KeyType.Backspace && storedData.length() > 0) {
            storedData = storedData.substring(0, storedData.length() - 1);
        }
    }

    // REQUIRES: type is int
    // MODIFIES: this
    // EFFECTS: sets the stored integer
    public void setData(int integer) {
        storedData = "" + integer;
    }

    // REQUIRES: type is int
    // EFFECTS: returns stored integer
    public int getStoredInt() {
        int parsedInt;
        try {
            parsedInt = Integer.parseInt(storedData);
        } catch (NumberFormatException exception) {
            return 0;
        }
        return parsedInt;
    }

    // REQUIRES: type is string
    // EFFECTS: returns stored string
    public String getStoredString() {
        return storedData;
    }

    // EFFECTS: returns input field type
    public FieldType getType() {
        return type;
    }

    // EFFECTS: returns input field name
    public String getName() {
        return name;
    }
}
