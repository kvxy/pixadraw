package model;

import persistence.Writable;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

// Represents a sprite which can hold multiple frames
public class Sprite implements Writable {
    private int width;
    private int height;
    private String name;
    private List<Frame> frames;

    // REQUIRES: width and height both > 0
    //           name has at least 1 character
    // EFFECTS: creates a sprite with a width, height, name, empty palette, and one frame
    public Sprite(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.frames = new ArrayList<>();
        addFrame(0);
    }

    // REQUIRES: 0 <= index <= number of frames
    // MODIFIES: this
    // EFFECTS: adds an empty frame at given index and returns it
    public Frame addFrame(int index) {
        Frame frame = new Frame(width, height);
        frames.add(index, frame);
        return frame;
    }

    // REQUIRES: 0 <= index < number of frames
    // MODIFIES: this
    // EFFECTS: deletes frame at given index
    public void deleteFrame(int index) {
        frames.remove(index);
    }

    // EFFECTS: returns the number of frames in sprite
    public int getFramesLength() {
        return frames.size();
    }

    // REQUIRES: 0 <= index < number of frames
    // EFFECTS: gets frame at given index
    public Frame getFrame(int index) {
        return frames.get(index);
    }

    // EFFECTS: returns sprite name
    public String getName() {
        return name;
    }

    // EFFECTS: returns sprite width
    public int getWidth() {
        return width;
    }

    // EFFECTS: returns sprite height
    public int getHeight() {
        return height;
    }

    // EFFECTS: returns the object of this sprite
    @Override
    public Object toObject() {
        JSONObject spriteObject = new JSONObject();
        spriteObject.put("name", name);
        spriteObject.put("width", width);
        spriteObject.put("height", height);

        JSONArray framesArray = new JSONArray();
        for (Frame frame : frames) {
            framesArray.put(frame.toObject());
        }
        spriteObject.put("frames", framesArray);

        return spriteObject;
    }
}
