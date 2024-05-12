package tools;

import model.Frame;

// A basic pen tool
public class PenTool extends Tool {
    // MODIFIES: frame
    // EFFECTS: uses the pen tool on frame at given coordinates
    @Override
    public void use(int x, int y, int r, int g, int b, Frame frame) {
        draw(x, y, r, g, b, frame);
    }
}
