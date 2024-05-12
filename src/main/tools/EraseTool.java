package tools;

import model.Frame;

// An erasure tool
public class EraseTool extends Tool {
    // MODIFIES: frame
    // EFFECTS: uses the erasure tool on frame at given coordinates
    @Override
    public void use(int x, int y, int r, int g, int b, Frame frame) {
        erase(x, y, frame);
    }
}
