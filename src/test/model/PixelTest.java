package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PixelTest {
    Pixel pixelDefault;
    Pixel pixelWhite;
    Pixel pixelBlack;
    Pixel pixelTransparent;
    Pixel pixelNormal;

    @BeforeEach
    public void setup() {
        pixelDefault = new Pixel();
        pixelWhite = new Pixel(255, 255, 255);
        pixelBlack = new Pixel(0, 0, 0);
        pixelTransparent = new Pixel(50, 25, 0, true);
        pixelNormal = new Pixel(150, 100, 50);
    }

    @Test
    public void testConstructor() {
        assertTrue(pixelDefault.equals(Pixel.DEFAULT_PIXEL));
        assertTrue(pixelWhite.equals(255, 255, 255));
        assertTrue(pixelBlack.equals(0, 0, 0));

        assertTrue(pixelTransparent.equals(50, 25, 0));
        assertTrue(pixelTransparent.getTransparent());

        assertTrue(pixelNormal.equals(150, 100, 50));
        assertFalse(pixelNormal.getTransparent());
    }

    @Test
    public void testSetColor() {
        pixelNormal.setColor(50, 20, 175);
        assertTrue(pixelNormal.equals(50, 20, 175));
    }

    @Test
    public void testSetColorBounds() {
        pixelNormal.setColor(0, 0, 0);
        assertTrue(pixelNormal.equals(0, 0, 0));

        pixelNormal.setColor(255, 255, 255);
        assertTrue(pixelNormal.equals(255, 255, 255));
    }

    @Test
    public void testSetColorOverBounds() {
        pixelNormal.setColor(256, 256, 256);
        assertTrue(pixelNormal.equals(255, 255, 255));
    }

    @Test
    public void testSetColorUnderBounds() {
        pixelNormal.setColor(-1, -1, -1);
        assertTrue(pixelNormal.equals(0, 0, 0));
    }

    @Test
    public void testSetTransparency() {
        pixelNormal.setTransparency(true);
        assertTrue(pixelNormal.getTransparent());
        pixelNormal.setTransparency(false);
        assertFalse(pixelNormal.getTransparent());
    }

    @Test
    public void testColorGetters() {
        assertEquals(150, pixelNormal.getRed());
        assertEquals(100, pixelNormal.getGreen());
        assertEquals(50, pixelNormal.getBlue());
    }

    @Test
    public void testEqualsColor() {
        assertTrue(pixelNormal.equals(150, 100, 50));

        assertFalse(pixelNormal.equals(100, 100, 50));
        assertFalse(pixelNormal.equals(150, 50, 50));
        assertFalse(pixelNormal.equals(150, 100, 25));
    }

    @Test
    public void testEqualsOtherPixel() {
        assertTrue(pixelNormal.equals(new Pixel(150, 100, 50, false)));
        assertFalse(pixelNormal.equals(new Pixel(150, 100, 50, true)));
        assertFalse(pixelNormal.equals(new Pixel(0, 0, 0, false)));
    }
}
