package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.Tool;
import tools.EraseTool;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EraseToolTest {
    Tool eraser;
    Frame frame;

    @BeforeEach
    public void setup() {
        eraser = new EraseTool();

        frame = new Frame(32, 32);
        // removes all transparency
        for (int x = 0; x < 32; x ++) {
            for (int y = 0; y < 32; y ++) {
                frame.setPixelTransparent(x, y, false);
            }
        }
    }

    @Test
    public void testUseTool() {
        eraser.use(16, 16, 0, 0, 0, frame);
        assertTrue(frame.getPixel(16, 16).getTransparent());
    }

    @Test
    public void testUseToolBounds() {
        eraser.use(-1, 0, 0, 0, 0, frame);
        eraser.use(32, 0, 0, 0, 0, frame);
        eraser.use(0, -1, 0, 0, 0, frame);
        eraser.use(0, 32, 0, 0, 0, frame);

        // all pixels are still solid
        for (int x = 0; x < 32; x ++) {
            for (int y = 0; y < 32; y ++) {
                assertFalse(frame.getPixel(x, y).getTransparent());
            }
        }
    }
}
