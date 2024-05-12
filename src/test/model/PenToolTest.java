package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.PenTool;
import tools.Tool;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PenToolTest {
    Tool pen;
    Frame frame;

    @BeforeEach
    public void setup() {
        pen = new PenTool();
        frame = new Frame(32, 32);
    }

    @Test
    public void testUseTool() {
        pen.use(16, 16, 255, 50, 25, frame);
        assertTrue(frame.getPixel(16, 16).equals(255, 50, 25));
    }

    @Test
    public void testUseToolBounds() {
        pen.use(-1, 0, 50, 50, 50, frame);
        pen.use(32, 0, 50, 50, 50, frame);
        pen.use(0, -1, 50, 50, 50, frame);
        pen.use(0, 32, 50, 50, 50, frame);

        // all pixels are still transparent
        for (int x = 0; x < 32; x ++) {
            for (int y = 0; y < 32; y ++) {
                assertTrue(frame.getPixel(x, y).getTransparent());
            }
        }
    }
}
