package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FrameTest {
    Frame frame0;
    Frame frame1;

    @BeforeEach
    public void setup() {
        frame0 = new Frame(32, 40);
        frame1 = new Frame(1, 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(32, frame0.getWidth());
        assertEquals(40, frame0.getHeight());
        assertEquals(1, frame1.getWidth());
        assertEquals(1, frame1.getHeight());

        Pixel defaultPixel = new Pixel();
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 40; y++) {
                assertTrue(frame0.getPixel(x, y).equals(defaultPixel));
            }
        }
        assertTrue(frame1.getPixel(0, 0).equals(defaultPixel));
    }

    @Test
    public void testSetPixelColor() {
        frame1.setPixelColor(0, 0, 200, 100, 50);
        assertTrue(frame1.getPixel(0, 0).equals(200, 100, 50));

        frame0.setPixelColor(16, 20, 75, 50, 125);
        assertTrue(frame0.getPixel(16, 20).equals(75, 50, 125));
    }

    @Test
    public void testReplacePixelColor() {
        frame0.setPixelColor(5, 5, 200, 100, 50);
        assertTrue(frame0.getPixel(5, 5).equals(200, 100, 50));

        frame0.setPixelColor(5, 5, 75, 50, 125);
        assertTrue(frame0.getPixel(5, 5).equals(75, 50, 125));
    }

    @Test
    public void testSetPixelColorCorners() {
        frame0.setPixelColor(0, 0, 0, 0, 0);
        assertTrue(frame0.getPixel(0, 0).equals(0, 0, 0));

        frame0.setPixelColor(31, 39, 20, 30, 40);
        assertTrue(frame0.getPixel(31, 39).equals(20, 30, 40));
    }

    @Test
    public void testSetPixelTransparency() {
        frame0.setPixelTransparent(20, 20, false);
        assertFalse(frame0.getPixel(20, 20).getTransparent());

        frame1.setPixelTransparent(0, 0, false);
        assertFalse(frame1.getPixel(0, 0).getTransparent());

        frame1.setPixelTransparent(0, 0, true);
        assertTrue(frame1.getPixel(0, 0).getTransparent());
    }

    @Test
    public void testSetPixelTransparencyCorners() {
        frame0.setPixelTransparent(0, 0, false);
        assertFalse(frame0.getPixel(0, 0).getTransparent());

        frame0.setPixelTransparent(31, 39, false);
        assertFalse(frame0.getPixel(31, 39).getTransparent());
    }

    @Test
    public void testToObjectDimensions() {
        Object frameObject0 = frame0.toObject();
        assertEquals(frame0.getWidth() * frame0.getHeight() * 4, ((JSONArray)frameObject0).length());

        Object frameObject1 = frame1.toObject();
        assertEquals(4, ((JSONArray)frameObject1).length());
    }

    @Test
    public void testToObjectData() {
        frame0.setPixelColor(2, 2, 1, 2, 3);
        frame0.setPixelColor(8, 4, 5, 4, 1);
        frame0.setPixelTransparent(5, 9, false);
        frame0.setPixelTransparent(5, 5, false);
        JSONArray frameObject0 = (JSONArray)frame0.toObject();

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 40; y++) {
                int index = (y + x * 40) * 4;
                Pixel pixel = frame0.getPixel(x, y);
                assertEquals(pixel.getRed(), frameObject0.getInt(index));
                assertEquals(pixel.getGreen(), frameObject0.getInt(index + 1));
                assertEquals(pixel.getBlue(), frameObject0.getInt(index + 2));
                assertEquals(pixel.getTransparent(), frameObject0.getBoolean(index + 3));
            }
        }
    }
}
