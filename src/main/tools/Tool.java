package tools;

import model.Frame;

// Abstract tool class
public abstract class Tool {
    // MODIFIES: frame
    // EFFECTS: draws one pixel in the frame
    protected void draw(int x, int y, int r, int g, int b, Frame frame) {
        if (inBounds(x, y, frame)) {
            frame.setPixelTransparent(x, y, false);
            frame.setPixelColor(x, y, r, g, b);
        }
    }

    // MODIFIES: frame
    // EFFECTS: erases one pixel in the frame
    protected void erase(int x, int y, Frame frame) {
        if (inBounds(x, y, frame)) {
            frame.setPixelTransparent(x, y, true);
        }
    }

    // MODIFIES: frame
    // EFFECTS: uses the tool on frame at given coordinates
    public abstract void use(int x, int y, int r, int g, int b, Frame frame);

    // EFFECTS: checks if given coordinates are within sprite
    protected boolean inBounds(int x, int y, Frame frame) {
        return (x >= 0 && x < frame.getWidth() && y >= 0 && y < frame.getHeight());
    }
}
