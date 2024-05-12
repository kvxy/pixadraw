package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpriteTest {
    Sprite sprite0;
    Sprite sprite1;

    @BeforeEach
    public void setup() {
        sprite0 = new Sprite("Sprite 0", 32, 40);
        sprite1 = new Sprite("Small Sprite", 1, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(32, sprite0.getWidth());
        assertEquals(40, sprite0.getHeight());
        assertEquals("Sprite 0", sprite0.getName());
        assertEquals(1, sprite0.getFramesLength());
    }

    @Test
    public void testAddFrameToEnd() {
        Frame frame = sprite0.addFrame(1);
        assertEquals(2, sprite0.getFramesLength());
        assertEquals(frame, sprite0.getFrame(1));
    }

    @Test
    public void testAddFrameToStart() {
        Frame frame = sprite0.addFrame(0);
        assertEquals(2, sprite0.getFramesLength());
        assertEquals(frame, sprite0.getFrame(0));
    }

    @Test
    public void testAddFrameToMiddle() {
        sprite0.addFrame(1);

        Frame frame = sprite0.addFrame(1);
        assertEquals(3, sprite0.getFramesLength());
        assertEquals(frame, sprite0.getFrame(1));
    }

    @Test
    public void deleteLastFrame() {
        Frame frame = sprite0.addFrame(0); // add frame to start
        sprite0.deleteFrame(1);

        assertEquals(1, sprite0.getFramesLength());
        assertEquals(frame, sprite0.getFrame(0));
    }

    @Test
    public void deleteFirstFrame() {
        Frame frame = sprite0.addFrame(1); // add frame to end
        sprite0.deleteFrame(0);

        assertEquals(1, sprite0.getFramesLength());
        assertEquals(frame, sprite0.getFrame(0));
    }

    @Test
    public void deleteMiddleFrame() {
        Frame frameEnd = sprite0.addFrame(1); // add frame to end
        Frame frameStart = sprite0.addFrame(0); // add frame to start
        sprite0.deleteFrame(1);

        assertEquals(2, sprite0.getFramesLength());
        assertEquals(frameEnd, sprite0.getFrame(1));
        assertEquals(frameStart, sprite0.getFrame(0));
    }

    @Test
    public void testToObject() {
        sprite0.addFrame(0);

        JSONObject spriteObject = (JSONObject)sprite0.toObject();
        assertEquals("Sprite 0", spriteObject.getString("name"));
        assertEquals(32, spriteObject.getInt("width"));
        assertEquals(40, spriteObject.getInt("height"));
        assertEquals(2, spriteObject.getJSONArray("frames").length());
    }
}
