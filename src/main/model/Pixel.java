package model;

import persistence.Writable;

// Represents a single RGB pixel
public class Pixel {
    public static final Pixel DEFAULT_PIXEL = new Pixel(255, 255, 255, true);

    private int red;
    private int green;
    private int blue;
    private boolean transparent;

    // EFFECTS: creates a transparent pixel
    public Pixel() {
        setColor(DEFAULT_PIXEL.getRed(), DEFAULT_PIXEL.getGreen(), DEFAULT_PIXEL.getBlue());
        transparent = DEFAULT_PIXEL.getTransparent();
    }

    // EFFECTS: creates a pixel with an r, g, and b value that isn't transparent
    public Pixel(int r, int g, int b) {
        setColor(r, g, b);
        transparent = false;
    }

    // EFFECTS: creates a pixel with an r, g, b value, and if it is transparent
    public Pixel(int r, int g, int b, boolean transparent) {
        setColor(r, g, b);
        this.transparent = transparent;
    }

    // MODIFIES: this
    // EFFECTS: sets the r, g, and b value of this pixel
    //          if r, g, or b values are > 255, they are set to 255
    //          if r, g, or b values are < 0, they are set to 0
    public void setColor(int r, int g, int b) {
        red = Math.min(Math.max(0, r), 255);
        green = Math.min(Math.max(0, g), 255);
        blue = Math.min(Math.max(0, b), 255);
    }

    // MODIFIES: this
    // EFFECTS: sets the transparency
    public void setTransparency(boolean transparent) {
        this.transparent = transparent;
    }

    // EFFECTS: returns the red component of this pixel
    public int getRed() {
        return red;
    }

    // EFFECTS: returns the green component of this pixel
    public int getGreen() {
        return green;
    }

    // EFFECTS: returns the blue component of this pixel
    public int getBlue() {
        return blue;
    }

    // EFFECTS: returns the blue component of this pixel
    public boolean getTransparent() {
        return transparent;
    }

    // EFFECTS: returns true if given r g b value equals the pixel's
    public boolean equals(int r, int g, int b) {
        return getRed() == r && getGreen() == g && getBlue() == b;
    }

    // EFFECTS: returns true if given pixel's color and transparency equals this pixel
    public boolean equals(Pixel comparePixel) {
        return equals(comparePixel.getRed(), comparePixel.getGreen(), comparePixel.getBlue())
                && getTransparent() == comparePixel.getTransparent();
    }
}
