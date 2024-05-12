package model;

import org.json.JSONArray;
import persistence.Writable;

// Represents a single frame of a sprite
public class Frame implements Writable {
    private Pixel[][] pixels;
    private int width;
    private int height;

    // REQUIRES: width and height both > 0
    // EFFECTS: creates a new blank frame with given width and height
    public Frame(int width, int height) {
        pixels = new Pixel[width][height];
        this.width = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel();
            }
        }
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // MODIFIES: this
    // EFFECTS: sets color of pixel at given coordinates
    public void setPixelColor(int x, int y, int r, int g, int b) {
        pixels[x][y].setColor(r, g, b);
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // MODIFIES: this
    // EFFECTS: sets transparency of pixel at given coordinates
    public void setPixelTransparent(int x, int y, boolean transparent) {
        pixels[x][y].setTransparency(transparent);
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // EFFECTS: returns pixel at given coordinates
    public Pixel getPixel(int x, int y) {
        return pixels[x][y];
    }

    // EFFECTS: returns frame width
    public int getWidth() {
        return width;
    }

    // EFFECTS: returns frame height
    public int getHeight() {
        return height;
    }

    // EFFECTS: returns the object of this frame
    @Override
    public Object toObject() {
        JSONArray pixelsArray = new JSONArray();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                pixelsArray.put(pixel.getRed());
                pixelsArray.put(pixel.getGreen());
                pixelsArray.put(pixel.getBlue());
                pixelsArray.put(pixel.getTransparent());
            }
        }
        return pixelsArray;
    }
}
